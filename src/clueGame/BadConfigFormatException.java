package clueGame;

public class BadConfigFormatException extends Exception{

	//Default Constructor
	public BadConfigFormatException() {
		super("Bad Config file format");
	}
	
	//Constructor with a more detailed message that will be passed in as the parameter
	public BadConfigFormatException(String a) {
		super("Bad Config file format, detail: " + a);
	}
}
