package model.service;

import model.SModelField;

public class SudokuService {

	public static SModelField[][] generateSudoku(int emptyFields) {
//		int[][] sudoku = new int[][] { { 0, 0, 0, 9, 5, 0, 6, 1, 0 }, { 0, 2, 0, 0, 0, 0, 7, 0, 4 },
//				{ 6, 0, 1, 4, 0, 7, 0, 0, 0 }, { 5, 0, 0, 8, 1, 0, 0, 0, 0 }, { 8, 0, 4, 2, 0, 0, 0, 0, 7 },
//				{ 0, 0, 9, 0, 0, 3, 4, 0, 1 }, { 0, 3, 0, 0, 9, 1, 0, 0, 5 }, { 0, 0, 0, 0, 0, 5, 8, 4, 0 },
//				{ 7, 5, 0, 0, 0, 0, 0, 6, 0 } };
		int[][] sudoku = GridService.generateSudoku(emptyFields);
		SModelField[][] sudokuModel = new SModelField[9][9];
		for (int row = 0; row != 9; row++) {
			for (int col = 0; col != 9; col++) {
				sudokuModel[row][col] = sudoku[row][col] == 0 ? new SModelField(row, col, false)
						: new SModelField(row, col, sudoku[row][col], true);
			}
		}
		return sudokuModel;
	}

	/**
	 * Main method solving sudoku puzzle. It solves generated sudoku without
	 * considering user's input in not-fixed fields.
	 * 
	 * @param sudoku
	 * @return
	 * @throws SudokuUnsolvableException
	 */
	public static SModelField[][] solveSudoku(SModelField[][] sudoku) throws SudokuUnsolvableException {
		for (int r = 0; r != 9; r++) {
			for (int c = 0; c != 9; c++) {
				if (!sudoku[r][c].isFixed()) {
					try {
						sudoku[r][c].reset();
					} catch (FixedFieldException e) {
						e.printStackTrace();
					}
				}
			}
		}
		SParam param = new SParam(sudoku);

		// start from fist not-fixed field
		int row = param.getStartRow();
		int col = param.getStartCol();

		search: while (true) {
			if (!sudoku[row][col].isFixed()) {
				try {
					sudoku[row][col].setValue(fitNextSmallestValue(row, col, sudoku));
				} catch (NoValueFitsException e) {
//					if (row == param.getStartRow() && col == param.getStartCol()) {
//						System.out.println("Went back to start and didn't fit any number. No more solutions possible.");
//						return sudoku;
//					}
					// if no value fits search for last not-fixed field and increment it if possible
					// reset all not-fixed values that were passed by
					for (int r = row; r >= param.getStartRow(); r--) {
						for (int c = (r == row ? col : 8); c >= (r == param.getStartRow() ? param.getStartCol()
								: 0); c--) {
							try {
								if (!sudoku[r][c].isFixed() && canFitNextValue(r, c, sudoku)) {
									row = r;
									col = c;
									continue search;
								}
								sudoku[r][c].reset();
							} catch (FixedFieldException e1) {
							}
							if (r == 0 && c == 0) {
								System.out.println("sudoku : \n" + sudokuToString(sudoku));
								throw new SudokuUnsolvableException(
										"Went back to start and didn't fit any number. No solutions. #1");
							}
						}
					}
				} catch (FixedFieldException | InvalidValueException e) {
					e.printStackTrace();
				}
			}
			// go to the next field

			if (row == param.getEndRow() && col == param.getEndCol()) {
				System.out.println("solution " + param.getSolutions() + " found : \n" + sudokuToString(sudoku));
				return sudoku;
				/*
				 * solved = copySudoku(sudoku); // save solution param.incrementSolutions();
				 * System.out.println("solution " + param.getSolutions() + " found : \n" +
				 * sudokuToString(sudoku)); // we are only interested in finding if solution is
				 * unique if (param.getSolutions() > 1) throw new
				 * SudokuUnsolvableException("There are more than 1 solution!");
				 * 
				 * // We keep searching for more solutions. // Find last not-fixed value and
				 * increment it if possible // Reset all not-fixed values that were passed by
				 * for (int r = row; r >= param.getStartRow(); r--) { for (int c = (r == row ?
				 * col : 8); c >= (r == param.getStartRow() ? param.getStartCol() : 0); c--) {
				 * try { if (!sudoku[r][c].isFixed() && canFitNextValue(r, c, sudoku)) { row =
				 * r; col = c; continue; } sudoku[r][c].reset(); } catch (FixedFieldException
				 * e1) { } } } if (param.getSolutions() == 0) throw new
				 * SudokuUnsolvableException(
				 * "Went back to start and didn't fit any number. No solutions. #2"); else
				 * return solved;
				 */
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
	 * @param row
	 * @param col
	 * @param sudoku
	 * @return true or false whether any value fits or not
	 */
	private static boolean canFitNextValue(int row, int col, SModelField[][] sudoku) {
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
	private static int fitNextSmallestValue(int row, int col, SModelField[][] sudoku) throws NoValueFitsException {

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
	public static int[] findEndField(SModelField[][] sudoku) {
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
	public static int[] findStartField(SModelField[][] sudoku) {
		for (int row = 0; row != 9; row++) {
			for (int col = 0; col != 9; col++) {
				if (!sudoku[row][col].isFixed())
					return new int[] { row, col };
			}
		}
		return new int[] { 8, 8 };
	}

	public static String sudokuToString(SModelField[][] sudoku) {
		String s = "\n";

		for (int row = 0; row != 9; row++) {
			s += "|" + (sudoku[row][0].getValue() == 0 ? " " : sudoku[row][0].getValue()) + "|";
			for (int col = 1; col != 9; col++) {
				s += (sudoku[row][col].getValue() == 0 ? " " : sudoku[row][col].getValue()) + "|";
			}
			s += "\n";
		}
		return s;
	}

	public static SModelField[][] copySudoku(SModelField[][] sudoku) {
		SModelField[][] copy = new SModelField[9][9];
		for (int row = 0; row != 9; row++) {
			for (int col = 0; col != 9; col++) {
				copy[row][col] = new SModelField(row, col, sudoku[row][col].getValue(), sudoku[row][col].isFixed());
			}
		}
		return copy;
	}

}
