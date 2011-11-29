package pythagoras;

// This class consists solely of static methods used to parse initial, generic input
public class Parser {
	
	// This class can be expanded upon to be made more accurate, but for now
	// we'll just stick with these methods
	
	// These are the only characters that can appear in a valid math string
	static String validChars = "1234567890+-*/(),<>[]{}!%^";
	static String numerals = "0123456789";
	
	/* ========================================= *
	 * 		String Sanitation and Validation	 *
	 * ========================================= */
	private boolean charactersAreValid(String string) {
		
		for(int i = 0; i < string.length(); i++) {
			
			if(validChars.indexOf(string.charAt(i)) == -1) return false;
			
		}
		
		return true;
		
	}
	
	// Checks that all open parentheses have a matching closing parentheses 
	private boolean parenthesesAreMatched(String string) {
		
		int count = 0;
		
		for(int i = 0; i < string.length(); i++) {
			
			if(string.charAt(i) == '(') count++;
			if(string.charAt(i) == ')') count--;
			
			if(count < 0) return false;
		}
		
		if(count == 0) return true;
		
		return false;
		
	}
	
	// Checks that all open brackets have a matching closing bracket 
	private boolean bracketsAreMatched(String string) {
		
		int count = 0;
		
		for(int i = 0; i < string.length(); i++) {
			
			if(string.charAt(i) == '[') count++;
			if(string.charAt(i) == ']') count--;
			
			if(count < 0) return false;
		}
		
		if(count == 0) return true;
		
		return false;
		
	}
	
	private boolean isValidInput(String string) {
		
		if(!charactersAreValid(string)) return false;
		if(!parenthesesAreMatched(string)) return false;
		if(!bracketsAreMatched(string)) return false;
		
		return true;
		
	}
	
	/* ========================================= *
	 * 				Input Parsing			 	 *
	 * ========================================= */
	public String parseInitialInput(String string) {
		
		string = string.replace(" ", "");
		
		if(isValidInput(string)) {
			
			return string;
			
		} else {
			
			return null;
			
		}
		
	}
	
}
