package Controller;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import Model.QueryParser;
import View.SmileTable;

public class OrderListViewController extends AbstractController{
	class OrderList{
		public String orderNo;
		public String itemCode;
		public String orderQuantity;
		
		public OrderList(String s1,String s2,String s3){
			this.orderNo = s1;
			this.itemCode = s2;
			this.orderQuantity = s3;
		}
		public String toString()
		{
			return "주문번호 : " + orderNo + " 상품코드 : " + itemCode + " 주문수량 : " + orderQuantity;
		}
	}
	
	public class Order{
		String orderNo;
		String branchNo;
		String orderDate;
		String orderState;
		
		public Order(String s1,String s2,String s3,String s4){
			this.orderNo = s1;
			this.branchNo = s2;
			this.orderDate = s3;
			this.orderState = s4;
		}
	}
	
	class Item{
		String itemCode;
		String itemName;
		String itemCategory;
		String itemPrice;
		public Item(String s1,String s2,String s3,String s4){
			this.itemCode = s1;
			this.itemName = s2;
			this.itemCategory = s3;
			this.itemPrice = s4;
		}
	}
	
	int curOrderListCount=0;
	int oQuantity=0;
	int olQuantity=0;
	int iQuantity=0;
	
	String[] itemColumnNames = {"상품번호","상품이름","상품분류","상품가격","발주수량"};
	String[] orderListColumnNames = {"발주번호","상품코드","발주수량"};
	
	private ArrayList<Integer> selectedItemRowIndex;
	private ArrayList<OrderList> curOrderListDataArrayList;
	
	private JPanel orderTablePanel;
	private JPanel orderSouthPanel;
	
	private SmileTable itemListTable;
	private SmileTable orderListTable;
	private SmileTable orderCheckTable;
	private SmileTable orderTable;
	
	private OrderList[] orderListDatas;
	private OrderList[] curOrderList;
	private Order[] orderDatas;
	private Item[] itemDatas;
	
	private JPanel orderListViewPanel;
	private JPanel itemListPanel;
	private JPanel orderPanel;
	private JPanel subNorthPanel;
	private JPanel subCenterPanel;
	private JPanel subSouthPanel;
	private JPanel orderListTablePanel;
	private JPanel itemListTablePanel;
	//private JPanel subCenterRightPanel;
	
	private JButton add_Order_Button;
	private JButton order_Cancle_Button;
	private JButton order_List_Check_Button;
	private JButton order_Item_Remove_Button;
	private JButton order_Item_Add_Button;
	private JButton order_Confirm_Button;
	
	JFrame subFrame ;
	
	public OrderListViewController(MainController mainController) {
		super(mainController);
		// TODO Auto-generated constructor stub
	}

	void createOrderListMaintPanel(){
		this.orderPanel = new JPanel();
		orderPanel.setLayout(new BorderLayout(10, 10));
		orderPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
	
		String[] orderColumnNames = {"발주번호","지점번호","발주일자","발주상태"};
		String[][] data = new String[oQuantity][4];
		
		
		for(int i = 0 ;i< oQuantity;i++){
			System.out.println("+" + orderDatas[i].orderNo);
			data[i][0] = orderDatas[i].orderNo;
			data[i][1] = orderDatas[i].branchNo;
			data[i][2] = orderDatas[i].orderDate;
			data[i][3] = orderDatas[i].orderState;
		}
		
		
		
		orderTable = new SmileTable(orderColumnNames,data);

		orderTable.setSelectable(true);
		orderTable.setDeletable(false);
		orderTable.setEmptiable(false);
		
		orderTablePanel = orderTable.getTablePanel();
		orderPanel.add(orderTablePanel, BorderLayout.CENTER);
		
		orderSouthPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		add_Order_Button = new JButton("추가 발주");
		this.order_List_Check_Button = new JButton("발주 내용");
		order_Cancle_Button = new JButton("발주 취소");
		
		/*서브화면용 컴포넌트 초기화*/
		order_Item_Add_Button = new JButton("발주상품추가");
		order_Item_Remove_Button = new JButton("발주상품삭제");
		order_Confirm_Button = new JButton("발주신청");
		/*---------------*/
		orderSouthPanel.add(add_Order_Button);
		orderSouthPanel.add(order_List_Check_Button);
		orderSouthPanel.add(order_Cancle_Button);
		orderPanel.add(orderSouthPanel,BorderLayout.SOUTH);
	}
	
