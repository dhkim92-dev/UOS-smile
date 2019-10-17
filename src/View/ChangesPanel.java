package View;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * 인자로 전달된 두 데이터를 각각 테이블 형태로 만들어 덧붙인 패널.
 */
public class ChangesPanel extends JPanel {
	
	/**
	 * 인자로 전달된 두 데이터를 각각 테이블 형태로 만들어 덧붙인 패널.
	 * 마지막 인자로는 column 들의 이름을 전달한다. 
	 */
	public ChangesPanel(String[][] originalData, String[][] currentData, String[] colNames) {
		super();
		
		JTable origTable, curTable;
		
		origTable = new JTable(originalData, colNames);

		Vector<Vector> curVector = new Vector<Vector>();
		if (currentData != null) {
			for (int i = 0; i < currentData.length; i++) {
				Vector<String> tempRow = new Vector<String>();
				for (int j = 0; j < currentData[i].length; j++)
					tempRow.addElement(currentData[i][j]);
				curVector.addElement(tempRow);
			}
		}
		
		Vector<String> colNamesVector = new Vector<String>();
		for(int i=0; i<colNames.length; i++){
			colNamesVector.addElement(colNames[i]);
		}
		
		curTable = new JTable(curVector, colNamesVector);

		  
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p1.add(new JLabel("아래와 같이 변경되었습니다. 계속 진행하시겠습니까?"));
		p1.setBorder(new EmptyBorder(0, 30, 10, 0)); // Margin
		
		JPanel p2 = new JPanel(new GridLayout(0, 2));
		p2.add(new JLabel("변경 전"));
		p2.add(new JLabel("변경 후"));

		JPanel p3 = new JPanel(new GridLayout(0, 2));
		JScrollPane origPane = new JScrollPane(origTable);
		origPane.setBorder(new EmptyBorder(0, 0, 0, 10)); // Margin
		p3.add(origPane);
		JScrollPane curPane = new JScrollPane(curTable);
		curPane.setBorder(new EmptyBorder(0, 0, 0, 0)); // Margin
		p3.add(curPane);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(0, 20, 10, 0)); // Margin
		add(p1);
		add(p2);
		add(p3);
	}
	
	/**
	 * 인자로 전달된 두 데이터를 각각 테이블 형태로 만들어 덧붙인 패널.
	 * 마지막 인자로는 column 들의 이름을 전달한다. 
	 */
	public ChangesPanel(List<String[]> originalData, List<String[]> currentData, String[] colNames) {
		this(listToDArr(originalData), listToDArr(currentData), colNames);
	}
	
	/**
	 * 인자로 전달된 두 데이터를 각각 테이블 형태로 만들어 덧붙인 패널.
	 * 마지막 인자로는 column 들의 이름을 전달한다. 
	 */
	public ChangesPanel(List<String[]> originalData, String[][] currentData, String[] colNames) {
		this(listToDArr(originalData), currentData, colNames);
	}
	
	/**
	 * 인자로 전달된 두 데이터를 각각 테이블 형태로 만들어 덧붙인 패널.
	 * 마지막 인자로는 column 들의 이름을 전달한다. 
	 */
	public ChangesPanel(String[][] originalData, List<String[]> currentData, String[] colNames) {
		this(originalData, listToDArr(currentData), colNames);
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
}
