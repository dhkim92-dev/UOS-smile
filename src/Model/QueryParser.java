package Model;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class QueryParser {
	
	private static final String QUERY_XML_FILE_PATH = "./sql/queries.xml";
	
	/** 접속할 database IP 주소. 초기값은 "127.0.0.1" */
	public static String dbIP = "127.0.0.1";
	/** 접속할 port 번호. 초기값은 "1521" */
	public static String portNum = "1521";
	/** 오라클 서비스명. 초기값은 "XE" */
	public static String SID = "XE"; 
	/** DB에 접속할 ID. 초기값은 "uosmile" */
	public static String id = "uosmile";
	/** DB에 접속할 비밀번호. 초기값은 "uosmile" */
	public static String pw = "uosmile";
	
	// jdbc:oracle:thin:@//오라클이설치된컴퓨터ip주소:오라클port번호/오라클의서비스명
	private static String url = "jdbc:oracle:thin:@//" + dbIP + ":" +portNum + "/" + SID;
	
	/** 쿼리문을 실행하기 위한 Statement객체 */
	private static Statement SQLStatement = null;  
	
	/**
	 * 매개변수로 넘어온 id 에 해당하는 query 를 queries.xml 파일에서 찾아서 반환한다.<br>
	 * SELECT query 를 실행할 때, query 문을 변경해도 재컴파일&재실행을 하지 않고 결과를 확인할 수 있다.<br>
	 * INSERT, DELETE, CREATE 등의 query를 실행할 때에는 효용이 떨어짐.<br><br>
	 * 참고 - 쿼리에 ";" 이 포함돼 있으면 invalid character 예외가 발생한다. 
	 */
	public static String getQueryByID(String id){
		File queryFile = new File(QUERY_XML_FILE_PATH);
		
		try {
			Document xmlQueries = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(queryFile);
			XPath xpath = XPathFactory.newInstance().newXPath();
			Node query = (Node)xpath.evaluate("//*[@id='" + id + "']", xmlQueries, XPathConstants.NODE);
			
			if(query == null)
				throw new NoNodeException(id);
			else
				return query.getTextContent(); 
			
		} catch (ParserConfigurationException e) {
			System.out.println("ParserConfigurationException");
			e.printStackTrace();
		} catch (SAXException e) {
			System.out.println("SAXException");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			System.out.println("XPathExpressionException");
			e.printStackTrace();
		} catch (NoNodeException e) {
			System.out.println("ID 가 '" + e.getNodeID() + "'인 질의문을 찾지 못했습니다.");
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * 쿼리 결과인 {@link ResultSet}을 반환한다. SELECT 문을 이용할 때 호출.<br>
	 * 반환된 {@link ResultSet} 객체의 사용이 종료되면 close() 함수를 호출하여<br>
	 * {@link ResultSet} 객체가 이용하는 메모리 자원을 반환해야 한다. <br>
	 * 메소드 내부에서는 {@link Statement}클래스의 executeQuery() 메소드를 호출한다.<br><br>
	 * 참고 - 쿼리에 ";" 이 포함돼 있으면 invalid character 예외가 발생한다. 
	 */
	public static ResultSet executeQuery(String sql){
		if(SQLStatement == null)
			createSQLStatement();
		
		try{
			return SQLStatement.executeQuery(sql);
		}catch(SQLException e){
			System.out.println("QueryParser.executeQuery() 메소드에서 예외 발생");
			System.out.println("SQL 문 수행 실패");
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 쿼리 결과가 적용된 행의 갯수를 반환한다.<br>
	 * INSERT, UPDATE, DELETE, CREATE, ALTER 등을 이용할 때 호출.<br>
	 * 메소드 내부에서는 {@link Statement}클래스의 executeUpdate() 메소드를 호출한다.<br>  
	 */
	public static int executeUpdate(String sql){
		if(SQLStatement == null)
			createSQLStatement();
		
		try{
			return SQLStatement.executeUpdate(sql);
		}catch(SQLException e){
			System.out.println("QueryParser.executeUpdate() 메소드에서 예외 발생");
			System.out.println("SQL 문 수행 실패");
			e.printStackTrace();
		}
		
		return -1;
	}
	
	
	/**
	 * 질의 결과를 일차원 배열로 반환한다. 첫번째 결과만 반환된다.
	 */
	public static String[] getResultStringArr(String query, int columnLength){
		// 찾은 query 문을 DB 에 질의하여 결과를 얻는다.
		ResultSet rs = QueryParser.executeQuery(query);
		
		String[] arrResult = new String[columnLength];
		
		int resultColumnNumber = columnLength;	// 결과 데이터의 열 길이

		try {
			if(rs.next()) { // 결과가 있으면
				for(int j=0; j<resultColumnNumber; j++)
					arrResult[j] = rs.getString(j+1);
			}
			else{
				System.out.println("QueryParser.getResultStringArr() 결과 없음");
				return null;
			}
			
			rs.close();	// 사용이 끝난 ResultSet 은 항상 close() 해 준다.
			
		} catch (SQLException err) {
			err.printStackTrace();
			System.out.println("QueryParser.getResultStringArr() 에러 발생");
			return null;
		}
		
		return arrResult;
	}
	
	/**
	 * 질의 결과를 이차원 배열로 반환한다. 
	 */
	public static String[][] getResultString2DArr(String query, int columnLength){
		// 찾은 query 문을 DB 에 질의하여 결과를 얻는다.
		ResultSet rs = QueryParser.executeQuery(query);
		
		// DB 질의 결과를 담을 String ArrayList
		ArrayList<String[]> result = new ArrayList<String[]>();
		
		int resultColumnNumber = columnLength;	// 결과 데이터의 열 길이

		try {
			if(!rs.isBeforeFirst()){
				System.out.println("QueryParser.getResultStringArr() 결과 없음");
				return null;
			}
			
			int i = -1; // rs 의 index 는 -1 부터 시작한다.
			
			while(rs.next()) { // 다음 index 에 결과가 있으면
				i++;
				result.add(new String[resultColumnNumber]);
				
				for(int j=0; j<resultColumnNumber; j++)
					result.get(i)[j] = rs.getString(j+1);
			}
			
			rs.close();	// 사용이 끝난 ResultSet 은 항상 close() 해 준다.
			
		} catch (SQLException err) {
			err.printStackTrace();
			System.out.println("QueryParser.getResultStringArr() 에러 발생");
			return null;
		}
		
		String[][] arrResult = new String[result.size()][];
		for(int i=0; i<arrResult.length; i++)
			arrResult[i] = result.get(i);
		
		return arrResult;
	}
	
	/*ResultSet의 Row수 반환*/
	public static int getResultSetRowQuantity(ResultSet rs){
		int quantity=0;
		try {
			rs.last();
			quantity = rs.getRow();
			rs.beforeFirst();
			return quantity;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	private static void createSQLStatement() {
		Connection conn = null;

		try {
			// 오라클에 접속이 되면 Connection의 객체주소값을 얻을 수 있다.
			conn = DriverManager.getConnection(url, id, pw);

		} catch (SQLException e) {
			System.out.println("QueryParser.createStatement() 메소드에서 예외 발생");
			System.out.println("Oracle DB 에 접속 실패");
			e.printStackTrace();
		}

		try {
			// 쿼리문을 실행하기 위한 Statement객체
			/*06-07 <작성자> 김도훈, 기존의 코드에서 역순이 안된다고 요청하셔서 ResultSet의 역순 진행을 가능하게 소스 추가했습니다.*/
			SQLStatement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		} catch (NullPointerException e) {
			System.out.println("QueryParser.createStatement() 메소드에서 NullPointerException 발생");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("QueryParser.createStatement() 메소드에서 예외 발생");
			System.out.println("Connection 인스턴스로부터 Statement 객체 생성 실패");
			e.printStackTrace();
		}
	}
}

class NoNodeException extends Exception {
	private static final long serialVersionUID = 12634589761203L;
	
	private String NodeID;
	
	public NoNodeException(String id) {
		this.NodeID = id;
	}
	
	public String getNodeID(){
		return NodeID == null ? "" : NodeID;
	}
}

/** QueryParser 를 매번 쓰기 귀찮아서 만든 클래스 */
class Q extends QueryParser{
	
}