	void createSubPanel(){
		String[][] itemData = new String[iQuantity][5];
		String[][] initialOrderList = new String[256][4];         
		
		
		curOrderListDataArrayList = new ArrayList();
		orderPanel.remove(orderTablePanel);
		orderPanel.remove(orderSouthPanel);
		curOrderListCount=0;
		
		System.out.println("상품 갯수 : " + iQuantity);
		for(int i = 0 ;i< iQuantity;i++){
			System.out.println(itemDatas[i].itemCode);
			System.out.println(itemDatas[i].itemName);
			System.out.println(itemDatas[i].itemCategory);
			System.out.println(itemDatas[i].itemPrice);
			itemData[i][0] = itemDatas[i].itemCode;
			itemData[i][1] = itemDatas[i].itemName;
			itemData[i][2] = itemDatas[i].itemCategory;
			itemData[i][3] = itemDatas[i].itemPrice;
		}
		
		
		this.subCenterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		this.subNorthPanel  = new JPanel(new BorderLayout());
		this.itemListTable = new SmileTable(itemColumnNames,itemData);
		
		itemListTable.setSelectable(true);
		itemListTable.setDeletable(false);
		itemListTable.setEmptiable(false);
		itemListTable.setEditType(4, true);
		
		this.orderListTable = new SmileTable(orderListColumnNames,initialOrderList);		
		this.orderListTablePanel = this.orderListTable.getTablePanel();
		this.itemListTablePanel = this.itemListTable.getTablePanel();
		
		orderListTable.setSelectable(true);
		orderListTable.setDeletable(false);
		orderListTable.setEmptiable(false);
				
		subCenterPanel.add(this.orderListTablePanel);
				
		subSouthPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		subSouthPanel.add(order_Item_Remove_Button);
		subSouthPanel.add(order_Confirm_Button);
		
		orderPanel.add(subCenterPanel,BorderLayout.NORTH);
		
		/*별도의 창으로 상품 목록을 띄워줌*/
		subFrame = new JFrame("상품목록");
		
		subFrame.setSize(540,580);
		JPanel subFramePanel = new JPanel(new BorderLayout());
		JScrollPane sp = new JScrollPane(itemListTablePanel);
		sp.setSize(540,380);
		subFramePanel.setSize(540,580);
		subNorthPanel.add(sp);
		subFramePanel.add(subNorthPanel,BorderLayout.CENTER);
		JPanel subBottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		subBottomPanel.add(order_Item_Add_Button);
		subFramePanel.add(subBottomPanel,BorderLayout.SOUTH);
		
		subFrame.add(subFramePanel);
		subFrame.setVisible(true);
		
		orderPanel.add(subCenterPanel,BorderLayout.CENTER);
		orderPanel.add(subSouthPanel,BorderLayout.SOUTH);
	}
	
	private void addCurrentOrderListArray(OrderList obj){
		
		if(!this.curOrderListDataArrayList.isEmpty()){
			for(int i = 0 ;i<curOrderListDataArrayList.size();i++){
				 if(curOrderListDataArrayList.get(i).itemCode.equals(obj.itemCode)){
					    System.out.println("같아욧!!");
					 	OrderList tmpObj = curOrderListDataArrayList.get(i);
					 	System.out.println("수량 : "+ obj.orderQuantity);
					 	int a = Integer.parseInt(obj.orderQuantity);
					 	int b = Integer.parseInt(tmpObj.orderQuantity);
					 	int tempQ=a+b; 
					 	System.out.println(a + "+" + b+"="+ tempQ);
					 	tmpObj.orderQuantity = Integer.toString(tempQ);
					 	return ;
			 	}
			}
		}
		
		this.curOrderListDataArrayList.add(obj);
		System.out.println("그냥추가")	;
		return ;
	}
	
	private void setCurOrderListTableView(){
		orderListTable.setData(this.DataListToDataStringArray());
	}
	
	private String[][] DataListToDataStringArray(){
		String[][] str = new String[curOrderListDataArrayList.size()][3];
		for(int i = 0;i<curOrderListDataArrayList.size();i++){
				str[i][0] = curOrderListDataArrayList.get(i).orderNo;
				str[i][1] = curOrderListDataArrayList.get(i).itemCode;
				str[i][2] = curOrderListDataArrayList.get(i).orderQuantity;
		}
		return str;
	}
	
