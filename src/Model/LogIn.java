package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LogIn {
	public static final String EMPLOYEE_CODE_OWNER = "1";
	public static final String EMPLOYEE_CODE_MANAGER = "2";
	public static final String EMPLOYEE_CODE_SLAVE = "3";
	
	public static String branchNumber;
	public static String employeeNumber;
	public static String employeeCode;
	
	public static boolean login(String emNum, String emPW){
		String query = "select Branch_no from LOGIN_INFORMATION "
				+ "where Employee_no=" + emNum + " and Login_Password='" + emPW + "'";
		
		ResultSet rsLogin = Q.executeQuery(query);

		if(rsLogin==null)
			return false;
		else{
			try {
				ResultSet rsCode = null;
				if(rsLogin.next()) {
					System.out.println("결과 있음");
					branchNumber = rsLogin.getString(1);
					employeeNumber = emNum;
					rsCode = Q.executeQuery("select Position_code from EMPLOYEE where Employee_no=" + emNum);
				}
				else {// 결과가 없는 경우
					System.out.println("결과 없음");
					return false;
				}
				rsCode.next();
				employeeCode = rsCode.getString(1);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		System.out.println("branchNum : " + LogIn.branchNumber);
		System.out.println("employeeNum : " + LogIn.employeeNumber);
		System.out.println("employeeCode : " + LogIn.employeeCode);
		
		return true;
	}
}
