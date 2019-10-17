package Controller;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Model.DialogCaller;
import Model.QueryParser;
import View.MainFrame;
import View.SmileTable;

public class 직원정보관리Controller extends AbstractController
{
	JPanel 직원번호조회패널;
	public JPanel 직원정보관리패널;
	SmileTable 직원리스트테이블;
	JPanel 버튼패널;
	JLabel 직원수레이블;
	JButton 모두보기버튼, 등록버튼, 조회버튼, 직원정보수정버튼;
	JTextField 직원번호텍스트필드;
	JComboBox<String> 조건콤보박스;
	
	int 직원수 = 0;
	
	public 직원정보관리Controller(MainController mainController) {
		super(mainController);
	}

	@Override
	void startMenu() {
		setComponents();
		addListeners();
		addComponents();
		createTable();
		showMenuPanel(직원정보관리패널);		
	}
	
	/**
	 * 처음 메뉴를 클릭했을 때 보이는 데이터를 반환하고 그 수를 레이블에 입력한다.
	 */
	public String[][] getInitDataInTable()
	{
		QueryParser parser = new QueryParser();
		String query = parser.getQueryByID("getEmployeeDataInTable");
		직원수 = parser.executeUpdate(query);
		String[][] data = parser.getResultString2DArr(query, 7);
		return data;
	}
	
	/**
	 * 직원번호가 일치하는 데이터를 반환하고 그 수를 레이블에 입력한다.
	 * @param 직원번호
	 * @return
	 */
	public String[][] getDataSearchedByNoInTable(String 직원번호)	
	{
		QueryParser parser = new QueryParser();
		String query = "SELECT EMPLOYEE_NO, POSITION_CODE, EMPLOYEE_NAME, EMPLOYEE_PHONE_NO, CURRENT_WORK_STATE, WORK_START_DATE, WORK_END_DATE FROM EMPLOYEE WHERE EMPLOYEE_NO = "+직원번호;
		직원수 = parser.executeUpdate(query);
		String[][] data = parser.getResultString2DArr(query, 7);
		return data;
	}
	
	/**
	 * 해당 직원번호에 해당하는, 
	 * 테이블의 데이터가 아닌 모든 데이터를 가져온다.
	 * @param 직원번호
	 * @return
	 */
	public String[] getDataSearchedByNo(String 직원번호)
	{
		QueryParser parser = new QueryParser();
		String query = "SELECT * FROM EMPLOYEE WHERE EMPLOYEE_NO = "+직원번호;
		직원수 = parser.executeUpdate(query);
		String[] data = parser.getResultStringArr(query, 10);
		return data;
	}
	
	/**
	 * 우리지점의 데이터를 반환하고 그 수를 레이블에 입력한다.
	 * @return
	 */
	public String[][] getDataSearchedByBranchInTable()
	{
		QueryParser parser = new QueryParser();
		String query = parser.getQueryByID("getEmployeeDataInTableInMyBranch");
		직원수 = parser.executeUpdate(query);
		String[][] data = parser.getResultString2DArr(query, 7);
		return data;
	}
	
	public void addComponents()
	{
		직원번호조회패널.add(new JLabel("직원번호"));

		직원번호조회패널.add(직원번호텍스트필드);
		직원번호조회패널.add(조회버튼);
		직원번호조회패널.add(직원수레이블);
		직원정보관리패널.add(직원번호조회패널, BorderLayout.NORTH);
		
		버튼패널.add(모두보기버튼);
		버튼패널.add(등록버튼);
		버튼패널.add(직원정보수정버튼);
		직원정보관리패널.add(버튼패널, BorderLayout.SOUTH);
	}
	
	public void setComponents()
	{
		//패널 세팅
		직원정보관리패널 = new JPanel();
		직원정보관리패널.setLayout(new BorderLayout(10, 10));
		직원정보관리패널.setBorder(new EmptyBorder(10, 15, 10, 15)); // Margin은 여타 패널과 같게
		버튼패널 = new JPanel();
		버튼패널.setLayout(new FlowLayout());
		직원번호조회패널 = new JPanel();
		직원번호조회패널.setLayout(new FlowLayout());
//		버튼패널.setLayout(new BoxLayout(버튼패널, BoxLayout.Y_AXIS));
		
		//컴포넌트 세팅		
		직원수레이블 = new JLabel("총 "+직원수+" 직원");
		모두보기버튼 = new JButton("모든 직원 보기");
		등록버튼 = new JButton("신규 직원 등록");
		조회버튼 = new JButton("조회");
		직원정보수정버튼 = new JButton("직원 정보 수정");
		직원번호텍스트필드 = new JTextField(15);
	}
	
