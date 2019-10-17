package Controller;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Model.DialogCaller;
import Model.Encoder;
import Model.QueryParser;
import View.MainFrame;
import View.SmileTable;

public class 고객조회Controller extends AbstractController
{
	JPanel 고객번호조회패널;
	public JPanel 고객조회패널;
	SmileTable 고객리스트테이블;
	JPanel 버튼패널;
	JLabel 고객수레이블;
	JButton 모두보기버튼, 등록버튼, 조회버튼;
	JTextField 고객번호텍스트필드;
	
	int 고객수 = 0;
	
	public 고객조회Controller(MainController mainController) {
		super(mainController);
	}

	@Override
	void startMenu() {
		setComponents();
		addListeners();
		addComponents();
		createTable();
		showMenuPanel(고객조회패널);
		
	}
	
	public void addComponents()
	{
		고객번호조회패널.add(new JLabel("고객번호"));
		고객번호조회패널.add(고객번호텍스트필드);
		고객번호조회패널.add(조회버튼);
		고객번호조회패널.add(고객수레이블);
		고객조회패널.add(고객번호조회패널, BorderLayout.NORTH);
		
		버튼패널.add(모두보기버튼);
		버튼패널.add(등록버튼);
		고객조회패널.add(버튼패널, BorderLayout.SOUTH);
	}
	
	public void setComponents()
	{
		//패널 세팅
		고객조회패널 = new JPanel();
		고객조회패널.setLayout(new BorderLayout(10, 10));
		고객조회패널.setBorder(new EmptyBorder(10, 15, 10, 15)); // Margin은 여타 패널과 같게
		버튼패널 = new JPanel();
		버튼패널.setLayout(new FlowLayout());
		고객번호조회패널 = new JPanel();
		고객번호조회패널.setLayout(new FlowLayout());
//		버튼패널.setLayout(new BoxLayout(버튼패널, BoxLayout.Y_AXIS));
		
		//컴포넌트 세팅		
		고객수레이블 = new JLabel("총 "+고객수+" 고객");
		모두보기버튼 = new JButton("모든 고객 보기");
		등록버튼 = new JButton("신규 고객 등록");
		조회버튼 = new JButton("조회");
		고객번호텍스트필드 = new JTextField(15);
	}
	
	public void addListeners()
	{
		final 고객등록프레임 등록프레임 = new 고객등록프레임(mainFrame);

		모두보기버튼.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO 데이터베이스 전부 꺼내오기
				
			}
		});
		
		등록버튼.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				등록프레임.show();
			}
		});
		
		조회버튼.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 고객번호텍스트필드값과 일치하는 고객번호를 가진 것을 DB에서 꺼내와서 반환
				//여기서는 테스트 데이터로 테스트
				String[][] testdata = 
					{
						{ "5", "이춘식", "10", "남", "01012345678", "100" },
						{ "1", "김춘식", "20", "남", "01012345678", "200" },
						{ "7", "최춘식", "30", "여", "01012345678", "300" },
						{ "0", "윤춘식", "40", "여", "01012345678", "400" },
						{ "8", "오춘식", "50", "남", "01012345678", "500" }
					};				
				고객리스트테이블.setData(testdata);
			}
		});
	}
	
	public void createTable()
	{
/* <<< SmileTable 화면에 나타내기 - 시작 >>> */
		
		// SmileTable (1) table 의 열 이름 설정
		String[] columns = {"고객번호", "이름", "나이", "성별", "전화번호", "마일리지"};
				
		// SmileTable (2) table 에 입력될 데이터 설정
		String[][] data = {
				{ "1", "이춘식", "10", "남", "01012345678", "100" },
				{ "2", "김춘식", "20", "남", "01012345678", "200" },
				{ "3", "최춘식", "30", "여", "01012345678", "300" },
				{ "4", "윤춘식", "40", "여", "01012345678", "400" },
				{ "5", "오춘식", "50", "남", "01012345678", "500" },
			};
		
		// SmileTable (3) 열 이름과 데이터로 새로운 테이블을 생성한다.
		고객리스트테이블 = new SmileTable(columns, data);
		
		// SmileTable (4) 테이블에 관한 설정사항을 지정.
		// 해당 설정을 사용하지 않으려면 함수를 호출하지 않으면 됨.
		고객리스트테이블.setSelectable(true);	// 개별 선택 가능(앞쪽에 check box 열이 추가됨)
		고객리스트테이블.setDeletable(true);	// 선택된 row 를 삭제할 수 있음
		고객리스트테이블.setEmptiable(false);	// 전체 삭제 불가능
		
		// SmileTable (5) table 이 들어 있는 panel을 가져와서 현재 메뉴 패널에 설정.
		JPanel tablePanel = 고객리스트테이블.getTablePanel();
		고객조회패널.add(tablePanel, BorderLayout.CENTER);

		/* <<< SmileTable 화면에 나타내기 - 끝 >>> */
		
	}
	
	@Override
	public String toString() {
		return "고객 관리";
	}
}

