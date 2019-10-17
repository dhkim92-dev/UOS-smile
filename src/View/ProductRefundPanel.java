package View;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Component;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class ProductRefundPanel extends JPanel {
	
	String[] columnNames = {"판매번호", "판매금액", "판매시각", "고객번호", "판매유효여부"};
	
	public SmileTable mTable;
	public JTextField tfSaleNum;
	public JButton btnSearch, btnRefund, btnCancelRefund;
	private JPanel panel_2;
	
	public ProductRefundPanel(String[][] data) {
		setLayout(new BorderLayout(0, 0));
		setBorder(new EmptyBorder(10, 15, 10, 15));
		
		createTable(data);
		add(mTable.getTablePanel(), BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JPanel panel_1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(panel_1);
		
		tfSaleNum = new JTextField();
		panel_1.add(tfSaleNum);
		tfSaleNum.setColumns(10);
		
		btnSearch = new JButton("판매목록 조회");
		panel_1.add(btnSearch);
		
		btnCancelRefund = new JButton("환불 취소");
		panel_1.add(btnCancelRefund);
		
		panel_2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.add(panel_2);
		
		btnRefund = new JButton("환불");
		panel_2.add(btnRefund);
		
	}
	
	private void createTable(String[][] data) {
		mTable = new SmileTable(columnNames, data);
	}

	public SmileTable getTable(){
		return mTable;
	}
	
	public String getSaleNumber(){
		return tfSaleNum.getText();
	}
}
