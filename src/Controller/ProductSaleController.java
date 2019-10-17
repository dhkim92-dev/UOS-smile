package Controller;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Model.LogIn;
import Model.QueryParser;
import View.SmileTable;
import View.SmileTable.RowProvider;

public class ProductSaleController extends AbstractController{

	private JPanel productSalePanel;
	private JLabel lbSum;
	private JTextField tfCustomNum;
	private JButton btnPay;
	private SmileTable mTable;
	
	String[] columnNames;
	int[] pkIndexes = {1};
	private String[][] emptyData; 
	
	int sum;
	
	public ProductSaleController(MainController mainController) {
		super(mainController);
	}

	@Override
	void startMenu() {
		createProductSalePanel();
		setListeners();

		showMenuPanel(productSalePanel);
	}
	
	private void createProductSalePanel(){
		productSalePanel = new JPanel();
		productSalePanel.setLayout(new BorderLayout(10, 10));
		productSalePanel.setBorder(new EmptyBorder(10, 15, 10, 15)); // Margin
		
		columnNames = new String[]{"상품번호", "상품명", "가격", "수량", "합계", "이벤트"};
		emptyData = new String[0][6];
		
		mTable = new SmileTable(columnNames, emptyData);
		
		// 각 열의 편집 가능 여부 지정.
		mTable.setEditType(0, true); // 상품번호
		mTable.setEditType(3, true); // 수량
		
		// 테이블 설정
		mTable.setSelectable(true);	// 개별 선택 가능(앞쪽에 check box 열이 추가됨)
		mTable.setDeletable(true);	// 선택된 row 를 삭제할 수 있음
		mTable.setEmptiable(true);	// 전체 삭제 가능
		mTable.setInsertListener(new RowProvider() { // 새로운 행 추가시 
			@Override
			public String[] getNewRowValues() {
				// 빈 row를 반환
				String[] result = new String[columnNames.length];
				for(int i=0; i<columnNames.length; i++)
					result[i] = new String();
				
				return result;
			}
		});
		mTable.setTableChangeListener(tableListener);	// 테이블의 값이 바뀌었을 때, 변화를 처리할 리스너
		
		
		JPanel tablePanel = mTable.getTablePanel();
		productSalePanel.add(tablePanel, BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel();
		productSalePanel.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		
		JPanel snPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		southPanel.add(snPanel);
		
		JLabel lbSumText = new JLabel("합계   :  ");
		lbSumText.setFont(lbSumText.getFont().deriveFont(15.0f));
		snPanel.add(lbSumText);
		
		lbSum = new JLabel("     0");
		lbSum.setFont(lbSum.getFont().deriveFont(15.0f));
		snPanel.add(lbSum);
		
		JPanel ssPanel = new JPanel();
		ssPanel.setLayout(new BoxLayout(ssPanel, BoxLayout.X_AXIS));
		southPanel.add(ssPanel);
		
		JPanel panel_1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		ssPanel.add(panel_1);
		
		JLabel lbCusNum = new JLabel("고객 번호");
		panel_1.add(lbCusNum);
		
		tfCustomNum = new JTextField();
		panel_1.add(tfCustomNum);
		tfCustomNum.setColumns(10);
		
		JPanel panel_2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		ssPanel.add(panel_2);
		
		btnPay = new JButton("결제");
		panel_2.add(btnPay);
	}
	
	private void setListeners(){
		// 결제 버튼
		btnPay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String[][] resultData = mTable.getData();
				
				// 하나도 입력이 안된 경우
				if(resultData == null) {
					showMessageDialog("결제할 상품이 없습니다.");
					return;
				}
				
				// 상품 번호가 입력 안된 경우
				for(int i=0; i<resultData.length; i++) {
					if(resultData[i][1].equals("")) {
						showMessageDialog((i+1) + "번째 상품 번호가 잘못되었습니다.");
						return;
					}
				}
				
				// 상품 수량이 입력 안된 경우
				for(int i=0; i<resultData.length; i++) {
					try {
						Integer.parseInt(resultData[i][3]);
					} catch(NumberFormatException e) {
						showMessageDialog((i+1) + "번째 상품 수량이 잘못되었습니다.");
						e.printStackTrace();
						return;
					}
				}
				
				String customer = tfCustomNum.getText();
				if(customer.equals("")) {
					customer = "NULL";
				}
				else { // 고객번호 확인
					String q_customer = "select Customer_no from CUSTOMER where Customer_no=" + customer;
					String[] result = QueryParser.getResultStringArr(q_customer, 1);
					if(result == null) {
						showMessageDialog("해당 고객을 찾을 수 없습니다.");
						return;
					}
				}
				
				if(!showConfirmDialog("결제금액은 " + lbSum.getText() + "원 입니다.\n결제하시겠습니까?"))
					return;
				
				
				/* SALE 테이블에 insert - 시작 */
				
				String q_maxSaleNum = "select max(Sale_no) from SALE";
				String[] maxSaleNum = QueryParser.getResultStringArr(q_maxSaleNum, 1);
				long newSaleNum = Long.parseLong(maxSaleNum[0]) + 1;

				SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss", Locale.KOREA );
				Date currentTime = new Date();
				String dTime = formatter.format(currentTime);
				
				String query = "INSERT INTO SALE " +
				"(SALE_NO, BRANCH_NO, CUSTOMER_NO, SALES_AMOUNT, SALE_TIME, SALE_VALIDATION) VALUES ( "
				+ newSaleNum + ", " // 판매번호
				+ LogIn.branchNumber + ", " // 지점번호
				+ customer + ", " // 고객번호
				+ sum + ", " // 판매금액
				+ dTime + ", " // 판매시각
				+ "'Y')"; // 판매유효여부
				
				if( QueryParser.executeUpdate(query) <= 0 ) {
					showMessageDialog("결제 중 오류가 발생하였습니다.");
					System.out.println("'판매' 입력 실패");
					return;
				}
				
				/* SALE 테이블에 insert - 끝 */
				
				/* SALE_LIST 테이블에 insert - 시작 */
				
				for(int i=0; i<resultData.length; i++) {
					query = "insert into SALE_LIST "
					+ "(Sale_no, Item_no, Branch_no, Item_unit_price, Sale_quantity, Sale_sum) values ( "
					+ newSaleNum + ", " // 판매번호
					+ resultData[i][0] + ", " // 상품번호
					+ LogIn.branchNumber + ", " // 지점번호
					+ resultData[i][2] + ", " // 상품가격
					+ resultData[i][3] + ", " // 상품수량
					+ resultData[i][4] + ")"; // 합계
				
					if( QueryParser.executeUpdate(query) <= 0 ) {
						showMessageDialog("결제 중 오류가 발생하였습니다.");
						System.out.println("'판매 목록' 입력 실패");
						return;
					}
				}
				
				/* SALE_LIST 테이블에 insert - 끝 */
				
				
				showMessageDialog(resultData.length + "개 상품의 결제가 완료되었습니다.");
				mTable.setData(emptyData);
			}
		});
	}
	
	private TableModelListener tableListener = new TableModelListener() {
		@Override
		public void tableChanged(TableModelEvent event) {
			
			if(event == null) { // 삭제, 모두삭제인 경우 null 이 전달된다.
				calculateSum();
				return;
			}
			
			if(event.getColumn() == -1)
				return;
			
			int row = event.getFirstRow();
			int col = event.getColumn();
			
			if(col == 0) { // 상품번호
				String query =
						"select ITEM.Item_name, ITEM.Item_price, ITEM.Event_benefit " +
						"from ITEM, INVENTORY " + 
						"where INVENTORY.Branch_no=" + LogIn.branchNumber + 
						" and INVENTORY.Item_no=" + mTable.getValue(row, col) +
						" and INVENTORY.Item_code = ITEM.Item_code";
				
				String[] qrs = QueryParser.getResultStringArr(query, 3);
				
				if(qrs == null) {
					showMessageDialog("해당 상품이 없습니다.");
				}
				else {
					mTable.setValue(row, 1, qrs[0]);
					mTable.setValue(row, 2, qrs[1]);
					mTable.setValue(row, 5, qrs[2]);
				}
			}
			else if (col == 3) { // 수량
				try{
					int price = Integer.parseInt(mTable.getValue(row, 2));
					int quantity = Integer.parseInt(mTable.getValue(row, 3));
					mTable.setValue(row, 4, "" + (price * quantity));

					calculateSum();
					
				} catch(NumberFormatException e){
					showMessageDialog("수량에 숫자를 입력해 주세요.");
					e.printStackTrace();
				}
			}
		}
	};
	
	@Override
	public String toString() {
		return "상품 판매";
	}
	
	private void calculateSum(){
		sum = 0;
		String[][] curData = mTable.getData(); 
		
		if(curData == null) { // 테이블이 비어 있는 경우 
			lbSum.setText("" + sum);
			return;
		}
		
		for(int i=0; i<curData.length; i++) 
			sum += Integer.parseInt(curData[i][4]);
		
		lbSum.setText("" + sum);
	}
}