	public void addListeners()
	{
		//프레임 설정
		final 신규등록프레임 등록프레임 = new 신규등록프레임(mainFrame);
		final 직원정보수정프레임 수정프레임 = new 직원정보수정프레임(mainFrame);

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
				// TODO 직원번호텍스트필드값과 일치하는 직원번호를 가진 것을 DB에서 꺼내와서 반환
				//여기서는 테스트 데이터로 테스트
//				String[][] testdata = 
//					{
//						{ "5", "이춘식", "10", "남", "01012345678", "100" },
//						{ "1", "김춘식", "20", "남", "01012345678", "200" },
//						{ "7", "최춘식", "30", "여", "01012345678", "300" },
//						{ "0", "윤춘식", "40", "여", "01012345678", "400" },
//						{ "8", "오춘식", "50", "남", "01012345678", "500" }
//					};				
//				직원리스트테이블.setData(testdata);
				String[][] data = getInitDataInTable();
				직원리스트테이블.setData(data);
			}
		});
		
		//TODO 직원정보조회버튼리스너
		직원정보수정버튼.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				수정프레임.show();
			}
		});
		
		//TODO 모든 직원 보기 버튼 리스너
		
	}
	
	public void createTable()
	{
/* <<< SmileTable 화면에 나타내기 - 시작 >>> */
		// SmileTable (1) table 의 열 이름 설정
		String[] columns = {"직원번호", "직급", "이름", "전화번호", "근무여부", "근무시작일", "근무종료일"};
				
		// SmileTable (2) table 에 입력될 데이터 설정
		String[][] data = {
				{ "1", "점장", "이춘식", "01012345678", "Y", "20140101" , null},
				{ "2", "계약직", "김춘식", "01012345678", "Y", "20140102" , null},
				{ "3", "계약직", "최춘식", "01012345678", "N", "20140103" , "20140108"},
				{ "4", "정규직", "윤춘식", "01012345678", "Y", "20140104" , null},
				{ "5", "정규직", "오춘식", "01012345678", "N", "20140105" , "20140109"},
			};
		//TODO 실제 데이터 작업 시에는 이 부분을 활성화시킬 것
//		String[][] data = getInitDataInTable();
		
		// SmileTable (3) 열 이름과 데이터로 새로운 테이블을 생성한다.
		직원리스트테이블 = new SmileTable(columns, data);
		
		// SmileTable (4) 테이블에 관한 설정사항을 지정.
		// 해당 설정을 사용하지 않으려면 함수를 호출하지 않으면 됨.
		직원리스트테이블.setSelectable(true);	// 개별 선택 가능(앞쪽에 check box 열이 추가됨)
		직원리스트테이블.setDeletable(true);	// 선택된 row 를 삭제할 수 있음
		직원리스트테이블.setEmptiable(false);	// 전체 삭제 불가능
		
		// SmileTable (5) table 이 들어 있는 panel을 가져와서 현재 메뉴 패널에 설정.
		JPanel tablePanel = 직원리스트테이블.getTablePanel();
		직원정보관리패널.add(tablePanel, BorderLayout.CENTER);

		/* <<< SmileTable 화면에 나타내기 - 끝 >>> */
		
	}
	
	@Override
	public String toString() {
		return "직원 관리";
	}
}


/***
 * 신규 직원 등록 프레임
 * 완성도 : 60%
 * @Cleartype
 ***/

class 신규등록프레임 extends javax.swing.JFrame {
	
	static MainFrame mFrame;
	직원 현재보는직원;

    public 신규등록프레임(MainFrame mFrame) {
    	this.mFrame = mFrame;
        initComponents();
        setTextFieldValues();
        this.setResizable(false);
    }
    