	private void removeFromOrderListArray(String[][] rdata){
		for(int i=0;i<curOrderListDataArrayList.size();i++){
			for(int j=0;j<rdata.length;j++){
				if(curOrderListDataArrayList.get(i).itemCode.equals(rdata[j][1])){
					System.out.println("매치! 삭제 아이템 코드 : " + rdata[j][1] + " 검색된 코드 : "+curOrderListDataArrayList.get(i).itemCode);
					this.curOrderListDataArrayList.remove(i);
				}	
			}
		}
		DataListToDataStringArray();
	}
	
	
	private int getCurOrderListSize(){
		return curOrderListDataArrayList.size();
	}
	
	void setInitItemData(){
		String query = QueryParser.getQueryByID("getItemData");
		ResultSet rawData = QueryParser.executeQuery(query);
		
		try {
			if(rawData == null){
				System.out.println("결과 없음");
				rawData.close();
				return;
			}
			iQuantity = QueryParser.getResultSetRowQuantity(rawData);
			this.itemDatas = new Item[iQuantity];
			int i = 0;
			while(rawData.next()){
				itemDatas[i] = new Item(rawData.getString(1),rawData.getString(2),rawData.getString(3),rawData.getString(4));
				i++;
			}
			rawData.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void setInitOrderData(){
		String query = QueryParser.getQueryByID("getOrderData");
		ResultSet rawData = QueryParser.executeQuery(query);
		try {
			if(rawData == null){
				System.out.println("결과 없음");
				return;
			}
			
		    oQuantity=QueryParser.getResultSetRowQuantity(rawData);
			
			orderDatas = new Order[oQuantity];
			System.out.println("oQuantity : "+oQuantity);			
			int i = 0;
			while(rawData.next()){
				orderDatas[i] = new Order(rawData.getString(1), rawData.getString(2), rawData.getString(3), rawData.getString(4));
				System.out.println("데이터 삽입 ["  + i +  "]:" + orderDatas[i].orderNo +"|" + orderDatas[i].branchNo + "|"+ orderDatas[i].orderDate +"|" + orderDatas[i].orderState );
				i++;
			}
			rawData.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void setInitOrderListData(){
		String query = QueryParser.getQueryByID("getOrderListData");
		ResultSet rawData = QueryParser.executeQuery(query);
		try {
			if(rawData == null){
				System.out.println("결과 없음");
				rawData.close();
				return;
			}
			
			olQuantity = QueryParser.getResultSetRowQuantity(rawData);
			orderListDatas = new OrderList[olQuantity];
		
			int i = 0;
			while(rawData.next()){
				orderListDatas[i]=new OrderList(rawData.getString(1),rawData.getString(2),rawData.getString(3));
				i++;
			}
			rawData.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private OrderList[] getOrderNoOrderList(String orderNo){
		int count = 0;
		for(int i = 0 ; i < orderListDatas.length ; i++){
				if(orderNo.equals(orderListDatas[i].orderNo)){
					count++;
				}
		}
		OrderList[] result = new OrderList[count];
		
		int j = 0;
		for(int i = 0 ; i < orderListDatas.length ; i++){
			if(orderNo.equals(orderListDatas[i].orderNo)){
				result[j] = new OrderList(orderListDatas[i].orderNo,orderListDatas[i].itemCode,orderListDatas[i].orderQuantity);
				j++;
			}
	}
		
		return result;
	}
	
	private String makeOrderNumber(){
		Calendar cal = Calendar.getInstance();
		String year = new String(""+cal.get(Calendar.YEAR));
		String month = new String(""+cal.get(Calendar.MONTH));
		String day_of_month = new String(""+cal.get(Calendar.DAY_OF_MONTH));
		
		month = Integer.toString(Integer.parseInt(month) + 1);
		
		if(day_of_month.length()==1){
			day_of_month = new String("0"+day_of_month);
		}
		
		if(month.length()==1){
			month=new String("0"+month);
		}
		
		cal.get(Calendar.DAY_OF_MONTH);
	
		String ordNo= new String("1000001"+year+month+day_of_month+"1");
		
		System.out.println("ordNo : "+ordNo + " 길이 : " + ordNo.length());
		return ordNo;
	}
	
		private String[][] getOrderDataStringArray(){
			String [][] result = new String[orderDatas.length][4];
			
			int i,j;
			i=0; j=0;
			
			for(i=0;i<orderDatas.length;i++){
					result[i][0] = orderDatas[i].orderNo;
					result[i][1] = orderDatas[i].branchNo;
					result[i][2] = orderDatas[i].orderDate;
					result[i][3] = orderDatas[i].orderState;
			}
			
			return result;
		}
	
	@Override
	void startMenu() {
		oQuantity = 0;
		this.setInitOrderData();
		this.setInitItemData();
		this.setInitOrderListData();
		this.createOrderListMaintPanel();
		setListeners();
		this.showMenuPanel(orderPanel);
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "발주 관리";
	}
	
	private void setListeners()
	{
		add_Order_Button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
			      createSubPanel();
			      showMenuPanel(orderPanel);
			      System.out.println("발주완료");
			}
		});
		
		order_Item_Add_Button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				// row 가 아닌 String data 를 반환해 주는 식으로 구현돼 있음
				String[][] selectedRows = itemListTable.getSelectedData();
				//System.out.println("선택 로우 길이 : " + selectedRows[0].length);
			/*	for(int i=0; i<selectedRows.length; i++){
					for(int j=0; j<selectedRows[i].length; j++) {
					//	System.out.printf(selectedRows[i][j] + " ");
					}
					System.out.println();
				}
				*/
				for(int i = 0 ; i < selectedRows.length ; i++){
					OrderList obj = new OrderList(makeOrderNumber(),selectedRows[i][0],selectedRows[i][4]);
					System.out.println("추가된 값 " + obj.toString());
						try{
							Integer.parseInt(obj.orderQuantity);
						}catch(NumberFormatException e){
							e.printStackTrace();
							showMessageDialog("수량에는 숫자만 입력해야합니다.");
							return;
						}
					addCurrentOrderListArray(obj);
				}
				System.out.println("발주 목록 리스트 크기 : " + getCurOrderListSize());
				setCurOrderListTableView();
				return ;
			}
		});

		
		this.order_Item_Remove_Button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				if(curOrderListDataArrayList.size()==0){
					showMessageDialog("선택된 아이템이 없습니다.");
					return;
				}
				//Integer[] idxs = orderListTable.selectedRowIndex();
				String[][] rdata = orderListTable.getSelectedData();
				removeFromOrderListArray(rdata);
				setCurOrderListTableView();
			}
		});
		
		this.order_Confirm_Button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				/*
				 * 1. 발주번호를 생성한다.
				 * 2. 발주번호는 하루에 한개뿐이다. 
				 * 3. 발주번호를 생성했다면, 해당 발주번호에 발주 엔티티를 구성한다. 
				 *    만약 이미 발주번호가 이미 존재한다면, 발주목록만 추가하면된다.
				 * 4. 발주 엔티티를 형성했다면, 현재 발주목록에 추가된 데이터들로 발주목록 엔티티를 구성한다.
				 * 5. query를 날린다.
				 */
				String orderNo = makeOrderNumber();
				String branchNo = "1000001";
				String orderDate = orderNo.substring(7, 11)+"-"+orderNo.substring(11,13)+"-"+orderNo.substring(13,15);
				int exist_flag = 0;
				
				for(int i = 0;i<orderDatas.length;i++){
					if(orderDatas[i].orderNo.equals(orderNo)){
						exist_flag = 1;
					}
				}
				
				if(exist_flag!=1){
					String query1 = new String("insert into orders values('"+orderNo + "','" + branchNo + "','" + orderDate + "', '발주준비')");
					try{
						ResultSet rs1 = QueryParser.executeQuery(query1);
						System.out.println("query1 : " + query1);
						rs1.close();
					}catch (SQLException e){
						e.printStackTrace();
					}					
				}
				
				/* 현재발주목록 테이블의 값들을 String [][] 로 받아오기*/
				
				for(int i = 0; i < curOrderListDataArrayList.size();i++){
					String argv1= curOrderListDataArrayList.get(i).orderNo;
					String argv2 = curOrderListDataArrayList.get(i).itemCode;
					String argv3 = curOrderListDataArrayList.get(i).orderQuantity;
					int list_flag = 0;
					int existIndex=0;
					
					for(int j=0;j<orderListDatas.length;j++){
						if(orderListDatas[j].orderNo.equals(argv1) && orderListDatas[j].itemCode.equals(argv2)){
							existIndex=j;
							list_flag = 1;
						}
					}
					
					if(list_flag==0){
						String query2 = "insert into order_list values('"+argv1+"','"+argv2+"','"+argv3+"')";
						try{
							ResultSet rs = QueryParser.executeQuery(query2);
							rs.close();
						}catch (SQLException e){
							e.printStackTrace();
						}
					}else{
						int quantity = Integer.parseInt(argv3) + Integer.parseInt(orderListDatas[existIndex].orderQuantity);
						String query2 = "update order_list set order_amount = '" + quantity+"' where order_no='"+argv1 + "' and item_code='" + argv2 +"'";
						QueryParser.executeQuery(query2);
					};
				}
				QueryParser.executeQuery("commit");
				subFrame.setVisible(false);
				setInitOrderData();
				setInitOrderListData();
				subSouthPanel.remove(order_Item_Remove_Button);
				subSouthPanel.remove(order_Confirm_Button);
						
				orderPanel.add(orderTablePanel,BorderLayout.CENTER);
				orderPanel.add(orderSouthPanel,BorderLayout.SOUTH);
				
				orderPanel.remove(orderTablePanel);
				orderPanel.remove(orderSouthPanel);
				
				orderPanel.add(orderTablePanel,BorderLayout.CENTER);
				orderPanel.add(orderSouthPanel,BorderLayout.SOUTH);
								
				String[][] data = new String[orderDatas.length][4];
				for(int i = 0 ;i< oQuantity;i++){
					System.out.println("+" + orderDatas[i].orderNo);
					data[i][0] = orderDatas[i].orderNo;
					data[i][1] = orderDatas[i].branchNo;
					data[i][2] = orderDatas[i].orderDate;
					data[i][3] = orderDatas[i].orderState;
				}
				orderTable.setData(data);
			}
		});
		
		this.order_List_Check_Button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){try{
					String orderNo;
					String[][] selectedData = orderTable.getSelectedData();
					String[][] matchedData;
	
					System.out.println("데이터 길이 : "+selectedData.length);
					for(int i=0;i<selectedData.length;i++){
						OrderList[] matchedResult = getOrderNoOrderList(selectedData[i][0]);
						matchedData = new String[matchedResult.length][3];
						
						for(int j=0;j<matchedResult.length;j++){
							System.out.println("들어가긴 했다!");
							matchedData[j][0]=matchedResult[j].orderNo;
							matchedData[j][1]=matchedResult[j].itemCode;
							matchedData[j][2]=matchedResult[j].orderQuantity;
							System.out.println("|"+matchedData[j][0]+"|"+matchedData[j][1]+"|"+matchedData[j][2]+"|");
						}

						JFrame orderInfoFrame = new JFrame("발주 내역"+i);
						JPanel mainPanel = new JPanel(new BorderLayout());
						
						//System.out.println("발주데이터 : "+matchedData[0][0] + " | " + matchedData[0][1] + " | " + matchedData[0][2] );
						
						SmileTable listTable = new SmileTable(orderListColumnNames,matchedData);
						JPanel tablePanel = listTable.getTablePanel();
						mainPanel.add(tablePanel,BorderLayout.CENTER);
						orderInfoFrame.add(mainPanel);
						orderInfoFrame.setSize(500,500);
						orderInfoFrame.setResizable(false);
						orderInfoFrame.setVisible(true);
					}
				}catch(NullPointerException e){
					e.printStackTrace();
					showMessageDialog("발주내역을 선택하십시오");
				}
			}
			
		});
		
		this.order_Cancle_Button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
					/*
					 * 1. 발주상태를 확인한다
					 * 2. 발주 준비 상태인가?
					 * (1) yes -> 취소 가능 
					 * 선택한 테이블의 order_no를 얻어온다
					 * SQL 쿼리를 날린다.
					 * delete from order_list where order_no = %s ;
					 * delete from order where order_no = %s; 
					 * 기본 데이터 셋들을 다시 LOAD 한다.
					 * 패널을 다시 그린다.
					 * */
					
					/* 발주 상태 확인 */
					
					String[][] selectedData = orderTable.getSelectedData();
					
					for(int i = 0 ; i < selectedData.length; i++){
						if(selectedData[i][3].equals("발주준비")){
							String order_no = selectedData[i][0];
							String query1 = new String("delete from order_list where order_no = '"+order_no+"'");
							System.out.println("Query 1 : " + query1);
							String query2 = new String("delete from orders where order_no = '"+order_no+"'");
							System.out.println("Query 2 :" + query2);
							
							try {
								ResultSet rs1 = QueryParser.executeQuery(query1);
								rs1.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try {
								System.out.println("--------0---------");
								ResultSet rs2 = QueryParser.executeQuery(query2);
								System.out.println("--------1---------");
								rs2.close();
								System.out.println("--------2---------");
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("--------3---------");
							setInitOrderData();
							orderTable.setData(getOrderDataStringArray());
						}else{
							showMessageDialog("발주준비 상태가 아닌 발주는 취소 불가능합니다.");
						}
					}
				}
		});
	}
}



