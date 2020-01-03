package sudokuSolver;

import org.junit.jupiter.api.Test;

class TestSudoku {

	@Test
	void solverTest() {
		int[][] sudoku = new int[][] { { 0, 0, 0, 9, 5, 0, 6, 1, 0 }, { 0, 2, 0, 0, 0, 0, 7, 0, 4 },
				{ 6, 0, 1, 4, 0, 7, 0, 0, 0 }, { 5, 0, 0, 8, 1, 0, 0, 0, 0 }, { 8, 0, 4, 2, 0, 0, 0, 0, 7 },
				{ 0, 0, 9, 0, 0, 3, 4, 0, 1 }, { 0, 3, 0, 0, 9, 1, 0, 0, 5 }, { 0, 0, 0, 0, 0, 5, 8, 4, 0 },
				{ 7, 5, 0, 0, 0, 0, 0, 6, 0 } };

		// | | | |9|5| |6|1| |
		// | |2| | | | |7| |4|
		// |6| |1|4| |7| | | |
		// |5| | |8|1| | | | |
		// |8| |4|2| | | | |7|
		// | | |9| | |3|4| |1|
		// | |3| | |9|1| | |5|
		// | | | | | |5|8|4| |
		// |7|5| | | | | |6| |

		// solved is:
		// 3 4 7 | 9 5 2 | 6 1 8
		// 9 2 5 | 1 8 6 | 7 3 4
		// 6 8 1 | 4 3 7 | 5 9 2
		// ---------------------
		// 5 7 3 | 8 1 4 | 9 2 6
		// 8 1 4 | 2 6 9 | 3 5 7
		// 2 6 9 | 5 7 3 | 4 8 1
		// ---------------------
		// 4 3 8 | 6 9 1 | 2 7 5
		// 1 9 6 | 7 2 5 | 8 4 3
		// 7 5 2 | 3 4 8 | 1 6 9

		SField[][] sfields = new SField[9][9];
		for (int row = 0; row != 9; ++row) {
			for (int col = 0; col != 9; ++col) {
				if (sudoku[row][col] != 0)
					sfields[row][col] = new SField(sudoku[row][col], true);
				else
					sfields[row][col] = new SField(0, false);
			}
		}
		SPuzzle puzzle = new SPuzzle(sfields);
		System.out.println(new SPuzzle(sfields).toString());
		try {
			//System.out.println(SolverService.solve(puzzle).toString());
			SolverService.solve(puzzle).toString();
		} catch (SudokuUnsolvableException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	void generatorTest() {
		System.out.println(new SPuzzle(GeneratorService.generateSudoku(0)).toString());
	}

}
