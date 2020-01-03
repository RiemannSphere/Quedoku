package model;

public class SudokuService {

	public static SModelField[][] generateSudoku() {
		int[][] sudoku = new int[][] { { 0, 0, 0, 9, 5, 0, 6, 1, 0 }, { 0, 2, 0, 0, 0, 0, 7, 0, 4 },
				{ 6, 0, 1, 4, 0, 7, 0, 0, 0 }, { 5, 0, 0, 8, 1, 0, 0, 0, 0 }, { 8, 0, 4, 2, 0, 0, 0, 0, 7 },
				{ 0, 0, 9, 0, 0, 3, 4, 0, 1 }, { 0, 3, 0, 0, 9, 1, 0, 0, 5 }, { 0, 0, 0, 0, 0, 5, 8, 4, 0 },
				{ 7, 5, 0, 0, 0, 0, 0, 6, 0 } };

		SModelField[][] sudokuModel = new SModelField[9][9];
		for (int row = 0; row != 9; row++) {
			for (int col = 0; col != 9; col++) {
				sudokuModel[row][col] = sudoku[row][col] == 0 ? new SModelField(row, col, false)
						: new SModelField(row, col, sudoku[row][col], true);
			}
		}
		return sudokuModel;
	}

}
