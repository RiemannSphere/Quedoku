package sudokuSolver;

public class GeneratorService {

	public static SField[][] generateSudoku(int emptyFields) {
		// 1. generate 9x9 grid 
		// 2. remove fields while keeping sudoku solvable

		SField[][] grid = generateGrid();

		return grid;
	}

	public static SField[][] generateGrid() {
		// 1. fill diagonal cells randomly 
		// 2. start regular sudoku solving
		 
		SField[][] sudoku = new SField[9][9];

		int[] cell0 = new Unique().getUnique();
		int[] cell4 = new Unique().getUnique();
		int[] cell8 = new Unique().getUnique();

		int i0, i4, i8;
		i0 = i4 = i8 = 0;

		// filling up diagonal cells, they're counted like this:
		// 0 1 2
		// 3 4 5
		// 6 7 8
		for (int row = 0; row != 9; row++) {
			for (int col = 0; col != 9; col++) {
				sudoku[row][col] = new SField(0, false);
				try {
					if (SudokuService.whichCell(row, col) == 0)
						sudoku[row][col] = new SField(cell0[i0++], true);
					else if (SudokuService.whichCell(row, col) == 4)
						sudoku[row][col] = new SField(cell4[i4++], true);
					else if (SudokuService.whichCell(row, col) == 8)
						sudoku[row][col] = new SField(cell8[i8++], true);
				} catch (OutOfTheBoardException e) {
					e.printStackTrace();
				}
			}
		}
		
		return sudoku;
	}

}
