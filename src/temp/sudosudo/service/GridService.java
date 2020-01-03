package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import model.Constances;
import model.SudokuPuzzle;
import model.Unique;
import view.SudokuField;

public class GridService {

	private static final int ROWS = Constances.ROWS;
	private static final int COLUMNS = Constances.COLUMNS;
	private static final int CELLS = Constances.CELLS;

	public static int[][] generateSudoku(Constances.LEVEL level) {

		int[][] sudoku = generateFullGrid();
		int nonEmptyFields = level.levelCode;
		int emptyFields = ROWS * COLUMNS - nonEmptyFields;
		int row, col;

		int[][] toEmpty;
		
		/*
		 * searching for unique solution
		 */
		while (true) {			
			toEmpty = copySudoku(sudoku);
			
			/*
			 * removing certain numbers. zero == " "
			 */
			for (int c = 0; c != emptyFields; c++) {
				row = (int) (Math.random() * 9);
				col = (int) (Math.random() * 9);
				while (toEmpty[row][col] == 0) {
					row = (int) (Math.random() * 9);
					col = (int) (Math.random() * 9);
				}

				toEmpty[row][col] = 0;
			}
			if (hasUniqueSolution(toEmpty, level))
				break;
		}
		return toEmpty;
	}

	/*
	 * generates full 9x9 grid so that all rows, columns and cells fit well
	 */
	private static int[][] generateFullGrid() {
		long start = System.nanoTime();
		while (true) {
			int[][] sudoku = new int[ROWS][COLUMNS];

			int[] cell0 = new Unique().getUnique();
			int[] cell4 = new Unique().getUnique();
			int[] cell8 = new Unique().getUnique();

			int i0, i4, i8;
			i0 = i4 = i8 = 0;

			// diagonal cells
			for (int i = 0; i != ROWS; ++i) {
				for (int j = 0; j != COLUMNS; ++j) {
					if (whichCell(i, j) == 0) {
						sudoku[i][j] = cell0[i0++];
					} else if (whichCell(i, j) == 4) {
						sudoku[i][j] = cell4[i4++];
					} else if (whichCell(i, j) == 8) {
						sudoku[i][j] = cell8[i8++];
					}
				}
			}

			// remaining fields
			for (int i = 0; i != ROWS; ++i) {
				for (int j = 0; j != COLUMNS; ++j) {
					if (whichCell(i, j) != 0 && whichCell(i, j) != 4 && whichCell(i, j) != 8) {
						sudoku[i][j] = getFitValue(sudoku, i, j, whichCell(i, j));
					}
				}
			}

			if (howManyZeros(sudoku) == 0) {
				long time = System.nanoTime() - start;
				System.out.println("full grid exec time = " + time/1E9);
				return sudoku;
			}
		}
	}

	private static int whichCell(int row, int col) {
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
		return -1;
	}

	private static int getFitValue(int[][] sudoku, int row, int col, int cell) {

		boolean isRowOK = true;
		boolean isColumnOK = true;
		boolean isCellOK = true;

		int[] unique = new Unique().getUnique();

		for (int u : unique) {
			// fit to row
			for (int j = 0; j != COLUMNS; j++) {
				if (sudoku[row][j] == u) {
					isRowOK = false;
					break;
				}
			}
			// fit to column
			for (int i = 0; i != ROWS; i++) {
				if (sudoku[i][col] == u) {
					isColumnOK = false;
					break;
				}
			}
			// fit to cell
			for (int i = 0; i != ROWS; ++i) {
				for (int j = 0; j != COLUMNS; ++j) {
					if (whichCell(i, j) == cell) {
						if (sudoku[i][j] == u) {
							isCellOK = false;
							break;
						}
					}
				}
			}

			// check fitness
			if (isRowOK && isColumnOK && isCellOK) {
				return u;
			} else {
				isRowOK = isColumnOK = isCellOK = true;
			}

		}

		return 0; // returns 0 if is unable to fit any proper digit
	}

	public static int howManyZeros(int[][] sudoku) {
		int zeros = 0;
		for (int i = 0; i != ROWS; ++i) {
			for (int j = 0; j != COLUMNS; ++j) {
				if (sudoku[i][j] == 0)
					zeros++;
			}
		}
		return zeros;
	}

	public static boolean hasUniqueSolution(int[][] sudoku, Constances.LEVEL level) {

		// List<SudokuPuzzle> solutions = new ArrayList<>();
		HashSet<SudokuPuzzle> solutions = new HashSet<>();

		for (int attempt = 0; attempt != Constances.SEARCH_LIMIT; attempt++) {
			SudokuPuzzle newSolution = new SudokuPuzzle(solve(sudoku), level);
			solutions.add(newSolution);
			if( attempt >= 1 && solutions.size() != 1 ) {
				return false;
			}
		}
				
		return solutions.size() == 1;
	}

