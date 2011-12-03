package pythagoras.strings;

public abstract class Validator {
	
	static String validChars = "1234567890+-*/(),<>[]^";
	static String numerals = "0123456789";
	
	public abstract boolean stringIsValidForType(String string);
	
	// This function handles validation methods that apply to ALL input types
	public static boolean stringIsValid(String string) {
		
		if(!charactersAreValid(string)) return false;
		if(!parenthesesAreMatched(string)) return false;
		if(!bracketsAreMatched(string)) return false;
		
		return true;
		
	}
	
	private static boolean charactersAreValid(String string) {
		
		for(int i = 0; i < string.length(); i++) {
			
			if(validChars.indexOf(string.charAt(i)) == -1) return false;
			
		}
		
		return true;
		
	}
	
	// Checks that all open parentheses have a matching closing parentheses 
	private static boolean parenthesesAreMatched(String string) {
		
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
	private static boolean bracketsAreMatched(String string) {
		
		int count = 0;
		
		for(int i = 0; i < string.length(); i++) {
			
			if(string.charAt(i) == '[') count++;
			if(string.charAt(i) == ']') count--;
			
			if(count < 0) return false;
		}
		
		if(count == 0) return true;
		
		return false;
		
	}
	
}