    /**
     * 오늘 날짜를 YYYY-MM-DD 형식의 String으로 반환해준다.
     * @return
     */
    public String getCurrentDate()
    {
    	String date = "";
    	int m, d;
    	Calendar oCalendar = Calendar.getInstance( );  // 현재 날짜/시간 등의 각종 정보 얻기

        date += oCalendar.get(Calendar.YEAR);
		m = oCalendar.get(Calendar.MONTH) + 1;
		date += '-';
		if(m < 10) date += "0"+m;
		else date += m;
		d = oCalendar.get(Calendar.DAY_OF_MONTH);
		date += '-';
		if(d < 10) date += "0"+d;
		else date += d;

        return date;
    }
    
    /**
     * 직원의 전화번호와 근무일자를 입력하면 직원번호를 리턴해서 보내준다.
     */
    public String get직원번호(String 전화번호, String 근무일자)
    {
    	String 이전직원번호 = "";
    	String 직원번호 = "";
    	Model.Encoder encoder = new Model.Encoder();    	  
    	
    	이전직원번호 += 전화번호; //010-XXXX-XXXX의 X부분 추가
    	이전직원번호 += 근무일자;	//2014-XX-XX의 X부분 추가
    	StringTokenizer st = new StringTokenizer(이전직원번호, "-");
    	while(st.hasMoreTokens())
    	{
    		직원번호 += st.nextToken();
    	}
      	직원번호 = encoder.getCode(직원번호);	//코드화
    	return 직원번호;
    }
    
    public void setTextFieldValues()
    {
        현재보는직원.지점번호 = "1";	//TODO 지점번호는 1로 초기화됨(바꿀 수 있음) 	
        현재보는직원.근무시작일 = getCurrentDate();	//근무시작 : 오늘날짜
        근무시작일텍스트필드.setText(현재보는직원.근무시작일);
        지점번호텍스트필드.setText(현재보는직원.지점번호);
        현재보는직원.근무여부 = "Y";
        현재보는직원.근무종료일 = null;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

    	현재보는직원 = new 직원();
        타이틀패널 = new javax.swing.JPanel();
        신규직원등록레이블 = new javax.swing.JLabel();
        직원정보패널 = new javax.swing.JPanel();
        직원번호레이블 = new javax.swing.JLabel();
        직급레이블 = new javax.swing.JLabel();
        지점번호레이블 = new javax.swing.JLabel();
        직원명레이블 = new javax.swing.JLabel();
        전화번호레이블 = new javax.swing.JLabel();
        계좌번호레이블 = new javax.swing.JLabel();
        근무시작일레이블 = new javax.swing.JLabel();
        직원번호텍스트필드 = new javax.swing.JTextField();
        지점번호텍스트필드 = new javax.swing.JTextField();
        String[] 콤보박스내용배열 = {
                "점장",
                "메니져",
                "일반직원",
            };
        직급콤보박스 = new javax.swing.JComboBox(콤보박스내용배열);
        직원명텍스트필드 = new javax.swing.JTextField();
        전화번호텍스트필드 = new javax.swing.JTextField();
        계좌번호텍스트필드 = new javax.swing.JTextField();
        근무시작일텍스트필드 = new javax.swing.JTextField();
        은행레이블 = new javax.swing.JLabel();
        은행텍스트필드 = new javax.swing.JTextField();
        버튼패널 = new javax.swing.JPanel();
        등록버튼 = new javax.swing.JButton();
        취소버튼 = new javax.swing.JButton();
        초기화버튼 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        신규직원등록레이블.setFont(new java.awt.Font("굴림", 2, 18)); // NOI18N
        신규직원등록레이블.setForeground(new java.awt.Color(153, 153, 153));
        신규직원등록레이블.setText("신규직원등록");        

        javax.swing.GroupLayout 타이틀패널Layout = new javax.swing.GroupLayout(타이틀패널);
        타이틀패널.setLayout(타이틀패널Layout);
        타이틀패널Layout.setHorizontalGroup(
            타이틀패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(타이틀패널Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(신규직원등록레이블)
                .addContainerGap(204, Short.MAX_VALUE))
        );
        타이틀패널Layout.setVerticalGroup(
            타이틀패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, 타이틀패널Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(신규직원등록레이블)
                .addContainerGap())
        );