/**
 * 고객 등록 기능 80% 구현
 * 고객등록 버튼을 누르면 고객등록 창을 띄워준다. 이때 호출되는 패널.
 * @author Cleartype
 */
class 고객등록패널 extends javax.swing.JPanel {
	
	MainFrame mFrame;
	
	boolean is전화번호중복Checked;
	boolean is전화번호중복;	//전화번호가 중복된 것이 있는지 체킹하는 변수

	String 이름 = "", 나이 = "", 성별 = "", 전화번호 = "";	//받아올 data 값
	String 고객번호 = "";	//

    public 고객등록패널(MainFrame mFrame) {
    	this.mFrame = mFrame;
        initComponents();
    }
    
    public void initData()
    {
    	이름 = "";
    	나이 = "";
    	성별 = "";
    	전화번호 = "";
    	고객번호 = "";
    }
    
    public void initTextFieldData()
    {
    	이름텍스트필드.setText("");
    	나이텍스트필드.setText("");
    	전화번호텍스트필드.setText("");
    	성별라디오버튼그룹.clearSelection();
    	is전화번호중복 = false;
    	is전화번호중복Checked = false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        성별라디오버튼그룹 = new javax.swing.ButtonGroup();
        고객정보패널 = new javax.swing.JPanel();
        이름레이블 = new javax.swing.JLabel();
        이름텍스트필드 = new javax.swing.JTextField();
        나이텍스트필드 = new javax.swing.JTextField();
        나이레이블 = new javax.swing.JLabel();
        성별레이블 = new javax.swing.JLabel();
        전화번호텍스트필드 = new javax.swing.JTextField();
        전화번호레이블 = new javax.swing.JLabel();
        중복확인버튼 = new javax.swing.JButton();
        성별남라디오버튼 = new javax.swing.JRadioButton();
        성별여라디오버튼 = new javax.swing.JRadioButton();
        등록취소패널 = new javax.swing.JPanel();
        등록버튼 = new javax.swing.JButton();
        취소버튼 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        고객정보패널.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        이름레이블.setText("이름");

        이름텍스트필드.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                이름텍스트필드ActionPerformed(evt);
            }
        });

        나이레이블.setText("나이");
        
        /**
         * 숫자만 입력가능한 텍스트필드 : 키 리스너 생성
         * @author Cleartype
         *
         */
        //입력을 받아서 정수로 변환시 에러가 검출이 되면 변환되기 전으로 되돌린다.
        class ex extends KeyAdapter{
        	String a1;
		        public void keyReleased(KeyEvent e){
		        JTextField s=(JTextField)e.getSource();
		        try{
		        if(!s.getText().isEmpty())
		        Integer.parseInt(s.getText());
		        a1=s.getText();
		        }
		        catch(NumberFormatException nfe){s.setText(a1);}
		        }
		 }
        나이텍스트필드.addKeyListener(new ex());	//숫자만 입력가능한 텍스트필드 생성

        성별레이블.setText("성별");

        전화번호텍스트필드.setToolTipText("'-' 를 포함하여 입력하세요");

        전화번호레이블.setText("전화번호");
        전화번호텍스트필드.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				전화번호텍스트필드ActionPerformed(e);
			}
		});

        중복확인버튼.setText("중복확인");
        중복확인버튼.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                중복확인버튼ActionPerformed(evt);
            }
        });

        성별남라디오버튼.setText("남");
        성별라디오버튼그룹.add(성별남라디오버튼);
        성별남라디오버튼.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                성별남라디오버튼ActionPerformed(evt);
            }
        });

        성별여라디오버튼.setText("여");
        성별라디오버튼그룹.add(성별여라디오버튼);
        성별여라디오버튼.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                성별여라디오버튼ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout 고객정보패널Layout = new javax.swing.GroupLayout(고객정보패널);
        고객정보패널.setLayout(고객정보패널Layout);
        고객정보패널Layout.setHorizontalGroup(
            고객정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(고객정보패널Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(고객정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(고객정보패널Layout.createSequentialGroup()
                        .addGroup(고객정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(나이레이블)
                            .addComponent(이름레이블))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(고객정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(나이텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(이름텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(173, 173, 173))
                    .addGroup(고객정보패널Layout.createSequentialGroup()
                        .addGroup(고객정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(전화번호레이블)
                            .addComponent(성별레이블))
                        .addGap(18, 18, 18)
                        .addGroup(고객정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(고객정보패널Layout.createSequentialGroup()
                                .addComponent(성별남라디오버튼)
                                .addGap(52, 52, 52)
                                .addComponent(성별여라디오버튼)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(전화번호텍스트필드))
                        .addGap(18, 18, 18)
                        .addComponent(중복확인버튼, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        고객정보패널Layout.setVerticalGroup(
            고객정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(고객정보패널Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(고객정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(이름레이블)
                    .addComponent(이름텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(고객정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(나이텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(나이레이블))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(고객정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(성별레이블)
                    .addComponent(성별남라디오버튼)
                    .addComponent(성별여라디오버튼))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(고객정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(전화번호레이블)
                    .addComponent(전화번호텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(중복확인버튼))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        등록버튼.setText("등록");
        등록버튼.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                등록버튼ActionPerformed(evt);
            }
        });

        취소버튼.setText("취소");
        취소버튼.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                취소버튼ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout 등록취소패널Layout = new javax.swing.GroupLayout(등록취소패널);
        등록취소패널.setLayout(등록취소패널Layout);
        등록취소패널Layout.setHorizontalGroup(
            등록취소패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(등록취소패널Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(등록버튼)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                .addComponent(취소버튼)
                .addGap(28, 28, 28))
        );
        등록취소패널Layout.setVerticalGroup(
            등록취소패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, 등록취소패널Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(등록취소패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(등록버튼)
                    .addComponent(취소버튼))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("굴림", 2, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel1.setText("등록할 고객의 정보를 입력하세요");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(고객정보패널, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(등록취소패널, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(고객정보패널, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(등록취소패널, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>                        

    private void 중복확인버튼ActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO '전화번호텍스트필드' 의 값을 읽어와서 DB의 CUSTOMER 엔티티타입의 CUSTOMER_PHONE과 비교한다.
    	// [QUERY] SELECT CUSTOMER_PHONE FROM CUSTOMER WHERE CUSTOMER_PHONE = 'XXX'
    	is전화번호중복Checked = true;
    }                                      
    
    private void 전화번호텍스트필드ActionPerformed(ActionEvent e)
    {
    	//TODO 전화번호텍스트필드의 값이 바뀌면 is전화번호중복Checked = false로 바꾼다.
    }

    private void 이름텍스트필드ActionPerformed(java.awt.event.ActionEvent evt) {                                        
    }                                       

    private void 성별남라디오버튼ActionPerformed(java.awt.event.ActionEvent evt) {                                         
    	성별 = "남";
    }                                        

    private void 등록버튼ActionPerformed(java.awt.event.ActionEvent evt) {                                     
        // 정말 등록하시겠습니까? 팝업을 띄운 후 예를 클릭하면 데이터값에 텍스트필드값이 들어가고
    	// Encoder 클래스를 이용하여 코드를 생성하여 고객번호 String에 넣는다.
    	/*
    	 *  [QUERY] INSERT INTO CUSTOMER
    	 *  (CUSTOMER_NO, CUSTOMER_NAME, CUSTOMER_AGE, CUSTOMER_GENDER, CUSTOMER_PHONE, CUSTOMER_MILEAGE)
    	 *  VALUES (고객번호, 이름, 나이, 성별, 전화번호, 0)
    	 */
    	// 데이터값에 값이 들어간 후에 그 데이터값을 DB에 저장해준다.
    	// 중복처리가 된 상태라면 '이미 등록된 XX입니다' 팝업을, 중복버튼 자체가 눌리지 않았으면 '중복확인을 해주세요' 팝업을 띄운다.
    	// 입력하지 않은 값이 있다면 'XX를 입력해주세요' 팝업을 띄운다.
    	// DB에 저장까지 했다면 '등록하였습니다' 팝업을 띄우고 확인을 누르면 데이터값과 텍스트필드값을 초기화한다.
    	
    	DialogCaller caller = new DialogCaller(mFrame);
    	
    	//미입력값 처리
    	if(이름텍스트필드.getText().toString().equals("")
    			|| 나이텍스트필드.getText().toString().equals("")
    			|| 전화번호텍스트필드.getText().toString().equals("")
    			|| 성별라디오버튼그룹.getSelection() == null)
    	{
    		caller.showMessageDialog("미입력값이 있습니다.");
    	}
    	else
    	{
    		//중복처리
    		if(!is전화번호중복Checked)
        	{
        		caller.showMessageDialog("중복확인을 해주세요");
        	}
        	else
        	{
        		if(is전화번호중복)
        		{
        			caller.showMessageDialog("이미 등록된 전화번호입니다");
        		}
        		else
        		{
            		if(caller.showConfirmDialog("등록하시겠습니까?"))
            		{
            	    	//고객번호 생성 - 전화번호 뒤의 8자리
            	    	Encoder encoder = new Encoder();
            	    	고객번호 = encoder.getCode(전화번호텍스트필드.getText().toString().substring(3, 전화번호텍스트필드.getText().toString().length()));
            	    	
            			//TODO Database에 데이터를 넣는다.
            			caller.showMessageDialog("등록되었습니다");
            		}    		
        		}
        	}
    	}    	
    }                                    

    private void 취소버튼ActionPerformed(java.awt.event.ActionEvent evt) {        
    	// TODO MainFrame을 수정해서 팝업창 위에다 팝업창을 띄우게 하는 방법을 알아보자. 
    	//(현재도 뜨긴 하지만 메인프레임이 컨트롤러에 있어서 팝업창이 꺼지고 뜸)
    	DialogCaller caller = new DialogCaller(mFrame);
    	if(caller.showConfirmDialog("정말 취소하시겠습니까?"))
		{
    		caller.showMessageDialog("취소되었습니다.");
    		initData();
    		initTextFieldData();
		}
    }                                    

    private void 성별여라디오버튼ActionPerformed(java.awt.event.ActionEvent evt) {                                         
    	성별 = "여";
    }                                        


    // Variables declaration - do not modify                     
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel 고객정보패널;
    private javax.swing.JLabel 나이레이블;
    private javax.swing.JTextField 나이텍스트필드;
    private javax.swing.JButton 등록버튼;
    private javax.swing.JPanel 등록취소패널;
    private javax.swing.JRadioButton 성별남라디오버튼;
    private javax.swing.ButtonGroup 성별라디오버튼그룹;
    private javax.swing.JLabel 성별레이블;
    private javax.swing.JRadioButton 성별여라디오버튼;
    private javax.swing.JLabel 이름레이블;
    private javax.swing.JTextField 이름텍스트필드;
    private javax.swing.JLabel 전화번호레이블;
    private javax.swing.JTextField 전화번호텍스트필드;
    private javax.swing.JButton 중복확인버튼;
    private javax.swing.JButton 취소버튼;
    // End of variables declaration                   
}


class 고객등록프레임 extends javax.swing.JFrame{

	static MainFrame mFrame;
	고객등록패널 고객등록패널2;
	
    public 고객등록프레임(MainFrame mFrames) {
    	this.mFrame = mFrames;
    	this.setResizable(false);
        initComponents();
    }

    @SuppressWarnings("unchecked")          
    private void initComponents() {
    	
    	고객등록패널2 = new 고객등록패널(mFrame);
    	
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("신규고객등록");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(고객등록패널2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(고객등록패널2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }                      

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(고객등록프레임.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(고객등록프레임.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(고객등록프레임.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(고객등록프레임.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new 고객등록프레임(mFrame).setVisible(true);
            }
        });
    }
}