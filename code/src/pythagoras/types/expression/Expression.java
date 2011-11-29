package pythagoras.types.expression;

import pythagoras.types.*;

public class Expression extends MathString{
	
	private static int INVALID_INPUT = -1000000;
	
	/* ========================================= *
	 * 		         String Parsing		 	     *
	 * ========================================= */ 
	private static class ExpressionParser {

		public static String parse(String string) {

			// uniformity is key!
			string = string.replace(",", "");
			string = string.replace("-", "+-");
			
			// The order of these function calls is important (leave them like this)
			string = addNegativeOnes(string);
			string = addMultSymbols(string);
			string = addParentheses(string);
			
			return string;
			
		}
		
		private static String insert(String insert, int index, String string) {
			
			String front = string.substring(0, index);
			String end   = string.substring(index);
			
			string = front + insert + end;
			
			return string;
			
		}
		
		// Make implied negative 1s explicit
		// eg: -(9-5) -> -1(9-5)
		private static String addNegativeOnes(String string) {
			
			for(int i = 1; i < string.length(); i++) {
				
				char currentChar = string.charAt(i);
				char prevChar    = string.charAt(i-1);
				
				if(currentChar == '(' && prevChar == '-') {
					
					string = insert("1", i, string);
					i++;
					
				}
				
			}
			
			return string;
		}
		
		// Make implied multiplication explicit
		// eg: 2(7) -> 2*(7)
		private static String addMultSymbols(String string) {
			
			for(int i = 1; i < string.length(); i++) {
				
				char currentChar = string.charAt(i);
				char prevChar    = string.charAt(i-1);
				
				if(currentChar == '(' && !isTerminator(prevChar)) {
					
					string = insert("*", i, string);
					i++;
					
				}
				
			}
			
			return string;
		}
		
		// This checks whether a given character is a "terminator"
		// A terminator is a cyborg with the intent of destroying mankind
		private static boolean isTerminator(char currentChar) {
			
			return (currentChar == '*' || currentChar == '/' || currentChar == '+');
			
		}
		
		// We use parentheses to isolate individual values:
		// 2 + 4 * 8 -> (2)+(4)*(8)
		private static String addParentheses(String string) {
			
			// If the string is only a number (not an expression),
			// then we return the string in the form (<number>)
			if(string.indexOf('+') == -1 && string.indexOf('*') == -1 && string.indexOf('/') == -1) {
				
				// Get rid of excess parentheses
				string = string.replace('(', '\0');
				string = string.replace(')', '\0');
				
				return "(" + string + ")";
			
			}
			
			// All strings should either start with a + or (
			if(string.charAt(0) != '(' && string.charAt(0) != '+') string = "(" + string;
			
			// By appending the + to the end, we ensure that the last term is parsed and our
			// parentheses are closed.
			string = string + "+";
			
			for(int i = 1; i < string.length(); i++) {
				
				char currentChar = string.charAt(i);
				char prevChar = string.charAt(i-1);
				
				// Insert closing parentheses before every *, /, or +
				if(isTerminator(currentChar) && prevChar != ')') {
					
					string = insert(")", i, string);
					i+= 1;
					
				}
				
				// Insert opening parentheses after every *, /, or +
				if(isTerminator(prevChar) && currentChar != '(') {
					
					string = insert("(", i, string);
					i+= 1;
					
				}
				
				// When we find a parenthetical expression
				if(currentChar == '(') {
					
					// We find the end of the expression
					int close = getClosingParenthesesFromStart(i, string);
					
					String front = string.substring(0, i+1);
					String end   = string.substring(close);
					
					// We then parse the middle as its own expression
					String middle = addParentheses(string.substring(i+1, close));
					
					string = front + middle + end;
					
					// Continue from the end of the parenthetical
					i += middle.length();
					
				}

			}
			
			// Remove the plus sign that we appended in the beginning
			string = string.substring(0, string.length() - 1);
			
			return string;
			
		}
		
		// Given an index of an opening parentheses, find the matching closing parentheses
		private static int getClosingParenthesesFromStart(int index, String string) {
			
			if(string.charAt(index) != '(') return INVALID_INPUT;
			
			int count = 0;
			for(int i = index; i < string.length(); i++) {
				
				char currentChar = string.charAt(i);
				
				if(currentChar == '(') count++;
				if(currentChar == ')') count--;
				
				if(count == 0) return i;
				
			}
			
			return INVALID_INPUT;
			
		}
	}
	
	/* ========================================= *
	 * 		       String Validation		 	 *
	 * ========================================= */ 
	
	// This class can be expanded upon to be made more accurate, but for now
	// we'll just stick with these methods
	public static class ExpressionValidator {
		
		// The first character of an expression cannot be *, /, or )
		private static boolean firstCharIsValid(String string) {
			
			String invalidChars = "*/)";
			return (invalidChars.indexOf(string.charAt(0)) == -1);
			
		}
		
