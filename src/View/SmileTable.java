package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 * JTable 을 상속하는 Table. 필요한 
 */
public class SmileTable {
	
	private JPanel tablePanel;
	private JScrollPane tableScrollPane;
	private JTable mTable;
	private SmileTableModel mModel;
	private String[] colNames;
	private int colNum;
	
	private List<String[]> curData; // 화면상의 data
	
	private boolean selectable, deletable, emptiable, insertable;
	private List<Boolean> selectedList;	// 체크박스로 선택된 로우
	private boolean[] editable;
	private RowProvider insertListener = null; // 로우를 추가시 기본값을 전달 해 줄 리스너 
	
	private JButton btnDelete, btnDeleteAll, btnAdd;
	
	public SmileTable(String[] columnNames, String[][] dataArr){
		initialize(columnNames, dataArr);
	}
	
	public SmileTable(String[] columnNames, List<String[]> dataList){
		String[][] arr = new String[dataList.size()][];
		for(int i=0; i<arr.length; i++)
			arr[i] = dataList.get(i);
		
		initialize(columnNames, arr);
	}
	
	private void initialize(String[] columnNames, String[][] data){
		setColumnNames(columnNames);
		setData(data);
		
		mModel = new SmileTableModel();
		mTable = new JTable(mModel);

		mTable.getTableHeader().setReorderingAllowed(false); // column 순서 못바꿈
		
		mTable.setCellSelectionEnabled(true); // 셀 단위로 선택
		mTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 셀 하나만 선택
		
		editable = new boolean[colNum];
	}
	
	private void setColumnNames(String[] columnNames){
		// deep copy
		colNum = columnNames.length;
		colNames = new String[colNum];
		System.arraycopy(columnNames, 0, colNames, 0, colNum);
	}
	
	public void setData(String[][] data){
		// deep copy
		curData = new ArrayList<String[]>();
		for (int i = 0; i < data.length; i++) {
		    curData.add(new String[colNum]);
		    System.arraycopy(data[i], 0, curData.get(i), 0, colNum);
		}
		
		redraw();
	}
	
	/** 해당 컬럼의 편집 가능 여부를 설정한다. */
	public void setEditType(int columnIndex, boolean editable){
		this.editable[columnIndex] = editable;
	}
	
	/** 개별선택 가능여부를 설정한다. */
	public void setSelectable(boolean s){
		if(!s)
			return;
		
		this.selectable = true;
		
		selectedList = new ArrayList<Boolean>();
		for(int i=0; i<curData.size(); i++){
			selectedList.add(false);
		}
		
		mModel.fireTableStructureChanged();
	}
	
	/** 개별삭제 가능여부를 설정한다. */
	public void setDeletable(boolean deletable){
		if(!deletable)
			return;
		
		this.deletable = true;
	}
	
	/** 전체삭제 가능여부를 설정한다. */
	public void setEmptiable(boolean emptiable){
		if(!emptiable)
			return;
		
		this.emptiable = true;
	}
	
	/** 새로운 행을 추가할 때, 추가될 행의 기본값을 지정해 줄 인터페이스를 설정한다. */
	public void setInsertListener(RowProvider insertListener){
		try {
			if (insertListener == null)
				throw new NullPointerException();
		} catch (NullPointerException e) {
			System.out.println("insertListener가 null 입니다.");
			e.printStackTrace();
		}

		this.insertable = true;
		this.insertListener = insertListener;
	}
	
	/** TODO 특정 컬럼에 변화가 생겼을 때 해당하는 작업을 처리할 리스너를 설정한다. */
	public void setTableChangeListener(TableModelListener listener){
		mModel.addTableModelListener(listener);
	}
	
	/** 표가 담겨 있는 패널을 반환한다. 삭제/추가 가능여부 등의 설정은 이전에 완료해야 한다. */
	public JPanel getTablePanel() {
		tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout(0, 0));
		tablePanel.setBorder(new EmptyBorder(5, 10, 5, 10)); // Margin
		
		JPanel northPanel = new JPanel(new GridLayout(0, 2));
		tablePanel.add(northPanel, BorderLayout.NORTH);

		JPanel nwPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		northPanel.add(nwPanel);

		if (deletable) {
			btnDelete = new JButton("삭제");
			nwPanel.add(btnDelete);
		}

		if (emptiable) {
			btnDeleteAll = new JButton("전체삭제");
			nwPanel.add(btnDeleteAll);
		}

		JPanel nePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		northPanel.add(nePanel);

		if (insertable) {
			btnAdd = new JButton("추가");
			nePanel.add(btnAdd);
		}
		
		tableScrollPane = new JScrollPane(mTable);
		tablePanel.add(tableScrollPane, BorderLayout.CENTER);

		setListeners();
		
