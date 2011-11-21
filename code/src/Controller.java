
public class Controller {

	public static void main(String[] args) {
		new Controller();
	}
	
	public Controller() {
		
		// Eventually we'll create a class or some method for
		// the user to enter their own input, but for the moment
		// we'll just hard-code it in.
		String input = "4 12-4";
		
		System.out.print(input + " = ");
		
		Parser parser = new Parser();
		parser.parseString(input);
		System.out.print(parser.getValue());
		//System.out.print(Integer.parseInt(""));
		
		
	}
	
}
