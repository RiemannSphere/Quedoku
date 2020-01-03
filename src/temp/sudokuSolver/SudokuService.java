package sudokuSolver;

public class SudokuService {
	public static SField[][] copySudoku(SField[][] sudoku) {
		SField[][] copied = new SField[9][9];

		for (int row = 0; row != 9; row++) {
			for (int col = 0; col != 9; col++) {
				if (sudoku[row][col].getValue() == 0)
					copied[row][col] = new SField(0, false);
				else
					copied[row][col] = new SField(sudoku[row][col].getValue(), sudoku[row][col].isFixed());
			}
		}
		return copied;
	}

	public static int whichCell(int row, int col) throws OutOfTheBoardException {
		if (row >= 0 && row <= 2) {
			if (col >= 0 && col <= 2)
				return 0;
			if (col >= 3 && col <= 5)
				return 1;
			if (col >= 6 && col <= 8)
				return 2;
		}
		if (row >= 3 && row <= 5) {
			if (col >= 0 && col <= 2)
				return 3;
			if (col >= 3 && col <= 5)
				return 4;
			if (col >= 6 && col <= 8)
				return 5;
		}
		if (row >= 6 && row <= 8) {
			if (col >= 0 && col <= 2)
				return 6;
			if (col >= 3 && col <= 5)
				return 7;
			if (col >= 6 && col <= 8)
				return 8;
		}
		throw new OutOfTheBoardException(
				"Row or column out of the sudoku board. Tried arguments: (" + row + "," + col + ")");
	}

	/**
	 * @param sudoku
	 * @return row and column of last not-fixed field of given sudoku
	 */
	public static int[] findEndField(SField[][] sudoku) {
		for (int row = 8; row >= 0; row--) {
			for (int col = 8; col >= 0; col--) {
				if (!sudoku[row][col].isFixed())
					return new int[] { row, col };
			}
		}
		return new int[] { 0, 0 };
	}
	/**
	 * 
	 * @param sudoku
	 * @return row and column of first not-fixed field of given sudoku
	 */
	public static int[] findStartField(SField[][] sudoku) {
		for (int row = 0; row != 9; row++) {
			for (int col = 0; col != 9; col++) {
				if (!sudoku[row][col].isFixed())
					return new int[] { row, col };
			}
		}
		return new int[] { 8, 8 };
	}
}
