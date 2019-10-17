import Controller.LogInController;


public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LogInController logInController = new LogInController();
		logInController.startLogIn();
		
		// TODO 디버그용. 추후 삭제
		logInController.debug_logIn();
	}
}
