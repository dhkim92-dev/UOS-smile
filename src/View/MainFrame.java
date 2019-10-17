package View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame{

	private static final int FRAME_INIT_WIDTH = 1200;
	private static final int FRAME_INIT_HEIGHT = 800;
	
	public JPanel mainMenuPanel;
	public JPanel mainBackgroundPanel;
	
	public MainFrame() {
		super("UOSmile");
		
		setMinimumSize(new Dimension(FRAME_INIT_WIDTH, FRAME_INIT_HEIGHT));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		mainMenuPanel = new JPanel();
		getContentPane().add(mainMenuPanel, BorderLayout.WEST);
		
		mainBackgroundPanel = new JPanel();
		// mainBackGroundPanel 에 CardLayout 을 설정하여 컴포넌트가
		// mainBackGroundPanel 의 공간을 모두 사용할 수 있도록 한다. 
		mainBackgroundPanel.setLayout(new CardLayout());
		getContentPane().add(mainBackgroundPanel, BorderLayout.CENTER);
	}
}