package Controller;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Model.QueryParser;
import View.SmileTable;
public class BranchInfoController extends AbstractController{
	class Branch_Info{
		String branchNo;
		String branchName;
		String branchAddress;
		String branchPhoneNumber;
		String branchFranchiseCommission;
		
		public Branch_Info(String number, String Name, String Address, String phoneNumber, String commission){
			this.branchNo=number;
			this.branchName=Name;
			this.branchAddress = Address;
			this.branchPhoneNumber = phoneNumber;
			this.branchFranchiseCommission = commission;
		}
		
		public String toString(){
			return "지점 번호 : " + branchNo + " 지점 이름 : " + branchName + " 지점 주소 : " + branchAddress + "지점 전화번호 : "+branchPhoneNumber + "가맹수수료" + branchFranchiseCommission;
		}
	}
	Branch_Info myBranch;
	JPanel branchInfoViewPanel;
	JPanel northPanel;
	JPanel centerPanel;
	JPanel centerLeftPanel;
	JPanel centerRightPanel;
	JPanel southPanel;
	
	JLabel branchInfoLabel;
	JLabel branchNumberLabel;
	JLabel branchNameLabel;
	JLabel branchAddressLabel;
	JLabel branchPhoneNumberLabel;
	
	JTextField branchNumberTextField;
	JTextField branchNameTextField;
	JTextField branchAddressTextField;
	JTextField branchPhoneNumberTextField;
	
	JButton changeConfirmButton;
	JButton modifyButton;
	private void initComponents(){
		this.branchAddressLabel = new JLabel("지점 주소 :");
		this.branchAddressLabel.setFont(new java.awt.Font("굴림", 2, 20));
		this.branchNameLabel = new JLabel("지점명 :");
		this.branchNameLabel.setFont(new java.awt.Font("굴림", 2, 20));
		this.branchNumberLabel = new JLabel("지점 번호 : ");
		this.branchNumberLabel.setFont(new java.awt.Font("굴림", 2, 20));
		this.branchPhoneNumberLabel = new JLabel("지점 전화번호 : ");
		this.branchPhoneNumberLabel.setFont(new java.awt.Font("굴림", 2, 20));
		this.branchInfoLabel = new JLabel("지점 정보");
		this.branchInfoLabel.setFont(new java.awt.Font("굴림", 2, 20));
		
		branchNumberTextField = new JTextField(myBranch.branchNo);
		this.branchNumberTextField.setFont(new Font("굴림",Font.BOLD,15));
		branchNameTextField= new JTextField(myBranch.branchName);
		this.branchNameTextField.setFont(new Font("굴림",Font.BOLD,17));
		branchAddressTextField= new JTextField(myBranch.branchAddress);
		this.branchAddressTextField.setFont(new Font("굴림",Font.BOLD,10));
		branchPhoneNumberTextField= new JTextField(myBranch.branchPhoneNumber);
		this.branchPhoneNumberTextField.setFont(new Font("굴림",Font.BOLD,17));
		
		changeConfirmButton = new JButton("변경사항 저장");
		this.changeConfirmButton.setFont(new java.awt.Font("굴림", 2, 20));
		modifyButton = new JButton("수정");
		this.modifyButton.setFont(new java.awt.Font("굴림", 2, 20));
		
		branchInfoViewPanel = new JPanel();
		branchInfoViewPanel.setLayout(new BorderLayout(10, 10));
		branchInfoViewPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
		
		northPanel			= new JPanel(new FlowLayout(FlowLayout.RIGHT));
		northPanel.setSize(500,120);
		centerPanel			= new JPanel(new FlowLayout(FlowLayout.RIGHT));
		centerPanel.setSize(400,200);
		centerLeftPanel		= new JPanel(new GridLayout(4,1,200,50));
		centerRightPanel	= new JPanel(new GridLayout(4,1,200,50));
		southPanel			= new JPanel(new FlowLayout(FlowLayout.RIGHT));
		southPanel.setSize(400,80);
	}
	
