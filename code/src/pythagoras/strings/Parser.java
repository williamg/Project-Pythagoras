package pythagoras.strings;

public abstract class Parser {
	
	public abstract String parseType(String string);
	
	// This function handles all parsing that applies to ALL types os input
	public static String generalParse(String string) {
		
		string = string.replace(" ", "");
		
		return string;
		
	}
	
}
