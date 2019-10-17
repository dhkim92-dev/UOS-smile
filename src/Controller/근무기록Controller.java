package Controller;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Model.DialogCaller;
import Model.QueryParser;
import View.MainFrame;
import View.SmileTable;

public class 근무기록Controller extends AbstractController
{
	JPanel 직원번호조회패널;
	public JPanel 근무기록패널;
	SmileTable 직원리스트테이블;
	JPanel 버튼패널;
	JLabel 직원수레이블;
	JButton 근무시작버튼, 근무종료버튼, 근무여부변경버튼, 조회버튼;
	JTextField 직원번호텍스트필드;
	
	int 직원수 = 0;
	
	public 근무기록Controller(MainController mainController) {
		super(mainController);
	}

	@Override
	void startMenu() {
		setComponents();
		addListeners();
		addComponents();
		createTable();
		showMenuPanel(근무기록패널);		
	}
	
	/**
	 * 처음 메뉴를 클릭했을 때 보이는 데이터를 반환하고 그 수를 레이블에 입력한다.
	 */
	public String[][] getInitDataInTable()
	{
		QueryParser parser = new QueryParser();
		String query = parser.getQueryByID("getAllWorkingRecord");
		직원수 = parser.executeUpdate(query);
		String[][] data = parser.getResultString2DArr(query, 5);
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
		String query = "SELECT * FROM WORKING_RECORD WHERE EMPLOYEE_NO = "+직원번호;
		직원수 = parser.executeUpdate(query);
		String[][] data = parser.getResultString2DArr(query, 5);
		return data;
	}
	
	public void addComponents()
	{
		직원번호조회패널.add(new JLabel("직원번호"));

		직원번호조회패널.add(직원번호텍스트필드);
		직원번호조회패널.add(조회버튼);
		직원번호조회패널.add(직원수레이블);
		근무기록패널.add(직원번호조회패널, BorderLayout.NORTH);
		
		버튼패널.add(근무시작버튼);
		버튼패널.add(근무종료버튼);
		버튼패널.add(근무여부변경버튼);
		근무기록패널.add(버튼패널, BorderLayout.SOUTH);
	}
	
	public void setComponents()
	{
		//패널 세팅
		근무기록패널 = new JPanel();
		근무기록패널.setLayout(new BorderLayout(10, 10));
		근무기록패널.setBorder(new EmptyBorder(10, 15, 10, 15)); // Margin은 여타 패널과 같게
		버튼패널 = new JPanel();
		버튼패널.setLayout(new FlowLayout());
		직원번호조회패널 = new JPanel();
		직원번호조회패널.setLayout(new FlowLayout());
//		버튼패널.setLayout(new BoxLayout(버튼패널, BoxLayout.Y_AXIS));
		
		//컴포넌트 세팅		
		직원수레이블 = new JLabel("총 "+직원수+" 직원");
		근무시작버튼 = new JButton("근무 시작");
		근무종료버튼 = new JButton("근무 종료");
		조회버튼 = new JButton("조회");
		근무여부변경버튼 = new JButton("근무 여부 변경");
		직원번호텍스트필드 = new JTextField(15);
	}
	
	//근무기록 구조체에 하루임금을 계산해줌
	/**
	 * 00시부터 12시까지는 주간시급, 12시부터 24시까지는 야간시급으로 친다.
	 * @param _근무기록
	 */
	public int get하루임금(근무기록 _근무기록)
	{
		int start_time, fin_time, 주간시급, 야간시급, 하루임금 = 0;
		start_time = Integer.valueOf(_근무기록.근무시작시간.substring(0, 2));	//시간만 받아옴
		fin_time = Integer.valueOf(_근무기록.근무종료시간.substring(0, 2));	//시간만 받아옴
		
		QueryParser parser = new QueryParser();
		String query = parser.getQueryByID("getAllHourlyWage");
		String[][] 시급 = parser.getResultString2DArr(query, 2);
		if(시급[0][0].equals("D"))	//첫번째 칼럼이 주간시급이면
		{
			주간시급 = Integer.valueOf(시급[0][1]);
			야간시급 = Integer.valueOf(시급[1][1]);
		}
		else	//첫번재 칼럼이 야간시급이면
		{
			주간시급 = Integer.valueOf(시급[1][1]);
			야간시급 = Integer.valueOf(시급[0][1]);			
		}
		if(start_time <= 12 && fin_time <= 12)
		{
			하루임금 = 주간시급 * (fin_time - start_time);
		}
		else if(start_time <= 12 && fin_time >= 12)
		{
			하루임금 = 주간시급 * (12-start_time) + 야간시급 * (fin_time-12);
		}
		else if(start_time >= 12 && fin_time >= 12)
		{
			하루임금 = 야간시급 * (fin_time - start_time);			
		}
		return 하루임금;
	}
	
	public void addListeners()
	{
		//프레임 설정
		final 직원정보조회프레임 조회프레임 = new 직원정보조회프레임(mainFrame);

		근무시작버튼.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showMessageDialog("근무를 시작합니다 : " + getCurrentTime());
				//TODO 해당 로그인 정보에 해당하는 직원번호의 근무날짜와 근무시작시간을 DB에 저장
			}
		});
		
		근무종료버튼.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showMessageDialog("근무를 종료합니다 : " + getCurrentTime());
				//TODO 해당 로그인 정보에 해당하는 직원번호의 근무날짜와 근무종료시간을 DB에 저장
				//근무날짜가 바뀌었을 경우(=오늘날짜와 직원번호를 가진 컬럼이 없을 경우)
				//근무종료시간이 null인 칼럼에 24:00을 넣고 하루임금 기록 후 새로운 insert 쿼리를 만들어서
				//근무 시작시간에 00:00을 넣고 근무 종료시간과 날짜를 기록
				//하루임금을 계산하여 기록
			}
		});
		
		근무여부변경버튼.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				조회프레임.show();
			}
		});
		
		조회버튼.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 직원번호텍스트필드값과 일치하는 직원번호를 가진 것을 DB에서 꺼내와서 반환
				//여기서는 테스트 데이터로 테스트
