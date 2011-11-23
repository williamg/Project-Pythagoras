public class Term extends MathPart {
	
	static String numerals = "0123456789";
	
	public Term(String string) {
		setValue(Parser.parseTerm(string));
	}
	
	// This function handles multiplication and division in the term
	public void simplify() {
		
		String term = getValue();
		
		for(int currentIndex = 0; currentIndex < term.length(); currentIndex++) {
		
			char currentChar = term.charAt(currentIndex);
			
			if(currentChar == '*' || currentChar == '/') {
				
				int numOne = getValueBefore(currentIndex);
				int numTwo = getValueAfter(currentIndex);
				
				int result = 0;
				if(currentChar == '*') result = numOne * numTwo;
				if(currentChar == '/') result = numOne / numTwo; 
				
				// What we do next is replace the operation with the result:
				// (2)*(8)/(4) => (16)/(4) => (4)
				
				String searchString = new String("(" + numOne + ")" + currentChar + "(" + numTwo + ")");
				int split = term.indexOf(searchString) + searchString.length();
				String restOfTerm = term.substring(split);
				term = "(" + result + ")".concat(restOfTerm);
				
				setValue(term);
				currentIndex = 0;
				
			}
		
		}
		
	}
	
	// These next two functions assume an index of either an * or a /
	// They then find the previous and next value respectively
	// Currently there is no support for parenthetical expression:
	// (4)*(2+4) will most likely result in an error
	private int getValueBefore(int index) {
		
		String term = getValue();
		int start = 0, finish = index-1;
		
		for(int currentIndex = index; currentIndex >= 0; currentIndex--) {
			
			char currentChar = term.charAt(currentIndex);
			
			if(currentChar == '(') {
				start = currentIndex;
				break;
			}
			
		}
		
		String value = term.substring(start+1, finish);
		return Integer.parseInt(value);
		
	}
	
	private int getValueAfter(int index) {
		
		String term = getValue();
		int start = index+1, finish = 0;
		
		for(int currentIndex = index; currentIndex < term.length(); currentIndex++) {
			
			char currentChar = term.charAt(currentIndex);
			
			if(currentChar == ')') {
				finish = currentIndex;
				break;
			}
			
		}
		
		String value = term.substring(start+1, finish);
		return Integer.parseInt(value);
		
	}
	
}
