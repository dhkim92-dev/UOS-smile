package Model;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import View.MainFrame;

public class DialogCaller {
	
	MainFrame mainFrame;
	
	public DialogCaller(JFrame mainFrame) {
		this.mainFrame = (MainFrame)mainFrame;
	}
	
	/**
	 * 매개변수로 전달된 message 를 Dialog 창에 띄운다.
	 * 사용자는 "확인" 버튼을 눌러 Dialog 를 종료한다. 
	 */
	public void showMessageDialog(Object message){
		JOptionPane.showMessageDialog(mainFrame, message, "UOSmile",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * 매개변수로 전달된 message 를 Dialog 창에 띄운다.
	 * 사용자가 "예" 버튼을 누른 경우 true가, "아니오" 버튼을 누른 경우 false 가 반환된다. 
	 */
	public boolean showConfirmDialog(Object message){
		int result = JOptionPane.showConfirmDialog(mainFrame,
				message, "UOSmile", JOptionPane.YES_NO_OPTION);
		
		return result == 0 ? true : false;
	}
}
