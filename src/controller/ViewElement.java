package controller;

import java.util.IllegalFormatException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ViewElement {

	private BlockingQueue<String> queue;

	public ViewElement(SController controller) {
		controller.addView(this);
	}

	/**
	 * Changes given field's value. If new value is invalid changes back to old
	 * value.
	 * 
	 * @param row
	 * @param col
	 * @param oldValue
	 * @param newValue
	 */
	protected final void changeSudokuField(int row, int col, int oldValue, int newValue) {
		try {
			queue.offer(String.format(SController.CHANGE_FIELD_FORMAT, SController.FROM_V_CHANGE_FIELD, row, col,
					oldValue, newValue), SController.OFFER_TIMEOUT, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IllegalFormatException e) {
			System.err.println("Command doesn't match a format.");
			e.printStackTrace();
		}
	}

	protected final void createNewGame() {

	}

	/**
	 * Internal method used to trigger certain actions
	 * 
	 * @param command
	 */
	public final void executeCommand(String command) {
		String[] split = command.split(SController.SEPARATOR);
		// split[0] is a task, rest of split[] elements are parameters of a task
		switch (split.length > 0 ? split[0] : "") {
		case SController.FROM_M_UPDATE_FIELD:
			if (split.length == 4) {
				int row = Integer.valueOf(split[1]);
				int col = Integer.valueOf(split[2]);
				int value = Integer.valueOf(split[3]);
				updateSudokuField(row, col, value);
			} else {
				System.err.println("Wrong number of arguments. You've changed command format, haven't you?");
			}
			break;
		case SController.FROM_M_UPDATE_SUDOKU:
			if (split.length == 3) {
				String sudokuString = split[1];
				String fixedString = split[2];
				String[] values = sudokuString.split("");
				String[] bools = fixedString.split("");
				int[][] sudoku = new int[9][9];
				boolean[][] fixed = new boolean[9][9];
				for (int row = 0; row != 9; row++) {
					for (int col = 0; col != 9; col++) {
						sudoku[row][col] = Integer.valueOf(values[9 * row + col]);
						fixed[row][col] = Integer.valueOf(bools[9 * row + col]) == 0 ? false : true;
					}
				}
				updateSudokuPuzzle(sudoku, fixed);
			} else {
				System.err.println("Wrong number of arguments. You've changed command format, haven't you?");
			}
			break;
		}
	}

	public void setQueue(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	/**
	 * Override this method to update view of sudoku field
	 * 
	 * @param row
	 * @param col
	 * @param value
	 */
	protected void updateSudokuField(int row, int col, int value) {

	};

	/**
	 * Override this method to update view of sudoku puzzle
	 * 
	 * @param sudoku
	 */
	protected void updateSudokuPuzzle(int[][] sudoku, boolean[][] fixed) {

	};

}
