package pythagoras.strings.types;

import pythagoras.strings.MathString;
import pythagoras.strings.Parser;
import pythagoras.strings.Validator;

public class Expression extends MathString{
	
	private static int INVALID_INPUT = -1000000;
	private static enum OPERATION {ADDITION, MULT_AND_DIV, EXPONENTIAL};
	
	/* ========================================= *
	 * 		         String Parsing		 	     *
	 * ========================================= */ 
	private class ExpressionParser extends Parser {

		public String parseType(String string) {
			
			// uniformity is key!
			string = string.replace(",", "");
			string = string.replace("-", "+(-1)*");
			string = string.replace("[", "(");
			string = string.replace("]", ")");
			
			// The order of these function calls is important
			string = expandFactorials(string);
			string = addMultSymbols(string);
			string = addParentheses(string);
			
			return string;
			
		}
		
		// This function inserts the given string such that the first character of
		// the string is at the specified index of the resulting string:
		// "or" inserted into the string "Hello Wld" at index 7 results in "Hello World"
		
		// Another way to think about it is that it inserts the string before the character
		// at the specified index of the original string
		private String insert(String insert, int index, String string) {
			
			String front = string.substring(0, index);
			String end   = string.substring(index);
			
			string = front + insert + end;
			
			return string;
			
		}
		
		private String expandFactorials(String string) {
			
			int index = 0;
			
			while(string.indexOf('!') != -1) {
				
				index = string.indexOf('!', index);
				
				return "blah";
				
			}
			
			return string;
			
		}
		
		// Make implied multiplication explicit
		// eg: 2(7) -> 2*(7)
		private String addMultSymbols(String string) {
			
			for(int i = 1; i < string.length(); i++) {
				
				char currentChar = string.charAt(i);
				char prevChar    = string.charAt(i-1);
				
				if(currentChar == '(' && !isTerminator(prevChar) && prevChar != '(') {
					
					string = insert("*", i, string);
					i++;
					
				}
				
			}
			
			return string;
		}
		
		// This checks whether a given character is a "terminator"
		// A terminator is a cyborg with the intent of destroying mankind
		private  boolean isTerminator(char currentChar) {
			
			return (currentChar == '*' || currentChar == '/' || currentChar == '+' || currentChar == '^');
			
		}
		
		// We use parentheses to isolate individual values:
		// 2 + 4 * 8 -> (2)+(4)*(8)
		private String addParentheses(String string) {
			
			// If the string is only a number (not an expression),
			// then we return the string in the form <number>
			// I realize the lack of parentheses seems counter-intuitive, but this prevents double nesting
			// parentheses.
			if(string.indexOf('+') == -1 && string.indexOf('*') == -1 && string.indexOf('/') == -1 && string.indexOf('^') == -1) {
				
				// Get rid of parentheses
				string = string.replace('(', '\0');
				string = string.replace(')', '\0');
				
				return string;
			
			}
			
			// All expressions should be of the form " +<expression>+ "
			if(string.charAt(0) != '+') string = "+" + string;
			string = " " + string + "+ ";
			
			for(int i = 1; i < string.length(); i++) {
				
				char currentChar = string.charAt(i);
				
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
					
				} else if(isTerminator(currentChar)) {
					
					if(string.charAt(i-1) != ')') {
						string = insert(")", i, string);
						i++;
					}
					
					if(string.charAt(i+1) != '(') {
						string = insert("(", i+1, string);
						i++;
					}
					
				}

			}
			
			// Remove the padding and plus signs that we added at the beginning
			string = string.substring(3, string.length() - 3);
			
			return string;
			
		}
		
