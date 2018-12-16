package exceptions;
@SuppressWarnings("serial")
public class InvalidOperationNumberException extends Exception {

	public InvalidOperationNumberException() {
		super();
	}
	
	public InvalidOperationNumberException(String arg0) {
		super(arg0);
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}