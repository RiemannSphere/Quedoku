package sudokuSolver;

public class NoValueFitsException extends Exception {
	private static final long serialVersionUID = 1L;

	public NoValueFitsException(String message) {
		super(message);
	}

}