		// Given an index of an opening parentheses, find the matching closing parentheses
		private int getClosingParenthesesFromStart(int index, String string) {
			
			if(string.charAt(index) != '(') return INVALID_INPUT;
		
			for(int i = index, count = 0; i < string.length(); i++) {
				
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
	public class ExpressionValidator extends Validator {
		
		public boolean stringIsValidForType(String string) {
			
			if(!firstCharIsValid(string)) return false;
			if(!charactersAreValid(string)) return false;
			if(!signsAreValid(string)) return false;
			
			return true;
			
		}
		
		// The first character of an expression cannot be *, /, or )
		private boolean firstCharIsValid(String string) {
			
			String invalidChars = "*/)^!]";
			return (invalidChars.indexOf(string.charAt(0)) == -1);
			
		}
		
		private boolean charactersAreValid(String string) {
			
			String validChars = ".0123456789+-/*()^![]";
			
			for(int i = 0; i < string.length(); i++) {
				
				if(validChars.indexOf(string.charAt(i)) == -1) return false;
				
			}
			
			return true;
		}
		
		// Checks that weird things like 4+*5 don't occur
		private boolean signsAreValid(String string) {
			
			String validChars = ".0123456789+-([";
			
			for(int i = 0; i < string.length(); i++) {
				
				char currentChar = string.charAt(i);
				if(currentChar == '+' || currentChar == '-') {
					
					if(validChars.indexOf(string.charAt(i+1)) == -1) return false;
					
				}
				
			}
			
			return true;
		}
		
	}
	
	/* ========================================= *
	 * 		        Instance Methods		 	 *
	 * ========================================= */ 
	
	public ExpressionParser parser = new ExpressionParser();
	public ExpressionValidator validator = new ExpressionValidator();
	
	public Expression(String string) {
		
		super(MathString.STRING_TYPE.EXPRESSION, string);
		
	}
	
	public void manipulate() {
		
		setString(simplify(getString()));
		
	}
	
	public ExpressionParser getParser() {
		return parser;
	}
	
	public ExpressionValidator getValidator() {
		return validator;
	}
	
	private String simplify(String string) {
		
		// Recall the order of operations: PEMDA
		// Parentheses are handled automatically, and the rest are as follows:
		string = performOperation(OPERATION.EXPONENTIAL, string);
		string = performOperation(OPERATION.MULT_AND_DIV, string);
		string = performOperation(OPERATION.ADDITION, string);
		return string;
		
	}
	
	// Performs all instances of the given operation within the given string
	private String performOperation(OPERATION operation, String string) {
		
		while(operationIsPresent(operation, string)) {
			
			int index = getNextIndexForOperation(operation, string);
			
			int start = getStartIndexOfValueBefore(index, string);
			int end   = getEndIndexOfValueAfter(index, string);
			
			double valueOne = getValueOfString(string.substring(start, index));
			double valueTwo = getValueOfString(string.substring(index+1, end+1));
			
			if(valueOne == INVALID_INPUT || valueTwo == INVALID_INPUT) return "Invalid Input";
			
			double result = 0;
			
			if(operation == OPERATION.ADDITION)     result = valueOne + valueTwo;
			if(operation == OPERATION.EXPONENTIAL)  result = Math.pow(valueOne, valueTwo);
			if(operation == OPERATION.MULT_AND_DIV) {
				
				if(index == string.indexOf('*')) result = valueOne * valueTwo;
				if(index == string.indexOf('/')) result = valueOne / valueTwo;
			}
			
			// Replace the old operation with the result
			string = string.substring(0, start+1) + result + string.substring(end);
		}
		
		return string;
		
	}
	
	private boolean operationIsPresent(OPERATION operation, String string) {
		
		if(operation == OPERATION.ADDITION)     return (string.indexOf('+') != -1);
		if(operation == OPERATION.MULT_AND_DIV) return (string.indexOf('*') != -1 || string.indexOf('/') != -1);
		if(operation == OPERATION.EXPONENTIAL)  return (string.indexOf('^') != -1);
		
		return false;
		
	}
	
	private int getNextIndexForOperation(OPERATION operation, String string) {
		
		if(operation == OPERATION.ADDITION)     return string.indexOf('+');
		if(operation == OPERATION.EXPONENTIAL)  return string.indexOf('^');
		if(operation == OPERATION.MULT_AND_DIV) {
			
			// Since multiplication and division have to be handled concurrently, 
			// we return the closest index
			
			int nextMult = string.indexOf('*');
			int nextDiv = string.indexOf('/');
			
			if(nextDiv < 0) nextDiv = nextMult + 1;
			if(nextMult < 0) nextMult = nextDiv + 1;
			
			return (nextMult < nextDiv) ? nextMult : nextDiv; 
		}
		
		return INVALID_INPUT;
		
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
