package pythagoras.strings;

import pythagoras.strings.types.*;

// This is the superclass from which all types inherit.
public abstract class MathString {
	
	// All possible input types
	public static enum STRING_TYPE {
		
		EXPRESSION (Expression.class);
		
		private final Class<? extends MathString> typeClass;
		
		// Each type has its own class
		STRING_TYPE(Class<? extends MathString> paramClass) {
			typeClass = paramClass;
		}
		
		// returns an instance of the correct type
		public MathString getInstance(String string) { 
			
			// All subclass constructors should accept a String parameter
			try {
				return typeClass.getDeclaredConstructor(String.class).newInstance(string);
			} catch(Exception E) {
				System.out.println("ERROR: Invalid contructor.");
				return null;
			}
		
		}
		
	}
	
	private STRING_TYPE type;
	private String		string;

	public abstract Parser getParser();
	public abstract Validator getValidator();
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
	
	// Given a string, identify and return an instance of its appropriate type
	public static MathString getInstanceForString(String string) {
		
		// Perform general validation functions
		if(Validator.stringIsValid(string) == false) return null;
		
		// Perform general parsing functions
		string = Parser.generalParse(string);
		
		for(STRING_TYPE stringType : STRING_TYPE.values()) {
			
			MathString instance = stringType.getInstance(string);
			
			if(instance.getValidator().stringIsValidForType(string)) {
				
				instance.setString(instance.getParser().parseType(string));
				return instance;
				
			}
		
		}
		
		return null;
		
	}

}
