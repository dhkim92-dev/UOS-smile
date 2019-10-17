package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Model.LogIn;
import Model.QueryParser;
import View.ProductRefundPanel;
import View.SmileTable;

public class ProductRefundController extends AbstractController {

	ProductRefundPanel refundPanel;
	private SmileTable mTable;
	
	private String[][] data;
	
	int[] pkIndexes = {1};
	
	public ProductRefundController(MainController mainController) {
		super(mainController);
		// TODO Auto-generated constructor stub
	}

	@Override
	void startMenu() {
		initData();
		
		refundPanel = new ProductRefundPanel(data);
		mTable = refundPanel.mTable;
		
		showMenuPanel(refundPanel);
		
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
		refundPanel.btnSearch.addActionListener(new ActionListener() {
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
				
				putCommas(saleList, 1);
				putCommas(saleList, 3);
				
				if(saleList == null){
					showMessageDialog("해당 판매목록을 찾을 수 없습니다.");
					return;
				}
				
				SmileTable tbSaleList = new SmileTable(colNames, saleList);
				showMessageDialog(tbSaleList.getTablePanel());
			}
		});
		
		refundPanel.btnCancelRefund.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String saleNo = null;
				int index;
				
				for(index=0; index<data.length; index++) {
					if(data[index][0].equals(getSaleNo())) {
						saleNo = getSaleNo();
						break;
					}
				}
				
				if(saleNo == null) {
					showMessageDialog("목록에 있는 판매 번호를 입력 해 주세요.");
					return;
				}
				
				if(!data[index][4].equals("N")) {
					showMessageDialog("환불된 상품이 아닙니다.");
					return;
				}
					
				if(showConfirmDialog(saleNo + "번 판매의 환불을 취소하시겠습니까?")){
					String query = "update SALE set SALE_VALIDATION='Y'"
							+ "where Branch_no=" + LogIn.branchNumber + " and "
							+ "Sale_no=" + saleNo;
					if(QueryParser.executeUpdate(query)>0){
						showMessageDialog("환불이 취소되었습니다.");
						initData();
						mTable.setData(data);
						return;
					}
					else {
						showMessageDialog("환불이 취소 중 오류가 발생하여\n환불을 취소하지 못했습니다.");
						return;
					}
				}
			}
		});
		
		refundPanel.btnRefund.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String saleNo = null;
				int index;
				
				for(index=0; index<data.length; index++) {
					if(data[index][0].equals(getSaleNo())) {
						saleNo = getSaleNo();
						break;
					}
				}
				
				if(saleNo == null) {
					showMessageDialog("목록에 있는 판매 번호를 입력 해 주세요.");
					return;
				}
				
				if(data[index][4].equals("N")) {
					showMessageDialog("이미 환불된 상품입니다.");
					return;
				}
					
				if(showConfirmDialog(saleNo + "번 판매를 환불 하시겠습니까?")) {
					String query = "update SALE set SALE_VALIDATION='N'"
							+ "where Branch_no=" + LogIn.branchNumber + " and "
							+ "Sale_no=" + saleNo;
					if(QueryParser.executeUpdate(query)>0){
						showMessageDialog("환불을 완료하였습니다.");
						initData();
						mTable.setData(data);
						return;
					}
					else {
						showMessageDialog("환불 처리 중 오류가 발생하여\n환불을 진행하지 못했습니다.");
						return;
					}
				}
			}
		});
	}
	
	@Override
	public String toString() {
		return "상품 환불";
	}

	private String getSaleNo(){
		return refundPanel.tfSaleNum.getText();
	}
}
