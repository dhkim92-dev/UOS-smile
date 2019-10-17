package Controller;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Model.QueryParser;
import View.SmileTable;

public class ItemListViewController extends AbstractController{
	
	JPanel itemListViewPanel;
	private SmileTable itemListViewTable;
	
	private JButton searchButton;
	private JTextField inputSearchTextField;
	private JComboBox searchComboBox;
	private ArrayList <ArrayList>originalData;
	static private String[][] origData;
	int quantity = 0;
	
	public ItemListViewController(MainController mainController) {
		super(mainController);
		// TODO Auto-generated constructor stub
		
	}
	
	void createItemListPanel()
	{
		this.itemListViewPanel = new JPanel();
		itemListViewPanel.setLayout(new BorderLayout(10, 10));
		itemListViewPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
	
		String[] columnNames = {"상품 코드", "상품 명","분 류","가 격(원)"};
		
		origData = new String[quantity][4];
		
		for(int i=0;i<quantity;i++){
			origData[i][0]=(String) originalData.get(0).get(i);
			origData[i][1]=(String) originalData.get(1).get(i);
			origData[i][2]=(String) originalData.get(3).get(i);
			origData[i][3]=(String) originalData.get(2).get(i);
		}
		itemListViewTable = new SmileTable(columnNames,origData);
		
		itemListViewTable.setSelectable(false);
		itemListViewTable.setDeletable(false);
		itemListViewTable.setEmptiable(false);
		
		JPanel tablePanel = itemListViewTable.getTablePanel();
		itemListViewPanel.add(tablePanel, BorderLayout.CENTER);

		
		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		String[] separation_List = {
							"상품번호",
							"상품명",
							"분류"
							};
		searchComboBox = new JComboBox(separation_List);
		inputSearchTextField = new JTextField(20);
		searchButton = new JButton("검색");
		JLabel separation_Label = new JLabel("검색 조건");
		
		southPanel.add(separation_Label);
		southPanel.add(this.searchComboBox);
		southPanel.add(this.inputSearchTextField);
		southPanel.add(this.searchButton);
		
		itemListViewPanel.add(southPanel,BorderLayout.SOUTH);
	}

	private void setInitialData(){
		//초기 데이터를 쿼리로 날려 받아온다.
		String query = QueryParser.getQueryByID("getItemData");
		ResultSet rawData = QueryParser.executeQuery(query);
		
		if(rawData == null){
			System.out.println("결과 없음");
			return;
		}
				
	this.originalData = new ArrayList();
	ArrayList <String>item_code = new ArrayList();
	ArrayList <String>item_name = new ArrayList();
	ArrayList <String>item_category = new ArrayList();
	ArrayList <String>item_price = new ArrayList();
	ArrayList <String>item_dcrate = new ArrayList();
	ArrayList <String>item_event = new ArrayList();
		// DB 질의 결과를 담을 문자열 배열
		try {
			int i = -1;
			while(rawData.next()){
				quantity++;
				item_code.add(rawData.getString(1));
				item_name.add(rawData.getString(2));
				item_category.add(rawData.getString(3));
				item_price.add(rawData.getString(4));
				item_dcrate.add(rawData.getString(5));
				item_event.add(rawData.getString(6));				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showMessageDialog("갯수세기 실패");
			return;
		}
		
		originalData.add(item_code);
		originalData.add(item_name);
		originalData.add(item_category);
		originalData.add(item_price);
		originalData.add(item_dcrate);
		originalData.add(item_event);
		System.out.println("raw data 수 : "+quantity);
	
			
		showMessageDialog("초기 데이터 설정 완료");
	}
	
	private void setListeners()
	{
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String searchContent = inputSearchTextField.getText();
				String searchDivision = (String) searchComboBox.getSelectedItem();
				int s_quantity=0;
				ArrayList <Integer> index = new ArrayList();
				int sep_code=0;
				if(searchDivision.equals("")){
					searchDivision = "상품명";
				}
				String[][] data;
				switch(searchDivision){
					case "상품명": sep_code=1; break;
					case "분류":  sep_code=2;	break;
					case "상품번호":sep_code=0;break;
					default :break;
				}
				
				for(int i=0;i<quantity;i++){
					if(origData[i][sep_code].equals(searchContent)){
						s_quantity++;
						index.add(i);
					}
				}
				
				if(s_quantity!=0){						
					data=new String[s_quantity][6];
					for(int i=0;i<s_quantity;i++){
						data[i]=origData[index.get(i)];
					}
					if(data!=null){
						itemListViewTable.setData(data);
					}
				}
			}
		});
	}
	
	
	@Override
	void startMenu() {
		quantity=0;
		setInitialData();
		this.createItemListPanel();
		setListeners();
	    this.showMenuPanel(itemListViewPanel);
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "상품 조회";
	}
}
