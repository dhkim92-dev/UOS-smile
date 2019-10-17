package Controller;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import View.MainFrame;

/**
 * 각 메뉴 컨트롤러의 부모가 되는 추상 클래스. {@link MainController#createMainMenuNodes(DefaultMutableTreeNode)
 * createMainMenuNodes()} 메소드 내에서 AbstractController 를 상속하는 컨트롤러를 생성하여 메뉴로 설정하면,
 * 메뉴 클릭 시 startMenu() 함수가 호출되면서 해당 메뉴가 시작된다. 
 */
public abstract class AbstractController {
	
	protected MainFrame mainFrame;
	protected JPanel mainBackgroundPanel;

	/**
	 * Main background panel 을 매개변수로 전달받는 생성자.
	 */
	public AbstractController(MainController mainController) {
		this.mainFrame = mainController.mainFrame;
		this.mainBackgroundPanel = mainController.mainBackgroundPanel;
	}
	
	
	/**
	 * 각 메뉴가 선택되었을 때 이 메소드가 실행된다.
	 * 현재 메뉴에 해당하는 패널을 생성하여 Main background panel 에 설정하는 과정을 구현해야 한다.
	 */
	abstract void startMenu();
	
	
	/**
	 * Main background panel 에 매개변수로 전달된 panel 을 나타낸다.
	 */
	protected void showMenuPanel(JPanel menuPanel) {
		
		if(menuPanel == null){
			System.out.println("Panel 이 null 로 설정되어 있습니다.");
			return;
		}
		
		mainBackgroundPanel.removeAll();
		mainBackgroundPanel.add(menuPanel);
		mainBackgroundPanel.revalidate();
		mainBackgroundPanel.repaint();
	}
	
	/**
	 * 매개변수로 전달된 message 를 Dialog 창에 띄운다.
	 * 사용자는 "확인" 버튼을 눌러 Dialog 를 종료한다. 
	 */
	protected void showMessageDialog(Object message){
		JOptionPane.showMessageDialog(mainFrame, message, "UOSmile",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * 매개변수로 전달된 message 를 Dialog 창에 띄운다.
	 * 사용자가 "예" 버튼을 누른 경우 true가, "아니오" 버튼을 누른 경우 false 가 반환된다. 
	 */
	protected boolean showConfirmDialog(Object message){
		int result = JOptionPane.showConfirmDialog(mainFrame,
				message, "UOSmile", JOptionPane.YES_NO_OPTION);
		
		return result == 0 ? true : false;
	}
	
	/** 
	 * Main menu 들을 나타내는 tree 에 나타낼 메뉴명. 
	 */
	public abstract String toString();
	
	public String putCommas(int iPrice) {
		return putCommas("" + iPrice);
	}
	
	public String putCommas(String strPrice) {
		strPrice = eraseComma(strPrice);
		int iPrice = Integer.parseInt(strPrice);
		return String.format("%,13d", iPrice);
	}
	
	public void putCommas(String[][] arr, int priceColumn) {
		if(arr == null) {
			System.out.println("AbstractController.putCommas() : arr 매개변수가 NULL임.");
			return;
		}
		
		for(int i=0; i<arr.length; i++)
			arr[i][priceColumn] = putCommas(arr[i][priceColumn]);
	}
	
	public String eraseComma(String strPrice) {
		strPrice = strPrice.replaceAll("\\,", "");
		strPrice = strPrice.replaceAll("\\s", "");
		
		return new String(strPrice);
	}
	
	public void eraseCommas(String[][] arr, int priceColumn) {
		if(arr == null) {
			System.out.println("AbstractController.eraseCommas() : arr 매개변수가 NULL임.");
			return;
		}
		
		for(int i=0; i<arr.length; i++)
			arr[i][priceColumn] = eraseComma(arr[i][priceColumn]);
	}
}
