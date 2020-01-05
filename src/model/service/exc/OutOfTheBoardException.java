package model.service.exc;

public class OutOfTheBoardException extends Exception {
	private static final long serialVersionUID = 1L;

	public OutOfTheBoardException( String message ) {
		super(message);
	}
	
}
