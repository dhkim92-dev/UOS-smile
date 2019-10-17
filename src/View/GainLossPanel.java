package View;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Component;
import java.util.List;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class GainLossPanel extends JPanel {
	
	String[] columnNames = {" "};
	
	public SmileTable mTable;
	public JComboBox<String> cbYear, cbMonth;
	public JButton btnSearch;
	
	public GainLossPanel(String[][] data) {
		Vector<String> years = new Vector<String>();
		for(int i=2010; i<2050; i++)
			years.add("" + i);
		
		Vector<String> months = new Vector<String>();
		for(int i=1; i<13; i++)
			months.add("" + i);
		
		setLayout(new BorderLayout(0, 0));
		setBorder(new EmptyBorder(10, 15, 10, 15));
		
		createTable(data);
		add(mTable.getTablePanel(), BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JPanel panel_1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(panel_1);
		
		cbYear = new JComboBox<String>(years);
		cbYear.setSelectedIndex(4);
		panel_1.add(cbYear);

		panel_1.add(new JLabel(" 년"));

		cbMonth = new JComboBox<String>(months);
		cbMonth.setSelectedIndex(5);
		panel_1.add(cbMonth);
		
		panel_1.add(new JLabel(" 월"));
		
		btnSearch = new JButton("손익 조회");
		panel_1.add(btnSearch);
	}
	
	private void createTable(String[][] data) {
		mTable = new SmileTable(columnNames, data);
	}

	public SmileTable getTable(){
		return mTable;
	}
	
	public String getYear(){
		return (String) cbYear.getSelectedItem();
	}
	
	public String getMonth(){
		return (String) cbMonth.getSelectedItem();
	}
}
