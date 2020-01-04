package model.service;

public class SudokuUnsolvableException extends Exception {
	private static final long serialVersionUID = 1L;

	public SudokuUnsolvableException(String message) {
		super(message);
	}
}