        직원정보패널.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        직원번호레이블.setText("직원번호");

        직급레이블.setText("직급");

        지점번호레이블.setText("지점번호");

        직원명레이블.setText("직원명");

        전화번호레이블.setText("전화번호");

        계좌번호레이블.setText("계좌번호");

        근무시작일레이블.setText("근무시작일");

        직원번호텍스트필드.setEditable(false);
        지점번호텍스트필드.setEditable(false);
        근무시작일텍스트필드.setEditable(false);

        은행레이블.setText("은행");

        javax.swing.GroupLayout 직원정보패널Layout = new javax.swing.GroupLayout(직원정보패널);
        직원정보패널.setLayout(직원정보패널Layout);
        직원정보패널Layout.setHorizontalGroup(
            직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(직원정보패널Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(직원정보패널Layout.createSequentialGroup()
                        .addComponent(근무시작일레이블)
                        .addGap(18, 18, 18)
                        .addComponent(근무시작일텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(직원정보패널Layout.createSequentialGroup()
                        .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(지점번호레이블)
                            .addComponent(직원번호레이블)
                            .addComponent(직급레이블)
                            .addComponent(직원명레이블)
                            .addComponent(전화번호레이블)
                            .addComponent(계좌번호레이블))
                        .addGap(29, 29, 29)
                        .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(전화번호텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(직원정보패널Layout.createSequentialGroup()
                                .addComponent(계좌번호텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(은행레이블)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(은행텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(직원명텍스트필드, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(직급콤보박스, javax.swing.GroupLayout.Alignment.LEADING, 0, 75, Short.MAX_VALUE))
                            .addComponent(지점번호텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(직원번호텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        직원정보패널Layout.setVerticalGroup(
            직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(직원정보패널Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(직원번호레이블)
                    .addComponent(직원번호텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(직급레이블)
                    .addComponent(직급콤보박스, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(지점번호레이블)
                    .addComponent(지점번호텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(직원명레이블)
                    .addComponent(직원명텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(전화번호레이블)
                    .addComponent(전화번호텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(계좌번호텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(계좌번호레이블)
                    .addComponent(은행레이블)
                    .addComponent(은행텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(근무시작일레이블)
                    .addComponent(근무시작일텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        초기화버튼.setText("초기화");
        초기화버튼.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                초기화버튼ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout 버튼패널Layout = new javax.swing.GroupLayout(버튼패널);
        버튼패널.setLayout(버튼패널Layout);
        버튼패널Layout.setHorizontalGroup(
            버튼패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, 버튼패널Layout.createSequentialGroup()
                .addComponent(초기화버튼, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(등록버튼, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(취소버튼, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        버튼패널Layout.setVerticalGroup(
            버튼패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(버튼패널Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(버튼패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(등록버튼)
                    .addComponent(취소버튼)
                    .addComponent(초기화버튼))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(버튼패널, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(타이틀패널, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(직원정보패널, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(타이틀패널, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(직원정보패널, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(버튼패널, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    /**
     * 직원 세팅
     * @param 직원번호
     * @param 직급코드
     * @param 지점번호
     * @param 직원이름
     * @param 직원전화번호
     * @param 직원계좌은행
     * @param 직원계좌번호
     * @param 근무시작일
     * @param 근무종료일
     * @param 근무여부
     */
    public void set현재보는직원()
    {
    	QueryParser parser = new QueryParser();
		String query = "";
		
		//정보를 구조체에 저장
		현재보는직원.근무시작일 = 근무시작일텍스트필드.getText().toString();
		현재보는직원.직원번호 = get직원번호(전화번호텍스트필드.getText().toString(), 근무시작일레이블.getText().toString());
		//Index 순서 : 점장, 메니져, 일반직원
		if(직급콤보박스.getSelectedIndex() == 0)	//점장
			query = "SELECTED POSITION_CODE FROM POSITION WHERE POSITION = '점장'";
		else if(직급콤보박스.getSelectedIndex() == 1)	//매니저
			query = "SELECTED POSITION_CODE FROM POSITION WHERE POSITION = '메니져'";
		else if(직급콤보박스.getSelectedIndex() == 2)	//일반직원
			query = "SELECTED POSITION_CODE FROM POSITION WHERE POSITION = '일반직원'";
		현재보는직원.직급코드 = parser.getResultStringArr(query, 2)[0];
		현재보는직원.지점번호 = 지점번호텍스트필드.getText().toString();
		현재보는직원.직원이름 = 직원명텍스트필드.getText().toString();
		현재보는직원.직원전화번호 = 전화번호텍스트필드.getText().toString();
		현재보는직원.직원계좌은행 = 은행텍스트필드.getText().toString();
		현재보는직원.근무시작일 = 근무시작일텍스트필드.getText().toString();
		현재보는직원.근무종료일 = null;
		현재보는직원.근무여부 = "Y";
    }

    private void 초기화버튼ActionPerformed(java.awt.event.ActionEvent evt) {                                      
    	DialogCaller caller = new DialogCaller(mFrame);    	
    	if(caller.showConfirmDialog("입력한 정보를 모두 지우시겠습니까?"))
    	{
    		직원명텍스트필드.setText("");
    		전화번호텍스트필드.setText("");
    		계좌번호텍스트필드.setText("");
    		은행텍스트필드.setText("");    		
    	}
    }                                     

    private void 등록버튼ActionPerformed(java.awt.event.ActionEvent evt) {                                   
    	DialogCaller caller = new DialogCaller(mFrame);
    	//아직 입력 안한값이 있는지 체크
    	if(직원명텍스트필드.getText().toString().equals("")
    			|| 전화번호텍스트필드.getText().toString().equals("")
    			|| 계좌번호텍스트필드.getText().toString().equals("")
    			|| 은행텍스트필드.getText().toString().equals(""))
    	{
    		caller.showMessageDialog("미입력값이 있습니다");
    	}
    	else	//미입력값이 없을 경우
    	{
    		if(caller.showConfirmDialog("정말 등록하시겠습니까?"))
	    	{
    			QueryParser parser = new QueryParser();
    			String query = "";
    			
	    		//정보를 구조체에 저장
	    		set현재보는직원();
	    			    		
	    		//정보를 데이터베이스에 저장
	    		query = "INSERT INTO EMPLOYEE(EMPLOYEE_NO,  POSITION_CODE, BRANCH_NO, EMPLOYEE_NAME, EMPLOYEE_PHONE_NO, EMPLOYEE_ACCOUNT, EMPLOYEE_ACCOUNT_BANK, WORK_START_DATE, WORK_END_DATE, CURRENT_WORK_STATE)"
	    				+ "VALUES (" 
	    				+ 현재보는직원.직원번호 + ", "
	    				+ 현재보는직원.직급코드 + ", "
	    				+ 현재보는직원.지점번호 + ", "
	    				+ 현재보는직원.직원이름 + ", "
	    				+ 현재보는직원.직원전화번호 + ", "
	    				+ 현재보는직원.직원계좌은행 + ", "
	    				+ 현재보는직원.직원계좌번호 + ", "
	    				+ 현재보는직원.근무시작일 + ", "
	    				+ 현재보는직원.근무종료일 + ", "
	    				+ 현재보는직원.근무여부 + ")";
	    		
	    		if(parser.executeQuery(query) != null)
    			{
	    			//메시지 띄우기
	    			caller.showMessageDialog("등록되었습니다. (직원번호 : " + 현재보는직원.직원번호 + ")");   		
    			}

	    		//창닫기
	    		dispose();
	    	}
    	}
    }                                    
    private void 취소버튼ActionPerformed(java.awt.event.ActionEvent evt) {                                     
    	DialogCaller caller = new DialogCaller(mFrame);
    	if(caller.showConfirmDialog("정말 취소하시겠습니까?"))
    	{
    		dispose();
    	}
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
            java.util.logging.Logger.getLogger(신규등록프레임.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(신규등록프레임.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(신규등록프레임.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(신규등록프레임.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new 신규등록프레임(mFrame).setVisible(true);
            }
        });
    }

    private javax.swing.JLabel 계좌번호레이블;
    private javax.swing.JTextField 계좌번호텍스트필드;
    private javax.swing.JLabel 근무시작일레이블;
    private javax.swing.JTextField 근무시작일텍스트필드;
    private javax.swing.JButton 등록버튼;
    private javax.swing.JPanel 버튼패널;
    private javax.swing.JLabel 신규직원등록레이블;
    private javax.swing.JLabel 은행레이블;
    private javax.swing.JTextField 은행텍스트필드;
    private javax.swing.JLabel 전화번호레이블;
    private javax.swing.JTextField 전화번호텍스트필드;
    private javax.swing.JLabel 지점번호레이블;
    private javax.swing.JTextField 지점번호텍스트필드;
    private javax.swing.JLabel 직급레이블;
    private javax.swing.JComboBox 직급콤보박스;
    private javax.swing.JLabel 직원명레이블;
    private javax.swing.JTextField 직원명텍스트필드;
    private javax.swing.JLabel 직원번호레이블;
    private javax.swing.JTextField 직원번호텍스트필드;
    private javax.swing.JPanel 직원정보패널;
    private javax.swing.JButton 초기화버튼;
    private javax.swing.JButton 취소버튼;
    private javax.swing.JPanel 타이틀패널;
}

/***
 *  클래스 구조체 : 직원 엔티티타입
 *  @author Cleartype
 */
class 직원{
	String 직원번호, 직급코드, 지점번호, 직원이름, 직원전화번호, 직원계좌번호, 직원계좌은행, 근무시작일, 근무종료일, 근무여부;
}


/**
 *	직원을 직원번호로 검색하여 해당직원의 정보를 받아와 그 일부를 수정할 수 있게 해주는 프레임
 *	완성도 : 80%
 * @author Cleartype
 */

class 직원정보수정프레임 extends javax.swing.JFrame
{
	boolean is수정버튼 = true;
	static MainFrame mFrame;
	직원 현재보고있는직원;
	
    public 직원정보수정프레임(MainFrame mFrame) {
    	this.mFrame = mFrame;
        initComponents();
    }
    @SuppressWarnings("unchecked")
    private void initComponents() {

    	현재보고있는직원 = new 직원();
    	
        직원정보패널 = new javax.swing.JPanel();
        직급레이블 = new javax.swing.JLabel();
        지점번호레이블 = new javax.swing.JLabel();
        직원명레이블 = new javax.swing.JLabel();
        전화번호레이블 = new javax.swing.JLabel();
        계좌번호레이블 = new javax.swing.JLabel();
        근무시작일레이블 = new javax.swing.JLabel();
        지점번호텍스트필드 = new javax.swing.JTextField();
        직원명텍스트필드 = new javax.swing.JTextField();
        전화번호텍스트필드 = new javax.swing.JTextField();
        계좌번호텍스트필드 = new javax.swing.JTextField();
        근무시작일텍스트필드 = new javax.swing.JTextField();
        은행레이블 = new javax.swing.JLabel();
        은행텍스트필드 = new javax.swing.JTextField();
        직급텍스트필드 = new javax.swing.JTextField();
        타이틀패널 = new javax.swing.JPanel();
        직원정보수정레이블 = new javax.swing.JLabel();
        검색패널 = new javax.swing.JPanel();
        직원번호레이블 = new javax.swing.JLabel();
        직원번호텍스트필드 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        취소버튼 = new javax.swing.JButton();
        수정버튼 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        직원정보패널.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        직급레이블.setText("직급");

        지점번호레이블.setText("지점번호");

        직원명레이블.setText("직원명");

        전화번호레이블.setText("전화번호");

        계좌번호레이블.setText("계좌번호");

        근무시작일레이블.setText("근무시작일");

        지점번호텍스트필드.setEditable(false);

        직원명텍스트필드.setEditable(false);

        근무시작일텍스트필드.setEditable(false);

        은행레이블.setText("은행");

        직급텍스트필드.setEditable(false);
        전화번호텍스트필드.setEditable(false);
        계좌번호텍스트필드.setEditable(false);
        은행텍스트필드.setEditable(false);

        javax.swing.GroupLayout 직원정보패널Layout = new javax.swing.GroupLayout(직원정보패널);
        직원정보패널.setLayout(직원정보패널Layout);
        직원정보패널Layout.setHorizontalGroup(
            직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(직원정보패널Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(직원정보패널Layout.createSequentialGroup()
                        .addComponent(근무시작일레이블)
                        .addGap(18, 18, 18)
                        .addComponent(근무시작일텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(직원정보패널Layout.createSequentialGroup()
                        .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(지점번호레이블)
                            .addComponent(직급레이블)
                            .addComponent(직원명레이블)
                            .addComponent(전화번호레이블)
                            .addComponent(계좌번호레이블))
                        .addGap(29, 29, 29)
                        .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(전화번호텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(직원정보패널Layout.createSequentialGroup()
                                .addComponent(계좌번호텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(은행레이블)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(은행텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(직원명텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(직급텍스트필드, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(지점번호텍스트필드, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)))))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        직원정보패널Layout.setVerticalGroup(
            직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(직원정보패널Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(직급레이블)
                    .addComponent(직급텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(지점번호레이블)
                    .addComponent(지점번호텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(직원명레이블)
                    .addComponent(직원명텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(전화번호레이블)
                    .addComponent(전화번호텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(계좌번호텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(계좌번호레이블)
                    .addComponent(은행레이블)
                    .addComponent(은행텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(직원정보패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(근무시작일레이블)
                    .addComponent(근무시작일텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        직원정보수정레이블.setFont(new java.awt.Font("굴림", 2, 18)); // NOI18N
        직원정보수정레이블.setForeground(new java.awt.Color(153, 153, 153));
        직원정보수정레이블.setText("직원정보수정");

        javax.swing.GroupLayout 타이틀패널Layout = new javax.swing.GroupLayout(타이틀패널);
        타이틀패널.setLayout(타이틀패널Layout);
        타이틀패널Layout.setHorizontalGroup(
            타이틀패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(타이틀패널Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(직원정보수정레이블)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        타이틀패널Layout.setVerticalGroup(
            타이틀패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(타이틀패널Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(직원정보수정레이블)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        검색패널.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        직원번호레이블.setText("직원번호");

        직원번호텍스트필드.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                직원번호텍스트필드ActionPerformed(evt);
            }
        });

        jButton1.setText("조회");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout 검색패널Layout = new javax.swing.GroupLayout(검색패널);
        검색패널.setLayout(검색패널Layout);
        검색패널Layout.setHorizontalGroup(
            검색패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(검색패널Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(직원번호레이블)
                .addGap(30, 30, 30)
                .addComponent(직원번호텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        검색패널Layout.setVerticalGroup(
            검색패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(검색패널Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(검색패널Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(직원번호레이블)
                    .addComponent(직원번호텍스트필드, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        취소버튼.setText("취소");
        취소버튼.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                취소버튼ActionPerformed(evt);
            }
        });

        수정버튼.setText("수정");
        수정버튼.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                수정버튼ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 298, Short.MAX_VALUE)
                .addComponent(수정버튼, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(취소버튼, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(취소버튼)
                    .addComponent(수정버튼))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(타이틀패널, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(직원정보패널, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(검색패널, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(타이틀패널, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(검색패널, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(직원정보패널, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void 직원번호텍스트필드ActionPerformed(java.awt.event.ActionEvent evt) {                                          
    }                                         

    private void 수정버튼ActionPerformed(java.awt.event.ActionEvent evt) {                                     
    	if(is수정버튼 == true)	//버튼이 수정버튼일 때 -> 확인버튼으로 바꿔야함
    	{
    		is수정버튼 = false;
    		수정버튼.setText("확인");
    		전화번호텍스트필드.setEditable(true);
    		계좌번호텍스트필드.setEditable(true);
    		은행텍스트필드.setEditable(true);
    	}
    	else	//버튼이 확인버튼일 때
    	{
    		DialogCaller caller = new DialogCaller(mFrame);
    		if(caller.showConfirmDialog("정말 수정하시겠습니까?"))
    		{
	    		is수정버튼 = true;
	    		수정버튼.setText("수정");
	    		전화번호텍스트필드.setEditable(false);
	    		계좌번호텍스트필드.setEditable(false);
	    		은행텍스트필드.setEditable(false);
	    		//TODO DB UPDATE (전화번호, 계좌번호, 은행) FROM 직원
	    		//TODO '현재보고있는직원' 구조체 값에 넣기
	    		caller.showMessageDialog("수정되었습니다.");
    		}
    	}
    }                                    

    private void 취소버튼ActionPerformed(java.awt.event.ActionEvent evt) {                                     
    	DialogCaller caller = new DialogCaller(mFrame);
    	if(caller.showConfirmDialog("정말 취소하시겠습니까?"))
    	{
    		// TODO 텍스트필드 값 ('현재보고있는직원' 구조체 값)으로 원상복귀
    		is수정버튼 = true;
    		수정버튼.setText("수정");
    		전화번호텍스트필드.setEditable(false);
    		계좌번호텍스트필드.setEditable(false);
    		은행텍스트필드.setEditable(false);
    		dispose();
    	}
    }                                    

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO 직원번호텍스트필드의 값을 가져와 DB에서 직원번호가 같은 칼럼을 받아옴
    	//-> 그 칼럼의 값을 class 구조체 '현재보고있는직원' 에 넣은 후
    	//직급, 지점번호, 이름, 전화번호, 계좌번호, 은행, 근무일자 텍스트필드에 넣음
    	// TODO 해당하는 칼럼이 없으면 해당하는 정보가 없습니다.(확인) 다이얼로그 띄움
    	QueryParser parser = new QueryParser();
    	String query = "SELECT POSITION_CODE, BRANCH_NO, EMPLOYEE_NAME, EMPLOYEE_PHONE_NO, EMPLOYEE_ACCOUNT, EMPLOYEE_ACCOUNT_BANK, WORK_START_DATE"
    			+ "FROM EMPLOYEE"
    			+ "WHERE EMPLOYEE_NO = '"+직원번호텍스트필드.getText().toString()+"'";
    	String result[];
    	if((result = parser.getResultStringArr(query, 7)) == null)
		{
    		DialogCaller caller = new DialogCaller(mFrame);
    		caller.showMessageDialog("해당하는 직원이 없습니다");
    		return;
		}
    	if(result[0].equals("1")) 직급텍스트필드.setText("점장");
    	else if(result[0].equals("2")) 직급텍스트필드.setText("메니져");
    	else if(result[0].equals("3")) 직급텍스트필드.setText("일반직원");
    	지점번호텍스트필드.setText(result[1]);
    	직원명텍스트필드.setText(result[2]);
    	전화번호텍스트필드.setText(result[3]);
    	계좌번호텍스트필드.setText(result[4]);
    	은행텍스트필드.setText(result[5]);
    	근무시작일텍스트필드.setText(result[6]);
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
            java.util.logging.Logger.getLogger(직원정보수정프레임.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(직원정보수정프레임.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(직원정보수정프레임.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(직원정보수정프레임.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new 직원정보수정프레임(mFrame).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel 검색패널;
    private javax.swing.JLabel 계좌번호레이블;
    private javax.swing.JTextField 계좌번호텍스트필드;
    private javax.swing.JLabel 근무시작일레이블;
    private javax.swing.JTextField 근무시작일텍스트필드;
    private javax.swing.JButton 수정버튼;
    private javax.swing.JLabel 은행레이블;
    private javax.swing.JTextField 은행텍스트필드;
    private javax.swing.JLabel 전화번호레이블;
    private javax.swing.JTextField 전화번호텍스트필드;
    private javax.swing.JLabel 지점번호레이블;
    private javax.swing.JTextField 지점번호텍스트필드;
    private javax.swing.JLabel 직급레이블;
    private javax.swing.JTextField 직급텍스트필드;
    private javax.swing.JLabel 직원명레이블;
    private javax.swing.JTextField 직원명텍스트필드;
    private javax.swing.JLabel 직원번호레이블;
    private javax.swing.JTextField 직원번호텍스트필드;
    private javax.swing.JLabel 직원정보수정레이블;
    private javax.swing.JPanel 직원정보패널;
    private javax.swing.JButton 취소버튼;
    private javax.swing.JPanel 타이틀패널;
    // End of variables declaration                   
}
