package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Model.LogIn;
import Model.QueryParser;
import View.ProductRefundPanel;
import View.SalesPanel;
import View.SmileTable;

public class SalesController extends AbstractController {

	SalesPanel salesPanel;
	private SmileTable mTable;
	
	private String[][] data;
	
	public SalesController(MainController mainController) {
		super(mainController);
		// TODO Auto-generated constructor stub
	}

	@Override
	void startMenu() {
		initData();
		
		salesPanel = new SalesPanel(data);
		mTable = salesPanel.mTable;
		
		showMenuPanel(salesPanel);
		
		setListeners();
	}

	public void initData() {
		String query = "select Sale_no, Sales_amount, Sale_time, Customer_no, Sale_validation "
				+ "from SALE "
				+ "where Branch_no=" + LogIn.branchNumber;
		data = QueryParser.getResultString2DArr(query, 5);
		
		putCommas(data, 1);
	}
	
	private void setListeners(){
		salesPanel.btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String[] colNames = {"상품명", "가격", "수량", "합계"};
				String query = "select Item_name, Item_unit_price, Sale_quantity, Sale_sum "
						+ "from ITEM, SALE_LIST, INVENTORY "
						+ "where SALE_LIST.Branch_no=" + LogIn.branchNumber + " and "
						+ "SALE_LIST.Sale_no=" + getSaleNo() + " and "
						+ "SALE_LIST.Item_no=INVENTORY.Item_no and "
						+ "INVENTORY.Item_code=ITEM.Item_code";
				
				String[][] saleList = QueryParser.getResultString2DArr(query, 4);
				
				if(saleList == null){
					showMessageDialog("해당 판매목록을 찾을 수 없습니다.");
					return;
				}
				
				putCommas(saleList, 1);
				putCommas(saleList, 3);
				
				SmileTable tbSaleList = new SmileTable(colNames, saleList);
				showMessageDialog(tbSaleList.getTablePanel());
			}
		});
	}
	
	@Override
	public String toString() {
		return "판매내역 조회";
	}

	private String getSaleNo(){
		return salesPanel.tfSaleNum.getText();
	}
}
