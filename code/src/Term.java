public class Term extends MathPart {
	
	static String numerals = "0123456789";
	private int intValue = 0;
	
	public Term(String string) {
		parseTerm(string);
	}
	
	public int getIntValue() {
		return intValue;
	}
	
	private void parseTerm(String string) {

		setValue(string);
		
		int sign = (string.charAt(0) == '-') ? -1 : 1;
		intValue = sign * Integer.parseInt(string.substring(1));
		
		
	}
}
