
// This is the super class that contains sub-classes Parser, Expressions, Terms. and Coefficients
// All of these classes share a "value" property that is their respective String representation
// Example values:
// Parser: 2x+8=6
// Expression: 2x+8
// Term: 2x
// Coefficient: 2

public abstract class MathPart {

	String value = new String();
	
	public void setValue(String string) {
		value = string;
	}
	
	public String getValue() {
		return value;
	}
	
	 
}
