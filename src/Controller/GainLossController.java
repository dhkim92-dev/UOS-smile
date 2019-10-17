package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.LogIn;
import Model.QueryParser;
import View.GainLossPanel;
import View.SmileTable;

public class GainLossController extends AbstractController {

	GainLossPanel panel;
	private SmileTable mTable;
	
	private String[][] data;
	private String[] rows;
	
	public GainLossController(MainController mainController) {
		super(mainController);
	}

	@Override
	void startMenu() {
		initData();
		
		panel = new GainLossPanel(data);
		mTable = panel.mTable;
		
		showMenuPanel(panel);
		
		setListeners();
	}

	public void initData() {
		data = new String[][]{
				{"매출액           : "},
				{"급여               : "},
				{"영업이익       : "},
				{""},
				{"가맹수수료   : "},
				{"총이익           : "}
		};
		
		rows = new String[]{
				"매출액           : ",
				"급여               : ",
				"영업이익       : ",
				"",
				"가맹수수료   : ",
				"총이익           : "
		};
	}
	
	private void setListeners(){
		panel.btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int income, payment, amount, commission, totalAmount;
				String strIncome, strPayment, strAmount, strCommission, strTotalAmount;
				
				String curYear = panel.getYear();
				String subCurYear = curYear.substring(2);
				String curMonth = panel.getMonth();
				
				String nextMonth;
				String nextYear;
				if(curMonth.equals("12")) {
					nextYear = "" + (Integer.parseInt(curYear) + 1);
					nextMonth = "01";
				}
				else {
					nextYear = curYear;
					int iCurMonth = Integer.parseInt(curMonth);
					if(iCurMonth < 9) {
						curMonth = "0" + curMonth;
						nextMonth = "0" + (iCurMonth + 1);
					}
					else
						nextMonth = "" + (iCurMonth + 1);
				}
				String subNextYear = nextYear.substring(2);
				
				/* 매출액 */
				String query = "select sum(sales_amount) from SALE where "
						+ " BRANCH_NO=" + LogIn.branchNumber + " and "
						+ "SALE_TIME>" + subCurYear + curMonth + "00000000 and "
						+ "SALE_TIME<" + subNextYear + nextMonth + "00000000 GROUP BY BRANCH_NO";
				
				String[] result = QueryParser.getResultStringArr(query, 1);
				
				if(result == null){
					strIncome = "0";
				}
				else {
					strIncome = result[0];
				}
				income = Integer.parseInt(strIncome);
				strIncome = rows[0] + putCommas(strIncome);
				mTable.setValue(0, 0, strIncome);
				
				
				/* 매출액 */
				query = "select sum(SALARY.Salary) from SALARY, EMPLOYEE where "
						+ "SALARY.EMPLOYEE_NO=EMPLOYEE.EMPLOYEE_NO and "
						+ "BRANCH_NO=" + LogIn.branchNumber + " and "
						+ "MONTH_NO=" + subCurYear + curMonth + " GROUP BY BRANCH_NO";
				
				System.out.println(query);
				
				result = QueryParser.getResultStringArr(query, 1);
				if(result == null){
					strPayment = "0";
				}
				else {
					strPayment = result[0];
				}

				payment = Integer.parseInt(strPayment);
				strPayment = rows[1] + putCommas(strPayment);
				mTable.setValue(1, 0, strPayment);

				
				/* 영업이익 */
				amount = income - payment;
				strAmount = rows[2] + putCommas(amount);
				mTable.setValue(2, 0, strAmount);
				
				
				/* 가맹수수료 */
				query = "select commission from branch where "
						+"branch_no=" + LogIn.branchNumber;
				String rate = QueryParser.getResultStringArr(query, 1)[0];
				double dbRate = Double.parseDouble(rate);
				dbRate /= 100;
				commission = (int)(amount * dbRate);
				strCommission = rows[4] + putCommas(commission);
				mTable.setValue(4, 0, strCommission);
				
				
				/* 총이익 */
				totalAmount = amount - commission;
				strTotalAmount = rows[5] + putCommas(totalAmount);
				mTable.setValue(5, 0, strTotalAmount);
			}
		});
	}
	
	@Override
	public String toString() {
		return "손익 조회";
	}
}
