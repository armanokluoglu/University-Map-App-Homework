package exceptions;
@SuppressWarnings("serial")
public class NodesNotNeighborsException extends Exception {

	public NodesNotNeighborsException() {
		super();
	}
	
	public NodesNotNeighborsException(String arg0) {
		super(arg0);
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}