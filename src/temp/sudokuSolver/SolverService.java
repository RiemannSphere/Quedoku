package sudokuSolver;

public class SolverService {

	public static SPuzzle solve(SPuzzle puzzle) throws SudokuUnsolvableException {
		SField[][] sudoku = puzzle.getSudoku();
		SPuzzle solved = new SPuzzle(sudoku);

		SParam param = new SParam(sudoku);

		// start from fist not-fixed field
		int row = param.getStartRow();
		int col = param.getStartCol();

		System.out.println("start : " + row + "," + col);
		System.out.println("end : " + param.getEndRow() + "," + param.getEndCol());
		
		while (true) {
			System.out.println("field " + row + "," + col);
			if (!sudoku[row][col].isFixed()) {
				try {
					sudoku[row][col].setValue(fitNextSmallestValue(row, col, sudoku));
				} catch (NoValueFitsException e) {
					if (row == param.getStartRow() && col == param.getStartCol()) {
						System.out.println("Went back to start and didn't fit any number. No more solutions possible.");
						return solved;
					}
					// if no value fits search for last not-fixed field and increment it if possible
					// reset all not-fixed values that were passed by
					for (int r = row; r >= param.getStartRow(); r--) {
						for (int c = (r == row ? col : 8); c >= (r == param.getStartRow() ? param.getStartCol()
								: 0); c--) {
							try {
								if (!sudoku[r][c].isFixed() && canFitNextValue(r, c, sudoku)) {
									row = r;
									col = c;
									continue;
								}
								sudoku[r][c].reset();
							} catch (FixedFieldException e1) {
							}
						}
					}
					if (param.getSolutions() == 0) {
						System.out.println("sudoku : \n" + new SPuzzle(sudoku));
						throw new SudokuUnsolvableException(
								"Went back to start and didn't fit any number. No solutions. #1");
					}else {
						return solved;
					}
				} catch (FixedFieldException | InvalidValueException e) {
					e.printStackTrace();
				}
			}
			// go to the next field

			if (row == param.getEndRow() && col == param.getEndCol()) {
				solved = new SPuzzle(sudoku);
				param.incrementSolutions();
				System.out.println("solution " + param.getSolutions() + " found : \n" + new SPuzzle(sudoku));
				// we are only interested in finding if solution is unique
				if (param.getSolutions() > 1)
					throw new SudokuUnsolvableException("There are more than 1 solution!");

				// We keep searching for more solutions.
				// Find last not-fixed value and increment it if possible
				// Reset all not-fixed values that were passed by
				for (int r = row; r >= param.getStartRow(); r--) {
					for (int c = (r == row ? col : 8); c >= (r == param.getStartRow() ? param.getStartCol() : 0); c--) {
						try {
							if (!sudoku[r][c].isFixed() && canFitNextValue(r, c, sudoku)) {
								row = r;
								col = c;
								continue;
							}
							sudoku[r][c].reset();
						} catch (FixedFieldException e1) {
						}
					}
				}
				if (param.getSolutions() == 0)
					throw new SudokuUnsolvableException("Went back to start and didn't fit any number. No solutions. #2");
				else
					return solved;
			}

			if (col == 8) {
				row++;
				col = 0;
				continue;
			}
			col++;
		}

	}

	/**
	 * @param sudoku
	 * @return Solved Sudoku puzzle
	 * @throws SudokuUnsolvableException
	 */
	public static SPuzzle solveRec(SPuzzle sudoku) throws SudokuUnsolvableException {
		return sol(0, 0, sudoku.getSudoku(), new SParam(sudoku.getSudoku()));
	}

