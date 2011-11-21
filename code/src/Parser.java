
public class Parser extends MathPart {
	
	// These are the only characters that can appear in a valid
	// mathematical expression/equation
	static String validChars = "1234567890+-";
	
	public Parser() {}
	
	/* ========================================= *
	 * 				String Sanitation			 *
	 * ========================================= */
	private String sanitizeString(String string) {
		
		string = string.replace(" ", "");
		string = string.replace(",", "");
		return string;
		
	}
	
	private boolean isValidCharacter(char c) {
		
		return (validChars.indexOf(c) != -1);
		
	}
	
	// By looping through and verifying each character of
	// the input, we can ensure that the string as a whole
	// is valid
	private boolean isValidInput(String string) {
		
		for(int i = 0; i < string.length(); i++) {
			
			if(!isValidCharacter(string.charAt(i))) return false;
			
		}
		
		return true;
		
	}
	
	/* ========================================= *
	 * 				String Parsing			 	 *
	 * ========================================= */
	
	public void parseString(String string) {
		
		string = sanitizeString(string);
		
		if(isValidInput(string)) {
			
				Expression expression = new Expression(string);
				expression.simplify();
				setValue(expression.getValue());
		
		} else {
			
			setValue("Invalid Input");
			
		}
		
	}
	
}
