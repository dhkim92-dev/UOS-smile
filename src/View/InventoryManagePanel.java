package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;

import View.SmileTable.RowProvider;

public class InventoryManagePanel extends JPanel {
	private String[] columnNames;

	public SmileTable mTable;
	public JButton btnSave;
	
	public InventoryManagePanel(String[] columnNames, String[][] data) {
		setLayout(new BorderLayout(0, 0));
		setBorder(new EmptyBorder(10, 15, 10, 15));
		
		this.columnNames = columnNames; 
		
		createTable(data);
		add(mTable.getTablePanel(), BorderLayout.CENTER);
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		add(panel, BorderLayout.SOUTH);
		
		btnSave = new JButton("저장");
		panel.add(btnSave);
	}
	
	private void createTable(String[][] data) {
		mTable = new SmileTable(columnNames, data);
		
		mTable.setEditType(0, true); // 상품번호
		mTable.setEditType(1, true); // 상품코드
		mTable.setEditType(4, true); // 재고수량
		mTable.setEditType(5, true); // 입고일자
		mTable.setEditType(6, true); // 유통기한
		
		mTable.setInsertListener(new RowProvider() {
			@Override
			public String[] getNewRowValues() {
				// 빈 row를 반환
				String[] result = new String[columnNames.length];
				for(int i=0; i<columnNames.length; i++)
					result[i] = new String();
				
				return result;
			}
		});
		
	}
	
	public void setTableChangeListener(TableModelListener tableListener) {
		mTable.setTableChangeListener(tableListener);
	}

	public SmileTable getTable() {
		return mTable;
	}
}