	/**
	 * @param row
	 * @param col
	 * @param sudoku
	 * @param sudokuParam
	 * @return recurrently returns sudoku until solved or throws exception
	 * @throws SudokuUnsolvableException
	 */
	private static SPuzzle sol(int row, int col, SField[][] sudoku, SParam sudokuParam)
			throws SudokuUnsolvableException {
		if (!sudoku[row][col].isFixed()) {
			try {
				System.out.println("field " + row + "," + col);
				sudoku[row][col].setValue(fitNextSmallestValue(row, col, sudoku));
			} catch (NoValueFitsException e) {
				if (row == 0 && col == 0) {
					System.out.println("Went back to 0,0. No more solutions possible.");
					return new SPuzzle(sudoku);
				}
				// if no value fits search for last not-fixed value and increment it if possible
				// reset all not-fixed values that were passed by
				for (int r = row; r >= 0; r--) {
					for (int c = r == row ? col : 8; c >= 0; c--) {
						try {
							if (r == 0 && c == 0 && !canFitNextValue(r, c, sudoku))
								throw new SudokuUnsolvableException("Cannot fit any not-fixed value.");
							if (!sudoku[r][c].isFixed() && canFitNextValue(r, c, sudoku))
								return sol(r, c, sudoku, sudokuParam);
							sudoku[r][c].reset();
						} catch (FixedFieldException e1) {
						}
					}
				}
				throw new SudokuUnsolvableException("Cannot fit any not-fixed value.");
			} catch (FixedFieldException | InvalidValueException e) {
				e.printStackTrace();
			}
		}
		// go to the next field

		// reached very last not-fixed field
		if (row == sudokuParam.getEndRow() && col == sudokuParam.getEndCol()) {
			sudokuParam.incrementSolutions();
			System.out.println("solution " + sudokuParam.getSolutions() + " found : \n" + new SPuzzle(sudoku));
			// we are only interested in finding if solution is unique
			if (sudokuParam.getSolutions() > 1)
				throw new SudokuUnsolvableException("There are more than 1 solution!");

			// if no value fits search for last not-fixed value and increment it if possible
			// reset all not-fixed values that were passed by
			for (int r = row; r >= 0; r--) {
				for (int c = r == row ? col : 8; c >= 0; c--) {
					try {
						if (!sudoku[r][c].isFixed() && canFitNextValue(r, c, sudoku))
							return sol(r, c, sudoku, sudokuParam);
						sudoku[r][c].reset();
					} catch (FixedFieldException e1) {
					}
				}
			}
			throw new SudokuUnsolvableException("Cannot fit any not-fixed value.");

			// keep searching for other solution
			// step back to last not-fixed field to increment it
//			for (int r = row; r >= 0; r--) {
//				for (int c = r == row ? col : 8; c >= 0; c--) {
//					if (!sudoku[r][c].isFixed())
//						return sol(r, c, sudoku, sudokuParam);
//				}
//			}
//			throw new SudokuUnsolvableException("Wasn't able to find any not-fixed field.");
		}

		if (col == 8) {
			return sol(row + 1, 0, sudoku, sudokuParam);
		}
		return sol(row, col + 1, sudoku, sudokuParam);
	}

	/**
	 * @param row
	 * @param col
	 * @param sudoku
	 * @return true or false whether any value fits or not
	 */
	private static boolean canFitNextValue(int row, int col, SField[][] sudoku) {
		try {
			fitNextSmallestValue(row, col, sudoku);
		} catch (NoValueFitsException e) {
			return false;
		}
		return true;
	}

	/**
	 * @param row
	 * @param col
	 * @param sudoku
	 * @return tries to find next smallest value inside given field
	 * @throws NoValueFitsException
	 */
	private static int fitNextSmallestValue(int row, int col, SField[][] sudoku) throws NoValueFitsException {

		int current = sudoku[row][col].getValue();
		int currentCell;
		try {
			currentCell = SudokuService.whichCell(row, col);
		} catch (OutOfTheBoardException e1) {
			e1.printStackTrace();
			return -1;
		}

		// in case of field being empty (value = 0) it start with 1
		// checks numbers in range <current+1,9>
		nextValue: for (int value = current + 1; value <= 9; value++) {
			// does it fit to the row?
			for (int c = 0; c != 9; c++) {
				if (c != col && sudoku[row][c].getValue() != 0 && sudoku[row][c].getValue() == value) {
					continue nextValue;
				}
			}

			// does it fit to the column?
			for (int r = 0; r != 9; r++) {
				if (r != row && sudoku[r][col].getValue() != 0 && sudoku[r][col].getValue() == value) {
					continue nextValue;
				}
			}

			// does it fit to the 3x3 cell?
			for (int r = 0; r != 9; r++) {
				for (int c = 0; c != 9; c++) {
					try {
						if (currentCell == SudokuService.whichCell(r, c)) {
							if (row != r && col != c) {
								if (sudoku[r][c].getValue() != 0 && sudoku[r][c].getValue() == value) {
									continue nextValue;
								}
							}
						}
					} catch (OutOfTheBoardException e) {
						e.printStackTrace();
					}
				}
			}

			// if program reached here, then value fits perfectly
			return value;
		}

		// if program reached here, then neither value fits
		throw new NoValueFitsException(
				"Did not able to fit any larger value than current: " + sudoku[row][col].getValue());
	}
}
