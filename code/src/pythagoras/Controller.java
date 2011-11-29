package pythagoras;

import pythagoras.types.*;

public class Controller {

	public static void main(String[] args) {
		new Controller();
	}
	
	public Controller() {
		
		// Eventually we'll create a class or some method for
		// the user to enter their own input, but for the moment
		// we'll just hard-code it in.
		
		// What is the meaning of life, the universe, and everything?
		String input = "2(1+2(3*4)+5(6-7)) + 2";
		System.out.print(input + " = ");
		
		Parser parser = new Parser();
		input = parser.parseInitialInput(input);
		
		// If we get valid input, manipulate it and display the output
		if(input != null) {
		
			MathString mathString = MathString.getInstanceForString(input);
			mathString.manipulate();
			
			System.out.println(mathString.getString());
		
		} else {
			
			System.out.println("Invalid Input");
			
		}
			
	}
	
}
