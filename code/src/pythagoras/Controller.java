package pythagoras;

import pythagoras.strings.MathString;

public class Controller {

	public static void main(String[] args) {
		new Controller();
	}
	
	public Controller() {
		
		// Eventually we'll create a class or some method for
		// the user to enter their own input, but for the moment
		// we'll just hard-code it in.
		
		// What is the meaning of life, the universe, and everything?
		String input = "(7+9(4-5^(8)))/2";
		System.out.print(input + " = ");;
		
		// This function validates input and then parses it for the appropriate type
		MathString mathString = MathString.getInstanceForString(input);
		
		if(mathString != null) {
			
			mathString.manipulate();
			System.out.print(mathString.getString());
		
		} else {
			
			System.out.print("Invalid Input");
			
		}
			
	}
	
}
