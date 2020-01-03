package model;

import java.util.Arrays;

import service.GridService;

/*
 * 3x3 cells' IDs
 * |.0.|.1.|.2.|
 * |.3.|.4.|.5.|
 * |.6.|.7.|.8.|
 */

public class SudokuPuzzle {

	private final int ROWS = Constances.ROWS;
	private final int COLUMNS = Constances.COLUMNS;

	private int[][] sudoku;
	private Constances.LEVEL level;
	private int fitness;
	private int zeros;

	public SudokuPuzzle(Constances.LEVEL level) {
		this.level = level;
		sudoku = GridService.generateSudoku(this.level);
		fitness = GridService.evaluateFitness(sudoku);
		// zeros = GridService.howManyZeros(sudoku);
	}

	public SudokuPuzzle(int[][] sudoku, Constances.LEVEL level) {
		this.sudoku = GridService.copySudoku(sudoku);
		fitness = GridService.evaluateFitness(sudoku);
		// zeros = GridService.howManyZeros(sudoku);
	}

	public int getFitness() {
		return fitness;
	}

	public int[][] getSudoku() {
		return GridService.copySudoku(sudoku);
	}

	@Override
	public String toString() {
		String s = "\n";

		for (int i = 0; i != ROWS; ++i) {
			s += "|" + (sudoku[i][0] == 0 ? " " : sudoku[i][0]) + "|";
			for (int j = 1; j != COLUMNS; ++j) {
				s += (sudoku[i][j] == 0 ? " " : sudoku[i][j]) + "|";
			}
			s += "\n";
		}
		s += "fitness = " + this.fitness + "\n";
//		s += "non-empty = " + (81 - this.zeros) + "\n";
//		s += "level = " + this.level;

		return s;
	}

	@Override
	public boolean equals(Object o) {

		if (o == this) {
			return true;
		}

		if (!(o instanceof SudokuPuzzle))
			return false;

		SudokuPuzzle other = (SudokuPuzzle) o;

		for (int i = 0; i != ROWS; ++i) {
			for (int j = 0; j != COLUMNS; ++j) {
				// System.out.println("o["+i+"]["+j+"]="+other.getSudoku()[i][j]+",
				// s["+i+"]["+j+"]="+this.sudoku[i][j]);
				if (other.getSudoku()[i][j] != this.sudoku[i][j]) {
					System.out.println("HA!");
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public int hashCode() {
		return Arrays.deepHashCode(this.getSudoku());
	}

}
