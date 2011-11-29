package pythagoras.types;

import pythagoras.types.expression.*;

// This is the superclass from which all types inherit.
public abstract class MathString {

	public static enum STRING_TYPE {INVALID, EXPRESSION}
	
	private STRING_TYPE type;
	private String		string;
	
	public abstract void manipulate();
	
	public MathString(STRING_TYPE initType, String initString) {
		
		type = initType;
		string = initString;
		
	}
	
	public STRING_TYPE getType() {
		return type;
	}
	
	public String getString() {
		return string;
	}
	
	public void setString(String parameterString) {
		string = parameterString;
	}
	
	/* ========================================= *
	 * 		     String Identification		 	 *
	 * ========================================= */ 
	 
	// For the time being, all types will have a subclass that validates strings
	// I may come up with a better, more uniform way to do this in the future, but I'll just leave it
	// like this for now.
	
	// Given a string, identify and return an instance of its appropriate type
	public static MathString getInstanceForString(String string) {
		
		if(Expression.ExpressionValidator.isValidExpression(string)) return new Expression(string);
		
		return null;
		
	}
	 
}
