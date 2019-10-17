package Controller;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JComboBox;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import Model.QueryParser;
import View.SmileTable;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class SaleReturnController extends AbstractController{

	class returnList{
		String returnNo,branchNo,itemNo;
		String returnDate;
		String returnQuantity;
		String returnReason;
		String returnCondition;
		String reStored;
	}
	int quantity;
	
	returnList[] rListDatas;
	String[][] rListStringDatas; 
	
	String branchNo="1000001";
	/*returnListFrame에 붙인다.*/
	JFrame returnListFrame;
	JButton yesButton,noButton; //rFrameSouthPanel에 붙임.
	JComboBox restoredCheckBox;
	
	JPanel rFrameNorthPanel,rFrameSouthPanel,rFrameCenterPanel;
	JPanel rFrameNorthWestPanel,rframeNorthEastPanel;
	JPanel rFrameCenterEastPanel,rFrameCenterWestPanel;
	JTextArea rrTextArea,rcTextArea;
	JTextField rrDateField,rrNumberField; //DateField는 20140404 형태로 저장받아야함. rrNumberField는 16자리반품번호;
	/*--------------------------*/
	
	/*----------infoFrame------------*/
	JFrame returnInfoFrame;
	JTextArea rirrTA,rircTA;
	JPanel riFrameNorthPanel,riFrameSouthPanel,riFrameCenterPanel;
	JPanel riFrameNorthWestPanel,riframeNorthEastPanel;
	JPanel riFrameCenterEastPanel,riFrameCenterWestPanel;
	
	/*--------------------------*/
	
	/*mainBackGroundPanel에 존재*/
	
	JButton returnInfoButton,listButton; 
	JPanel returnPanel;
	JPanel returnSouthPanel;
	SmileTable returnTable;
	/*------------------------*/
	
	
	private void initReturnListFrameComponents(){
		
		this.returnInfoFrame = new JFrame("반품 상세내역");
		this.rirrTA = new JTextArea();
		this.rircTA = new JTextArea();
	}
	
	private void initReturnInfoFrameComponenents(){
		
	}
	
	private void initMainComponents(){
		returnPanel = new JPanel();
		returnInfoButton = new JButton("반품상세내용");
		listButton = new JButton("반품등록");
		
		returnPanel.setLayout(new BorderLayout(10, 10));
		returnPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
		
		String[] listName ={"반품번호","상품번호","반품수량","반품일자"};
		returnTable = new SmileTable(listName,getFromArrayForTable());
		
		JPanel tablePanel = returnTable.getTablePanel();
		returnPanel.add(tablePanel, BorderLayout.CENTER);
		
		returnSouthPanel = new JPanel();
		returnSouthPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		returnSouthPanel.add(listButton);
		returnSouthPanel.add(returnInfoButton);
		
		returnPanel.add(returnSouthPanel,BorderLayout.SOUTH);
	}
	
	private String[][] getFromArrayForTable(){
		String [][] result = new String[quantity][4];
		
		for(int i = 0; i < quantity ; i++){
			result[i][0] = rListStringDatas[i][0];
			result[i][1] = rListStringDatas[i][2];
			result[i][2] = rListStringDatas[i][3];
			result[i][3] = rListStringDatas[i][4];
		}
		
		return result;
	}
	
	private void initComponents(){
		
	}
	
	
	private void setReturnData(){
		String query = "select * from return_list where branch_no = '" + branchNo +"'";
		try{
			ResultSet rs = QueryParser.executeQuery(query);
			quantity = QueryParser.getResultSetRowQuantity(rs);	
			this.rListDatas = new returnList[quantity];
			this.rListStringDatas = new String[quantity][8];
			
			int j = 0;
			
			while(rs.next()){
				for(int i=0;i<8;i++){
					rListStringDatas[j][i]=rs.getString(i+1);
				}
				j++;
			}
			
			rs.close();
		}catch(SQLException e){
			showMessageDialog("반품 내역 불러오기에 실패했습니다.");
			e.printStackTrace();
		}
	}
	
	public SaleReturnController(MainController mainController) {
		super(mainController);
		// TODO Auto-generated constructor stub
	}

	@Override
	void startMenu() {
		setReturnData();
		initMainComponents();
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "반품 관리";
	}

}



class JTextFieldLimit extends PlainDocument {
  private int limit;
  private boolean toUppercase = false;

   JTextFieldLimit(int limit) {
     super();
     this.limit = limit;
  }

   JTextFieldLimit(int limit, boolean upper) {
     super();
     this.limit = limit;
     this.toUppercase = upper;
  }



  public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
     if (str == null) {
        return;
     }

     if ( (getLength() + str.length()) <= limit) {
        if (toUppercase) {
           str = str.toUpperCase();
        }
        super.insertString(offset, str, attr);
     }
  }
}