	private void loadBranchInfoFromDatabase(){
		String branchNo = "1000001";
		String query = "select * from BRANCH where branch_No='"+branchNo+"'";
		System.out.println("쿼리문 : " + query);
		
		try{
			ResultSet rs = QueryParser.executeQuery(query);
			if(!rs.next()){
				showMessageDialog("데이터가 없습니다.");
			}else{
				myBranch = new Branch_Info(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
				System.out.println(myBranch.toString());
			}
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	private void createBranchInfoView(){
		northPanel.add(this.branchInfoLabel);
		southPanel.add(this.modifyButton);
		
		this.branchNumberTextField.setEditable(false);
		this.branchAddressTextField.setEditable(false);
		this.branchNameTextField.setEditable(false);
		this.branchPhoneNumberTextField.setEditable(false);
		this.branchNameTextField.setEditable(false);
		
		centerLeftPanel.add(this.branchNumberLabel,BorderLayout.WEST);
		centerLeftPanel.add(this.branchNameLabel,BorderLayout.WEST);
		centerLeftPanel.add(this.branchAddressLabel,BorderLayout.WEST);
		centerLeftPanel.add(this.branchPhoneNumberLabel,BorderLayout.WEST);
		
		centerRightPanel.add(this.branchNumberTextField);
		centerRightPanel.add(this.branchNameTextField);
		centerRightPanel.add(this.branchAddressTextField);
		centerRightPanel.add(this.branchPhoneNumberTextField);
			
		branchInfoViewPanel.add(this.northPanel,BorderLayout.NORTH);
		centerPanel.add(this.centerLeftPanel);
		centerPanel.add(this.centerRightPanel);
		branchInfoViewPanel.add(this.centerPanel,BorderLayout.CENTER);
		branchInfoViewPanel.add(this.southPanel,BorderLayout.SOUTH);
		showMyPanel(branchInfoViewPanel);
	}
	
	protected void showMyPanel(JPanel panel) {
		
		if(panel == null){
			System.out.println("Panel 이 null 로 설정되어 있습니다.");
			return;
		}
		
		mainBackgroundPanel.removeAll();
		mainBackgroundPanel.add(panel);
		mainBackgroundPanel.revalidate();
		mainBackgroundPanel.repaint();
	}
	
	private void saveAtDatabase(){
		Branch_Info cbInfo = new Branch_Info(this.branchNumberTextField.getText()
											,this.branchNameTextField.getText()
											,this.branchAddressTextField.getText()
											,this.branchPhoneNumberTextField.getText()
											,this.myBranch.branchFranchiseCommission);
		String query = "UPDATE BRANCH SET BRANCH_NAME = '" + cbInfo.branchName + "'," + 
										  "BRANCH_ADDRESS = '" + cbInfo.branchAddress + "',"  + 
										  "BRANCH_PHONE_NO = '" + cbInfo.branchPhoneNumber +
										  "' WHERE BRANCH_NO = '" + cbInfo.branchNo + "'";
		try{
			ResultSet rs = QueryParser.executeQuery(query);
			rs.close();
			rs = QueryParser.executeQuery("commit");
			rs.close();
		}catch(SQLException e){
			showMessageDialog("업데이트에 실패했습니다.");
			e.printStackTrace();
		}
		System.out.println("Query : "+query);
	}
	
	private void setListeners()
	{
		changeConfirmButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					southPanel.remove(changeConfirmButton);
					southPanel.add(modifyButton);
					branchNameTextField.setEditable(false);
					branchAddressTextField.setEditable(false);
					branchPhoneNumberTextField.setEditable(false);
					saveAtDatabase();
					showMyPanel(branchInfoViewPanel);
				}
			}
		);
		
		modifyButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				southPanel.remove(modifyButton);
				southPanel.add(changeConfirmButton);
				branchNameTextField.setEditable(true);
				branchAddressTextField.setEditable(true);
				branchPhoneNumberTextField.setEditable(true);
				showMyPanel(branchInfoViewPanel);
			}
		}
		);
	}
	
	public BranchInfoController(MainController mainController) {
		super(mainController);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	void startMenu() {
		this.loadBranchInfoFromDatabase();
		this.initComponents();
		this.createBranchInfoView();
		this.setListeners();
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "지점 정보 관리";
	}
	
}