//				String[][] testdata = 
//					{
//						{ "5", "2014-01-01", "01:00", "19:00", null },
//						{ "1", "2014-01-01", "00:00", "19:00", null },
//						{ "7", "2014-01-01", "18:00", "19:00", null },
//						{ "0", "2014-01-01", "19:00", "00:00", null },
//						{ "8", "2014-01-01", "15:00", "19:00", null }
//					};				
//				직원리스트테이블.setData(testdata);
				String[][] data = getInitDataInTable();
				직원리스트테이블.setData(data);
			}
		});
		
	}
	
	public void createTable()
	{
/* <<< SmileTable 화면에 나타내기 - 시작 >>> */
		// SmileTable (1) table 의 열 이름 설정
		String[] columns = {"직원번호", "근무날짜", "근무시작시간", "근무종료시간", "하루임금"};
				
		// SmileTable (2) table 에 입력될 데이터 설정
		String[][] data = 
		{
			{ "5", "2014-01-01", "01:00", "19:00", null },
			{ "1", "2014-01-01", "00:00", "19:00", null },
			{ "7", "2014-01-01", "18:00", "19:00", null },
			{ "0", "2014-01-01", "19:00", "00:00", null },
			{ "8", "2014-01-01", "15:00", "19:00", null }
		};				
		//TODO 실제 데이터 작업 시에는 이 부분을 활성화시킬 것
//		String[][] data = getInitDataInTable();
		
		// SmileTable (3) 열 이름과 데이터로 새로운 테이블을 생성한다.
		직원리스트테이블 = new SmileTable(columns, data);
		
		// SmileTable (4) 테이블에 관한 설정사항을 지정.
		// 해당 설정을 사용하지 않으려면 함수를 호출하지 않으면 됨.
		직원리스트테이블.setSelectable(false);	// 개별 선택 가능(앞쪽에 check box 열이 추가됨)
		직원리스트테이블.setDeletable(false);	// 선택된 row 를 삭제할 수 있음
		직원리스트테이블.setEmptiable(false);	// 전체 삭제 불가능
		
		// SmileTable (5) table 이 들어 있는 panel을 가져와서 현재 메뉴 패널에 설정.
		JPanel tablePanel = 직원리스트테이블.getTablePanel();
		근무기록패널.add(tablePanel, BorderLayout.CENTER);

		/* <<< SmileTable 화면에 나타내기 - 끝 >>> */
		
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
     * 현재 시간을 TT:MM:SS 형식의 String으로 반환해준다.
     * @return
     */
    public String getCurrentTime()
    {
    	String time = "";
    	int h, m, s;
    	Calendar oCalendar = Calendar.getInstance( );  // 현재 날짜/시간 등의 각종 정보 얻기

        h = oCalendar.get(Calendar.HOUR_OF_DAY);
        if(h < 10) time += "0"+h;
		else time += h;
        time += ":";
		m = oCalendar.get(Calendar.MINUTE);
		time += ':';
		if(m < 10) time += "0"+m;
		else time += m;
		s = oCalendar.get(Calendar.SECOND);
		time += ':';
		if(s < 10) time += "0"+s;
		else time += s;

        return time;
    }
	@Override
	public String toString() {
		return "근무 기록";
	}
}

class 근무기록
{
	String 직원번호, 근무날짜, 근무시작시간, 근무종료시간, 하루임금;
}

/**
 *	직원을 직원번호로 검색하여 해당직원의 정보를 받아와 그 일부를 수정할 수 있게 해주는 프레임
 *	완성도 : 80%
 * @author Cleartype
 */

class 직원정보조회프레임 extends javax.swing.JFrame
{
	boolean is해고버튼 = true;
	static MainFrame mFrame;
	직원 현재보고있는직원;
	
    public 직원정보조회프레임(MainFrame mFrame) {
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
        해고버튼 = new javax.swing.JButton();
        해고버튼 = new javax.swing.JButton();

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
        직원정보수정레이블.setText("직원정보조회");

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

        해고버튼.setText("직원을 해고합니다");
        해고버튼.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                해고버튼ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 298, Short.MAX_VALUE)
                .addComponent(해고버튼, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(해고버튼))
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

    private void 해고버튼ActionPerformed(java.awt.event.ActionEvent evt) {                                     
    	DialogCaller caller = new DialogCaller(mFrame);
    	if(caller.showConfirmDialog("정말 해고하시겠습니까?"))
    	{
    		QueryParser parser = new QueryParser();
    		String query = "UPDATE WORKING_RECORD SET CURRENT_WORK_STATE = 'N' WHERE EMPLOYEE_NO = '"+직원번호텍스트필드.getText().toString()+"'";
    		parser.executeQuery(query);
    		caller.showMessageDialog("해고되었습니다");
    		dispose();
    	}
    }                                    

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO 직원번호텍스트필드의 값을 가져와 DB에서 직원번호가 같은 칼럼을 받아옴
    	//-> 그 칼럼의 값을 직급, 지점번호, 이름, 전화번호, 계좌번호, 은행, 근무일자 텍스트필드에 넣음
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
            java.util.logging.Logger.getLogger(직원정보조회프레임.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(직원정보조회프레임.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(직원정보조회프레임.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(직원정보조회프레임.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new 직원정보조회프레임(mFrame).setVisible(true);
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
    private javax.swing.JButton 해고버튼;
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
    private javax.swing.JPanel 타이틀패널;
    // End of variables declaration                   
}