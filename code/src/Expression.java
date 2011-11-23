import java.util.*;

public class Expression extends MathPart {
	
	// This holds the terms of the expression:
	// The expression 3-1 has the terms 3 and -1
	ArrayList<Term> terms = new ArrayList<Term>(); 
	
	public Expression(String string) {
		
		setValue(string);
		terms = Parser.parseExpression(string);
		
	}
	
	public void simplify() {
		
		int sum = 0;
		
		for(int i = 0; i < terms.size(); i++) {
			terms.get(i).simplify();
			sum += Parser.parseValue(terms.get(i).getValue());
		}
		
		setValue(new Integer(sum).toString());
		
	}
	
}