		private static boolean charactersAreValid(String string) {
			
			String validChars = ".0123456789+-/*()";
			
			for(int i = 0; i < string.length(); i++) {
				
				if(validChars.indexOf(string.charAt(i)) == -1) return false;
				
			}
			
			return true;
		}
		
		// Checks that weird things like 4+*5 don't occur
		private static boolean signsAreValid(String string) {
			
			String validChars = ".0123456789+-(";
			
			for(int i = 0; i < string.length(); i++) {
				
				char currentChar = string.charAt(i);
				if(currentChar == '+' || currentChar == '-') {
					
					if(validChars.indexOf(string.charAt(i+1)) == -1) return false;
					
				}
				
			}
			
			return true;
		}
		
		public static boolean isValidExpression(String string) {
			
			if(!firstCharIsValid(string)) return false;
			if(!charactersAreValid(string)) return false;
			if(!signsAreValid(string)) return false;
			
			return true;
			
		}
		
	}
	
	/* ========================================= *
	 * 		        Instance Methods		 	 *
	 * ========================================= */ 
	
	public Expression(String string) {
		
		super(MathString.STRING_TYPE.EXPRESSION, string);
		setString(ExpressionParser.parse(getString()));
		
	}
	
	public void manipulate() {
		
		setString(simplify(getString()));
		
	}
	
	private String simplify(String string) {
		
		string = multiplyAndDivide(string);
		string = add(string);
		return string;
		
	}
	
	private String multiplyAndDivide(String string) {
		
		while(string.indexOf('*') >= 0 || string.indexOf('/') >= 0) {
			
			// Find the index of the closest * or /
			int nextMult = string.indexOf('*');
			int nextDiv = string.indexOf('/');
			
			if(nextDiv < 0) nextDiv = nextMult + 1;
			if(nextMult < 0) nextMult = nextDiv + 1;
			
			int index = (nextMult < nextDiv) ? nextMult : nextDiv; 
			
			// Find the first index of the first value and the last index of
			// the second value
			int start = getStartIndexOfValueBefore(index, string);
			int end   = getEndIndexOfValueAfter(index, string);
			
			double valueOne = getValueOfString(string.substring(start, index));
			double valueTwo = getValueOfString(string.substring(index+1, end+1));
			
			double result = 0;
			
			if(valueOne == INVALID_INPUT || valueTwo == INVALID_INPUT) return "Invalid Input";
			
			// Perform the operation
			// NOTE: remember that it is not possible for nextDiv and nextMult to be equal
			// therefore, an operation is guaranteed to occur
			if(nextMult < nextDiv) result = valueOne*valueTwo;
			if(nextMult > nextDiv) result = valueOne/valueTwo;
			
			// Replace the old operation with the result
			string = string.substring(0, start+1) + result + string.substring(end);
			
		}
		
		return string;
		
	}
	
	private String add(String string) {
		
		while(string.indexOf('+') >= 0) {
			
			int index = string.indexOf('+'); 
			
			// Find the first index of the first value and the last index of
			// the second value
			int start = getStartIndexOfValueBefore(index, string);
			int end   = getEndIndexOfValueAfter(index, string);
			
			double valueOne = getValueOfString(string.substring(start, index));
			double valueTwo = getValueOfString(string.substring(index+1, end+1));
			
			double result = valueOne + valueTwo;
			
			// Replace the old operation with the result
			string = string.substring(0, start+1) + result + string.substring(end);
			
		}
		
		return string;
		
	}
	
	// From a given index, find the first index of the value before
	// For example, in the string (4)+(2)*(5) and given the index 7,
	// this function would return 4, the opening parentheses of the 2
	private int getStartIndexOfValueBefore(int index, String string) {
		
		int count = 0;
		
		for(int i = index-1; i >= 0; i--) {
			
			char currentChar = string.charAt(i);
			
			if(currentChar == ')') count++;
			if(currentChar == '(') count--;
			
			if(count == 0) return i;
			
		}
		
		return INVALID_INPUT;
		
	}
	
	// From a given index, find the last index of the value after
	// For example, in the string (4)+(2)*(5) and given the index 7,
	// this function would return 10, the closing parentheses of the 5
	private int getEndIndexOfValueAfter(int index, String string) {

		int count = 0;
		
		for(int i = index+1; i < string.length(); i++) {
			
			char currentChar = string.charAt(i);
			
			if(currentChar == '(') count++;
			if(currentChar == ')') count--;
			
			if(count == 0) return i;
			
		}
		
		return INVALID_INPUT;
		
	}
	
	// Returns the double value of a string
	private double getValueOfString(String string) {
		
		// If we are given a single number, then parsing is easy
		if(string.indexOf('+') == -1 && string.indexOf('*') == -1 && string.indexOf('/') == -1) {
			
			// Remove any parentheses
			string = string.replace('(', '\0');
			string = string.replace(')', '\0');
			
			return Double.parseDouble(string);
			
		} else {
			
			// Otherwise, simplify the string and return its value
			return getValueOfString(simplify(string));
			
		}
		
	}
	
}
