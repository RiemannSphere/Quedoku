package view;

import controller.SController;
import controller.ViewElement;

public class SudokuView extends ViewElement {

	private SViewField[][] fields;

	public SudokuView(SController controller) {
		super(controller);
		fields = new SViewField[9][9];
		for (int r = 0; r != 9; r++) {
			for (int c = 0; c != 9; c++) {
				fields[r][c] = new SViewField(r, c, false,controller);
			}
		}
	}

	public SViewField[][] getFields() {
		return fields;
	}

	public SViewField getField(int row, int col) {
		return fields[row][col];
	}

	@Override
	public void updateSudokuPuzzle(int[][] sudoku, boolean[][] fixed) {
		for (int row = 0; row != 9; row++) {
			for (int col = 0; col != 9; col++) {
				fields[row][col].setValue(sudoku[row][col]);
				fields[row][col].setFixed(fixed[row][col]);
			}
		}
	}
}