	/*
	 * returns solved sudoku (solves randomly, even if doesn't have unique solution)
	 */
	public static int[][] solve(int[][] sudoku) {
		long start = System.nanoTime();
		int[][] toSolve;
		while (true) {
			toSolve = copySudoku(sudoku);
			for (int i = 0; i != ROWS; ++i) {
				for (int j = 0; j != COLUMNS; ++j) {
					// fill empty fields
					if (toSolve[i][j] == 0) {
						toSolve[i][j] = getFitValue(toSolve, i, j, whichCell(i, j));
					}
				}
			}
			// return if is solvable
			if (howManyZeros(toSolve) == 0) {
				long end = System.nanoTime();
				System.out.println("solve() exec time = " + (end - start)/1E9);
				return toSolve;
			}
		}
		
	}
	
	private static int[][] solveB(int[][] sudoku) {
		int[][] toSolve;
		while (true) {
			toSolve = copySudoku(sudoku);

			int[] cell0 = new Unique().getUnique();
			int[] cell4 = new Unique().getUnique();
			int[] cell8 = new Unique().getUnique();

			int i0, i4, i8;
			i0 = i4 = i8 = 0;

			// diagonal cells
			for (int i = 0; i != ROWS; ++i) {
				for (int j = 0; j != COLUMNS; ++j) {
					if (whichCell(i, j) == 0) {
						toSolve[i][j] = cell0[i0++];
					} else if (whichCell(i, j) == 4) {
						toSolve[i][j] = cell4[i4++];
					} else if (whichCell(i, j) == 8) {
						toSolve[i][j] = cell8[i8++];
					}
				}
			}

			// remaining fields
			for (int i = 0; i != ROWS; ++i) {
				for (int j = 0; j != COLUMNS; ++j) {
					if (whichCell(i, j) != 0 && whichCell(i, j) != 4 && whichCell(i, j) != 8) {
						toSolve[i][j] = getFitValue(toSolve, i, j, whichCell(i, j));
					}
				}
			}

			if (howManyZeros(toSolve) == 0) {
				return toSolve;
			}
		}
	}

	public static int[][] copySudoku(int[][] sudoku) {
		return Arrays.stream(sudoku)
	             .map((int[] row) -> row.clone())
	             .toArray((int length) -> new int[length][]);
	}
	
	/*
	 * max fitness is 27
	 */
	public static int evaluateFitness(int[][] sudoku) {

		int f = 0;

		// rows
		for (int i = 0; i != ROWS; ++i) {
			int count = 0;
			for (int j = 0; j != COLUMNS - 1; ++j) {
				if( sudoku[i][j] == 0 ) {
					count++;
					break;
				}
				for (int k = j + 1; k != COLUMNS; ++k) {
					if ( sudoku[i][k] == 0 || sudoku[i][j] == sudoku[i][k]) {
						count++;
						break;
					}
				}
				if (count != 0)
					break;
			}
			if (count == 0)
				f++;
		}

		// columns
		for (int i = 0; i != COLUMNS; ++i) {
			int count = 0;
			for (int j = 0; j != ROWS - 1; ++j) {
				if( sudoku[j][i] == 0 ) {
					count++;
					break;
				}
				for (int k = j + 1; k != ROWS; ++k) {
					if (sudoku[k][i] == 0 || sudoku[j][i] == sudoku[k][i]) {
						count++;
						break;
					}
				}
				if (count != 0)
					break;
			}
			if (count == 0)
				f++;
		}

		// cells
		List<Integer> singleCell;
		for (int c = 0; c != CELLS; c++) {
			singleCell = new ArrayList<>();
			for (int i = 0; i != COLUMNS; ++i) {
				for (int j = 0; j != ROWS - 1; ++j) {
					if (whichCell(i, j) == c) {
						singleCell.add(sudoku[i][j]);
					}
				}
			}
			if( singleCell.contains(0) )
				continue;
			if (singleCell.stream().distinct().count() == singleCell.size())
				f++;
		}
		return f;
	}
	
	public static int[][] view2grid( SudokuField[][] view ){
		
		int[][] grid = new int[ROWS][COLUMNS];
		for (int i = 0; i != ROWS; ++i) {
			for (int j = 0; j != COLUMNS; ++j) {
				grid[i][j] = view[i][j].getNumber();
			}
		}
		return grid;
	}
}