		return tablePanel;
	}
	
	public Integer[] selectedRowIndex(){
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		
		for(int i=0; i<curData.size(); i++)
			if(selectedList.get(i)) // 선택되었으면
				indexList.add(i);
		
		return listToIntegerArr(indexList);
	}/*어레이리스트 데이터 삭제에 필요 삭제 x*/
	
	private void setListeners() {
		if (deletable) {
			btnDelete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(showConfirmDialog("선택한 항목을 삭제하시겠습니까?")){
						for(int i=curData.size()-1; i>=0; i--){
							if(selectedList.get(i)){ // 선택되었으면
								selectedList.remove(i);
								curData.remove(i);
							}
						}
						mModel.fireTableDataChanged();
						mModel.fireTableChanged(null);
					}
				}
			});
		}

		if (emptiable) {
			btnDeleteAll.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(showConfirmDialog("모두 삭제하시겠습니까?")){
						selectedList.clear();
						curData.clear();
						mModel.fireTableDataChanged();
						mModel.fireTableChanged(null);
					}
				}
			});
		}
		
		if(insertable){
			btnAdd.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String[] newVals = new String[colNum];
					System.arraycopy(insertListener.getNewRowValues(), 0, newVals, 0, colNum);
					curData.add(newVals);
					
					if(selectable)
						selectedList.add(false);
					
					mModel.fireTableDataChanged();
				}
			});
		}
	}
	
	private boolean showConfirmDialog(Object message){
		int result = JOptionPane.showConfirmDialog(tableScrollPane,
				message, "UOSmile", JOptionPane.YES_NO_OPTION);
		
		return result == 0 ? true : false;
	}
	
	private void redraw() {
		if (tablePanel != null) {
			tablePanel.remove(tableScrollPane);
			tableScrollPane = new JScrollPane(mTable);
			tablePanel.add(tableScrollPane, BorderLayout.CENTER);
			
			tablePanel.revalidate();
			tablePanel.repaint();
		}
	}
	
	/** 현재 테이블의 데이터를 모두 반환한다. */
	public String[][] getData(){
		return listToDArr(curData);
	}
	
	
	/** 지정된 셀의 값을 반환한다. */
	public String getValue(int rowIndex, int columnIndex){
		return curData.get(rowIndex)[columnIndex];
	}
	
	/** 지정된 셀에 전달된 값을 설정한다. */
	public void setValue(int rowIndex, int columnIndex, String value) {
		String[] row = curData.get(rowIndex);
		row[columnIndex] = value;
		
		mModel.fireTableDataChanged();
	}
	
	/** 현재 테이블에서 선택된 행을 모두 반환한다. */
	public String[][] getSelectedData(){
		ArrayList<String[]> sList = new ArrayList<String[]>();
		
		for(int i=0; i<curData.size(); i++)
			if(selectedList.get(i)) // 선택되었으면
				sList.add(curData.get(i));
		
		return listToDArr(sList);
	}

	@SuppressWarnings("serial")
	private class SmileTableModel extends AbstractTableModel {
		public SmileTableModel() {
			super();
		}
		
		@Override
		public int getColumnCount() {
			if(selectable)
				return colNum + 1;
			
			return colNum;
		}
		
		@Override
		public String getColumnName(int column) {
			if(selectable){
				if(column == 0)
					return "선택";
				else
					column--;
			}

			return colNames[column];
		}
		
		@Override
		public Class<?> getColumnClass(int colIndex) {
			if(selectable){
				if(colIndex == 0)
					return Boolean.class;
				else
					colIndex--;
			}
			
			return String.class;
		}
		
		@Override
		public int getRowCount() {
			return curData.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int colIndex) {
			if(selectable) {
				if(colIndex == 0)
					return selectedList.get(rowIndex);
				else
					colIndex--;
			}

			return curData.get(rowIndex)[colIndex];
		}
		
		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			if(selectable) {
				if(columnIndex == 0){
					selectedList.set(rowIndex, (Boolean)value);
					return;
				}
				else
					columnIndex--;
			}

			curData.get(rowIndex)[columnIndex] = "" + value;
			fireTableCellUpdated(rowIndex, columnIndex);
			
			if(selectable){
				mTable.changeSelection(rowIndex, columnIndex+1, false, false);
			}
			else{
				mTable.changeSelection(rowIndex, columnIndex, false, false);
			}
		}
		
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if(selectable) {
				if(columnIndex == 0)
					return true;
				else
					columnIndex--;
			}
			
			return editable[columnIndex];
		}
	}
	
	/**
	 * SmileTable 이 새로운 행을 추가할 때, 추가될 행의 기본 값을 제공해 주는 인터페이스.   
	 */
	public interface RowProvider{
		/**
		 * 새로 추가될 행의 기본 값(서버에서 부여할 PK 등)을 반환한다.
		 * @return 추가될 행의 기본 값을 담은 String[]
		 */
		public String[] getNewRowValues();
	}
	
	/** String[] 의 list 를 String[][] 로 변환한다. */
	private static String[][] listToDArr(List<String[]> list){
		if(list.size() == 0)
			return null;
		
		String[][] arr = new String[list.size()][list.get(0).length];
		for(int i=0; i<arr.length; i++){
			System.arraycopy(list.get(i), 0, arr[i], 0, list.get(i).length);
		}
		return arr;
	}
	

	private static Integer[] listToIntegerArr(List<Integer> list){
		if(list.size() == 0)
			return null;
		
		Integer[] idxs = new Integer[list.size()]; 
		System.out.println("인덱스 갯수 : "+list.size());
		for(int i=0;i<list.size();i++)
			idxs[i] = list.get(i);
		return idxs;
	}
	
}

//TODO 속성별 정렬 기능