
public class Controller {

	public static void main(String[] args) {
		new Controller();
	}
	
	public Controller() {
		
		// Eventually we'll create a class or some method for
		// the user to enter their own input, but for the moment
		// we'll just hard-code it in.
		String input = "4-9/3-6*8";
		
		System.out.print(input + " = ");
		System.out.print(Parser.parseInitialInput(input));
		
		
	}
	
}
