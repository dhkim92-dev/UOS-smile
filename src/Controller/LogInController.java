package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Model.LogIn;
import View.LogInPanel;
import View.MainFrame;

public class LogInController {
	MainFrame mainFrame;
	JPanel mainBackgroundPanel;
	LogInPanel logInPanel;
	
	public LogInController(){
		mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}
	
	public void startLogIn(){
		this.mainBackgroundPanel = mainFrame.mainBackgroundPanel;
		logInPanel = new LogInPanel();
		mainBackgroundPanel.add(logInPanel);
		mainBackgroundPanel.revalidate();

		setListeners();
	}
	
	void setListeners(){
		// 로그인 버튼 클릭시
		logInPanel.btnLogIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String emNum = logInPanel.getEmployeeNum();
				String emPW = logInPanel.getPassword();
				
				boolean result = LogIn.login(emNum, emPW);
				
				if(!result){
					JOptionPane.showMessageDialog(mainFrame, "로그인에 실패하였습니다.", "Log in",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				mainBackgroundPanel.removeAll();
				
				MainController mc = new MainController(mainFrame);
				mc.initialize();
			}
		});
	}
	
	/** 디버그용 로그인. 추후 삭제해야 함 */
	public void debug_logIn(){
		LogIn.branchNumber = "1000001";
		LogIn.employeeNumber = "100000119201";
		LogIn.employeeCode = "1"; // 점장
		
		mainBackgroundPanel.removeAll();
		
		MainController mc = new MainController(mainFrame);
		mc.initialize();
	}
}
