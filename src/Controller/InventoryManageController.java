package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Model.CompareModel;
import Model.LogIn;
import Model.QueryParser;
import View.ChangesPanel;
import View.InventoryManagePanel;
import View.SmileTable;

public class InventoryManageController extends AbstractController {

	InventoryManagePanel imPanel;
	private SmileTable mTable;

	private String[] columnNames = { "상품번호", "상품코드", "상품명", "가격", "재고수량", "입고일자", "유통기한" };
	private String[][] origData;
	
	int[] pkIndexes = {1};
	
	public InventoryManageController(MainController mainController) {
		super(mainController);
	}

	@Override
	void startMenu() {
		initData();
		
		imPanel = new InventoryManagePanel(columnNames, origData);
		mTable = imPanel.mTable;
		
		showMenuPanel(imPanel);
		
		setListeners();
	}

	public void initData() {
		String query = "select Item_no, ITEM.Item_code, ITEM.Item_name, ITEM.Item_price, Stored_amount, Store_date, Shelf_life "
				+ "from INVENTORY, ITEM "
				+ "where Branch_no=" + LogIn.branchNumber + " and "
				+ "INVENTORY.Item_code=Item.Item_code";
		origData = QueryParser.getResultString2DArr(query, 7);
		
		putCommas(origData, 3);
	}
	
	private void setListeners(){
		imPanel.btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String query;
				int updatedItem=0, insertedItem=0;
				boolean successUpdate = true, successInsert = true ;
				
				String[][] curData = mTable.getData();
				
				String[][] updatedRows = CompareModel.getUpdatedRows(origData, curData, pkIndexes);
				String[][] insertedRows = CompareModel.getAddedRows(origData, curData, pkIndexes);
				
				if(updatedRows == null && insertedRows == null) {
					showMessageDialog("변경사항이 없습니다.");
					return;
				}
				
				putCommas(curData, 3);
				
				if(!showConfirmDialog(new ChangesPanel(origData, curData, columnNames)))
					return;
				
				if(updatedRows != null) {
					for(updatedItem=0; updatedItem<updatedRows.length; updatedItem++) {
						query = "update INVENTORY set Stored_amount=" + updatedRows[updatedItem][4]
								+ "where Branch_no=" + LogIn.branchNumber + " and "
								+ "Item_no=" + updatedRows[updatedItem][0];
						
						if(QueryParser.executeUpdate(query)<1)
							successUpdate = false;
					}
				}
				
				insertedRows[insertedItem][5] = insertedRows[insertedItem][5].equals("") ? "0" : insertedRows[insertedItem][5];
				insertedRows[insertedItem][6] = insertedRows[insertedItem][6].equals("") ? "0" : insertedRows[insertedItem][6];
				
				if(insertedRows != null) {
					for(insertedItem=0; insertedItem<insertedRows.length; insertedItem++) {
						query = "INSERT INTO INVENTORY " +
								"(BRANCH_NO, ITEM_NO, ITEM_CODE, STORED_AMOUNT, STORE_DATE, SHELF_LIFE) VALUES ( "
								+ LogIn.branchNumber + ", " // 지점번호
								+ insertedRows[insertedItem][0] + ", " // 상품번호
								+ insertedRows[insertedItem][1] + ", " // 상품코드
								+ insertedRows[insertedItem][4] + ", " // 재고수량
								+ insertedRows[insertedItem][5] + ", " // 입고일자
								+ insertedRows[insertedItem][6] + " )"; // 유통기한
						
						if(QueryParser.executeUpdate(query)<1)
							successInsert = false;
					}
				}
				
				if(successUpdate && successInsert) { // 에러가 나지 않은 경우
					origData = curData;
					mTable.setData(curData);
					
					String message = new String();
					if(updatedItem > 0)
						message += updatedItem + "개 재고의 정보가 수정되었습니다.\n";
					if(insertedItem > 0)
						message += insertedItem + "개 재고의 정보가 추가되었습니다.\n";
					
					showMessageDialog(message);
				}
				else
					showMessageDialog("오류가 발생하였습니다.");
			}
		});
		
		imPanel.setTableChangeListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent event) {
				if(event.getColumn() == -1)
					return;
				
				int row = event.getFirstRow();
				int col = event.getColumn();
				
				if(row<origData.length && col != 4) {
					showMessageDialog("이미 저장된 상품은 재고수량만 수정 가능합니다.");
					mTable.setValue(row, col, origData[row][col]);
					return;
				}
				
				if(col==1) { // 상품코드
					String itemCode = mTable.getValue(row, col);
					
					String query = "select Item_name, Item_price from ITEM "
							+ "where Item_code=" + itemCode;
					String[] qrs = QueryParser.getResultStringArr(query, 2);
					
					if(qrs == null) {
						showMessageDialog("해당 상품이 없습니다.");
					}
					else {
						mTable.setValue(row, 2, qrs[0]);
						mTable.setValue(row, 3, qrs[1]);
					}
				}
			}
		});
	}
	
	@Override
	public String toString() {
		return "재고 관리";
	}

}