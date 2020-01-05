package controller;

import java.util.IllegalFormatException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ModelElement {

	private BlockingQueue<String> queue;

	public ModelElement(SController controller) {
		controller.addModel(this);
	}

	public final void executeCommand(String command) {
		String[] split = command.split(SController.SEPARATOR);
		// split[0] is a task, rest of split[] elements are parameters of a task
		switch (split.length > 0 ? split[0] : "") {
		case SController.FROM_V_CHANGE_FIELD:
			if (split.length == 5) {
				int row = Integer.valueOf(split[1]);
				int col = Integer.valueOf(split[2]);
				int oldValue = Integer.valueOf(split[3]);
				int newValue = Integer.valueOf(split[4]);
				try {
					if (isValidValue(newValue)) {
						changeField(row, col, newValue);
						queue.offer(String.format(SController.UPDATE_FIELD_FORMAT, SController.FROM_M_UPDATE_FIELD, row,
								col, newValue), SController.OFFER_TIMEOUT, TimeUnit.MILLISECONDS);
					} else {
						changeField(row, col, oldValue);
						queue.offer(String.format(SController.UPDATE_FIELD_FORMAT, SController.FROM_M_UPDATE_FIELD, row,
								col, oldValue), SController.OFFER_TIMEOUT, TimeUnit.MILLISECONDS);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IllegalFormatException e) {
					System.err.println("Command doesn't match a format.");
					e.printStackTrace();
				}
			} else {
				System.err.println("Wrong number of arguments. You've changed command format, haven't you?");
			}
			break;
		case SController.FROM_V_NEW_GAME:
			newGame();
			break;
		case SController.FROM_V_SOLVE:
			solveCurrent();
			break;
		case SController.FROM_V_CHECK:
			if (isSolved()) {
				try {
					queue.offer(String.format(SController.SUDOKU_SOLVED_FORMAT, SController.FROM_M_SUDOKU_SOLVED),
							SController.OFFER_TIMEOUT, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			break;
		}
	}

	private boolean isValidValue(int value) {
		if (value >= 0 && value <= 9) {
			return true;
		}
		return false;
	}

	public void setQueue(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	/**
	 * Method parsing sudoku 2D array into a string to send it to view layer It
	 * doesn't use separators because sudoku contains only 1 digit numbers.
	 * 
	 * It must include another 2D array informing if field is fixed or not.
	 * 
	 * @param sudoku
	 * @param fixed
	 */
	protected final void updateSudokuViewPuzzle(int[][] sudoku, boolean[][] fixed) {
		StringBuilder sudoku2string = new StringBuilder();
		StringBuilder fixed2string = new StringBuilder();
		for (int row = 0; row != 9; row++) {
			for (int col = 0; col != 9; col++) {
				sudoku2string.append(Integer.toString(sudoku[row][col]));
				fixed2string.append(Integer.toString(fixed[row][col] == false ? 0 : 1));
			}
		}
		try {
			queue.offer(
					String.format(SController.UPDATE_SUDOKU_FORMAT, SController.FROM_M_UPDATE_SUDOKU,
							sudoku2string.toString(), fixed2string.toString()),
					SController.OFFER_TIMEOUT, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IllegalFormatException e) {
			System.err.println("Command doesn't match a format.");
			e.printStackTrace();
		}
	}

	/**
	 * Override this method to change value of your sudoku field
	 * 
	 * @param row
	 * @param col
	 * @param value
	 */
	protected void changeField(int row, int col, int value) {

	};

	/**
	 * Override this method to get new sudoku puzzle
	 */
	protected void newGame() {

	};

	/**
	 * Override this method to get solved sudoku
	 */
	protected void solveCurrent() {

	}

	/**
	 * Override this method to settle if sudoku is solved
	 * 
	 * @return true or false
	 */
	protected boolean isSolved() {
		return true;
	}

}
