import java.util.*;

// This class consists solely of static methods used to parse input
public class Parser {
	
	// These are the only characters that can appear in a valid
	// mathematical expression/equation
	static String validChars = "1234567890+-*/()";
	static String numerals = "0123456789";
	
	/* ========================================= *
	 * 				String Sanitation			 *
	 * ========================================= */
	private static String sanitizeString(String string) {
		
		// uniformity is key!
		string = string.replace(" ", "");
		string = string.replace(",", "");
		
		// it's easier to deal only with addition than to deal with addition and subtraction
		string = string.replace("-", "+-");
		return string;
		
	}
	
	private static boolean isValidCharacter(char c) {
		
		return (validChars.indexOf(c) != -1);
		
	}
	
	// By looping through and verifying each character of
	// the input, we can ensure that the string as a whole
	// is valid
	private static boolean isValidInput(String string) {
		
		for(int i = 0; i < string.length(); i++) {
			
			if(!isValidCharacter(string.charAt(i))) return false;
			
		}
		
		return true;
		
	}
	
	/* ========================================= *
	 * 				Input Parsing			 	 *
	 * ========================================= */
	public static String parseInitialInput(String string) {
		
		string = sanitizeString(string);
		
		if(isValidInput(string)) {
			
				Expression expression = new Expression(string);
				expression.simplify();
		
				return expression.getValue();
				
		} else {
			
			return "Invalid Input";
			
		}
	}
	
	/* ========================================= *
	 * 			Expression Parsing			 	 *
	 * ========================================= */
	
	public static ArrayList<Term> parseExpression(String string) {
		
		ArrayList<Term> terms = new ArrayList<Term>();
		
		int lastStop = 0;
		
		// by appending a sign to the end of the string, we force it to catch the last term
		string = string.concat("+");
		
		// if the first character of the expression isn't a sign, we need to append the appropriate sign
		if(string.charAt(0) != '+' && string.charAt(0) != '-') string = "+".concat(string);
		
		// we start at index 1 so that we don't catch the initial sign
		for(int currentIndex = 1; currentIndex < string.length(); currentIndex++) {
			
			// the terms are terminated by the signs of the proceeding terms
			// eg: 42+8-6 -> (+42)(+8)(-6)
			if(string.charAt(currentIndex) == '+') {
				
				Term term = new Term(string.substring(lastStop + 1, currentIndex));
				terms.add(term);
				lastStop = currentIndex;
			
			// we want to ignore signs within parentheses because they are part of
			// the current term
			} else if(string.charAt(currentIndex) == '(') {
				
				currentIndex = string.indexOf(')', currentIndex);
				
			}
			
		}
		
		return terms;
		
	}
	
	/* ========================================= *
	 * 				Term Parsing			 	 *
	 * ========================================= */ 
	 
	 public static String parseTerm(String string) {

		int parenthesesIndex = string.indexOf('(');
		
		// if there is an open parentheses that is not proceeded by a sign, multiplication is implied
		// this code simply makes that multiplication explicit by inserting the appropriate sign
		if(parenthesesIndex >= 0) {
			
			if(string.charAt(parenthesesIndex - 1) != '-' &&
			   string.charAt(parenthesesIndex - 1) != '+' &&
			   string.charAt(parenthesesIndex - 1) != '*' &&
			   string.charAt(parenthesesIndex - 1) != '/') {
				
				string = new StringBuffer(string).insert(parenthesesIndex, '*').toString();
				
			}
		}
		
		string = addParentheses(string);
		return string;
		
		
	}
	
	// Parentheses will be used to group numbers:
	// 2 => (2)
	// 2*6 => (2)*(6)
	// 2(6+4) => 2*(6+4) => (2)*(6+4) => (2)*(10) 
	private static String addParentheses(String string) {
		
		for(int currentIndex = 0; currentIndex < string.length(); currentIndex++) {
			
			char currentChar = string.charAt(currentIndex);
			
			// At every numeral or negative sign, we need an open parentheses
			if(numerals.indexOf(currentChar) >= 0 || currentChar == '-') {
				string = new StringBuffer(string).insert(currentIndex, '(').toString();
				
				// then we find the closest multiplication or division symbol
				int nextMult = string.indexOf('*', currentIndex);
				int nextDiv  = string.indexOf('/', currentIndex);
				
				if(nextMult == -1 && nextDiv >= 0) {
					currentIndex = nextDiv;
				} else if(nextDiv == -1 && nextMult >= 0) {
					currentIndex = nextMult;
				} else {
					currentIndex = (nextMult > nextDiv) ? nextDiv : nextMult;
				}
				
				// if we find one, we pick the closest and put the close parentheses there
				// if we don't, we just put it at the end of the term
				if(currentIndex == -1) {
					string = new StringBuffer(string).insert(string.length(), ')').toString();
					break;
				} else {
					string = new StringBuffer(string).insert(currentIndex, ')').toString();
				}
			
			// let's ignore parenthetical expressions for now
			} else if(currentChar == '(') {
				
				currentIndex = string.indexOf(')', currentIndex);
				
			}
			
		}
		
		return string;
		
	}
	
	/* ========================================= *
	 * 				Value Parsing			 	 *
	 * ========================================= */
	
	// This returns an integer value for a given string:
	// (8) => 8
	
	public static int parseValue(String string) {
		
		return Integer.parseInt(string.substring(1, string.length() - 1));
		
	}
	
}
