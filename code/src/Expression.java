import java.util.*;

public class Expression extends MathPart {
	
	// This holds the terms of the expression:
	// The expression 3-1 has the terms 3 and -1
	ArrayList<Term> terms = new ArrayList<Term>(); 
	
	public Expression(String string) {
		
		setValue(string);
		parseTerms(getValue());
		
	}
	
	private void parseTerms(String string) {
		
		int lastStop = 0;
		
		// by appending a sign to the end of the string, we force it to catch the last term
		string = string.concat("+");
		
		// if the first character of the expression is a numeral, we need to append the appropriate sign
		if(string.charAt(0) != '+' && string.charAt(0) != '-') string = "+".concat(string);
		
		// we start at index 1 so that we don't catch the initial sign
		for(int i = 1; i < string.length(); i++) {
			
			// the terms are terminated by the signs of the proceeding terms
			// eg: 42+8-6 -> (+42)(+8)(-6)
			if(string.charAt(i) == '+' || string.charAt(i) == '-') {
				
				Term term = new Term(string.substring(lastStop, i));
				terms.add(term);
				lastStop = i;
			}
			
		}
		
	}
	
	public void simplify() {
		
		int sum = 0;
		
		for(int i = 0; i < terms.size(); i++) {
			sum += terms.get(i).getIntValue();
		}
		
		setValue(new Integer(sum).toString());
		
	}
	
}
