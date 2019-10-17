package Controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import Model.LogIn;
import View.MainFrame;

public class MainController implements TreeSelectionListener {
	MainFrame mainFrame;
	JTree mainMenuTree;
	JPanel mainBackgroundPanel;

	MainController(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	void initialize() {
		this.mainBackgroundPanel = mainFrame.mainBackgroundPanel;

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		createMainMenuNodes(root);
		createMainMenuTree(root);

		mainFrame.revalidate();
		
		// TODO 자동으로 n번째 메뉴 선택. 디버그용. 추후 삭제
		mainMenuTree.setSelectionRow(18);
	}

	/**
	 * 메뉴 트리에 메뉴를 추가한다. 추가되는 노드는 {@link AbstractController} 를 상속하는 Controller
	 * 이다. Controller 를 생성할 때에는 this(MainController 의 인스턴스)를 매개변수로 전달해야 한다.
	 */
	private void createMainMenuNodes(DefaultMutableTreeNode root) {
		DefaultMutableTreeNode node;

		// 직원 이상
		if (slave()) {
			node = new DefaultMutableTreeNode("판매");
			node.add(new DefaultMutableTreeNode(new ProductSaleController(this)));
			node.add(new DefaultMutableTreeNode(new ProductRefundController(this)));
			node.add(new DefaultMutableTreeNode(new SalesController(this)));
			root.add(node);
		}

		// 직원 이상
		if (slave()) {
			node = new DefaultMutableTreeNode("재고");
			node.add(new DefaultMutableTreeNode(new InventoryManageController(this)));
			root.add(node);
		}

		// 매니저 이상
		if (manager()) {
			node = new DefaultMutableTreeNode("발주");
			node.add(new DefaultMutableTreeNode(new OrderListViewController(this)));
			node.add(new DefaultMutableTreeNode(new SaleReturnController(this)));
			root.add(node);
		}

		// 직원 이상
		if (slave()) {
			node = new DefaultMutableTreeNode("상품");
			node.add(new DefaultMutableTreeNode(new ItemListViewController(this)));
			root.add(node);
		}

		// 직원 이상
		if (slave()) {
			node = new DefaultMutableTreeNode("고객");
			node.add(new DefaultMutableTreeNode(new 고객조회Controller(this)));
			root.add(node);
		}

		// 매니저 이상
		if (manager()) {
			node = new DefaultMutableTreeNode("직원");
			node.add(new DefaultMutableTreeNode(new 직원정보관리Controller(this)));
			node.add(new DefaultMutableTreeNode(new 근무기록Controller(this)));
			// 점장 이상
			if (owner()) {
				node.add(new DefaultMutableTreeNode("급여 관리"));
			}
			root.add(node);
		}

		// 점장 이상
		if (owner()) {
			node = new DefaultMutableTreeNode("지점");
			node.add(new DefaultMutableTreeNode(new GainLossController(this)));
			node.add(new DefaultMutableTreeNode(new BranchInfoController(this)));
			root.add(node);
		}
	}

	private void createMainMenuTree(DefaultMutableTreeNode root) {
		mainMenuTree = new JTree(root);
		mainMenuTree.setBorder(new EmptyBorder(10, 10, 10, 10));
		mainMenuTree.setFont(new Font("굴림", Font.PLAIN, 17));
		mainMenuTree.setRootVisible(false);
		mainMenuTree.setPreferredSize(new Dimension(160, 450));

		for (int i = 0; i < mainMenuTree.getRowCount(); i++)
			mainMenuTree.expandRow(i);
		mainMenuTree.addTreeSelectionListener(this);

		mainFrame.mainMenuPanel.setBackground(Color.WHITE);
		mainFrame.mainMenuPanel.add(mainMenuTree);
	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) mainMenuTree
				.getLastSelectedPathComponent();

		if (node == null)
			return;

		// if(!node.isLeaf()){ // avoid collepsing
		// mainMenuTree.setToggleClickCount(0);
		// return;
		// }

		if (!(node.getUserObject() instanceof AbstractController)) {
			System.out
					.println("현재 메뉴 노드를 TempleteController 로 casting 할 수 없습니다.");

			/* 화면 지움 */
			mainBackgroundPanel.removeAll();
			mainBackgroundPanel.revalidate();
			mainBackgroundPanel.repaint();

			return;
		}

		AbstractController controller = (AbstractController) node
				.getUserObject();

		try {
			controller.startMenu();
		} catch (Exception e) {
			System.out.println(node.toString() + " : fail to startMenu().");
			e.printStackTrace();
		}
	}

	/** 점장 이상의 권한(점장) */
	private boolean owner() {
		return LogIn.employeeCode.equals(LogIn.EMPLOYEE_CODE_OWNER);
	}

	/** 매니저 이상의 권한(매니저, 점장) */
	private boolean manager() {
		return (LogIn.employeeCode.equals(LogIn.EMPLOYEE_CODE_MANAGER)
				|| LogIn.employeeCode.equals(LogIn.EMPLOYEE_CODE_OWNER));
	}

	/** 직원 이상의 권한(직원, 매니저, 점장) */
	private boolean slave() {
		return (LogIn.employeeCode.equals(LogIn.EMPLOYEE_CODE_SLAVE)
				|| LogIn.employeeCode.equals(LogIn.EMPLOYEE_CODE_MANAGER)
				|| LogIn.employeeCode.equals(LogIn.EMPLOYEE_CODE_OWNER));
	}
}
