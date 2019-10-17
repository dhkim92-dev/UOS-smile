package View;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LogInPanel extends JPanel {
	private final static String STRING_EMPLOYEE_NUMBER = "직원 번호"; 
	private final static String STRING_PASSWORD = "비밀 번호";
	private final static String STRING_CONNECT = "접속";
	
	private final static int EMPLOYEE_NUMBER_LENGTH = 20;
	private final static int PASSWORD_LENGTH = 20;
	
	public JButton btnLogIn; 
	
	private JTextField tfEmployeeNum;
	private JPasswordField tfPassword;
	
	public LogInPanel() {
		/* mainBackGroundPanel 은 CardLayout 이므로, LogInPanel 에 BoxLayout 을 적용해서
		 * mainBackGroundPanel 에 추가하면 LogInPanel 내의 컴포넌트들이 확대되는 문제가 발생한다.
		 * 따라서 LogInPanel 내에 innerPanel 을 다시 추가하여 그 내부에 컴포넌트들을 배치한다. */
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
		add(innerPanel);
		
		Component verticalStrut = Box.createVerticalStrut(150);
		innerPanel.add(verticalStrut);
		
		JPanel panel = new JPanel();
		innerPanel.add(panel);
		
		JLabel lblNewLabel = new JLabel(STRING_EMPLOYEE_NUMBER);
		panel.add(lblNewLabel);
		
		tfEmployeeNum = new JTextField();
		panel.add(tfEmployeeNum);
		tfEmployeeNum.setColumns(EMPLOYEE_NUMBER_LENGTH);
		
		JPanel panel_1 = new JPanel();
		innerPanel.add(panel_1);
		
		JLabel lblNewLabel_1 = new JLabel(STRING_PASSWORD);
		panel_1.add(lblNewLabel_1);
		
		tfPassword = new JPasswordField();
		tfPassword.setColumns(PASSWORD_LENGTH);
		panel_1.add(tfPassword);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		innerPanel.add(panel_2);
		
		btnLogIn = new JButton(STRING_CONNECT);
		panel_2.add(btnLogIn);
	}
	
	public String getEmployeeNum(){
		return tfEmployeeNum.getText();
	}
	
	public String getPassword(){
		return new String(tfPassword.getPassword());
	}
}
