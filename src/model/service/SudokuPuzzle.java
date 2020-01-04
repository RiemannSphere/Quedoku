package model.service;

import java.util.Arrays;

/*
 * 3x3 cells' IDs
 * |.0.|.1.|.2.|
 * |.3.|.4.|.5.|
 * |.6.|.7.|.8.|
 */

public class SudokuPuzzle {

	private int[][] sudoku;
	private int fitness;

	public SudokuPuzzle(int emptyFields) {
		sudoku = GridService.generateSudoku(emptyFields);
		fitness = GridService.evaluateFitness(sudoku);
	}

	public SudokuPuzzle(int[][] sudoku, int emptyFields) {
		this.sudoku = GridService.copySudoku(sudoku);
		fitness = GridService.evaluateFitness(sudoku);
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

		for (int i = 0; i != 9; ++i) {
			s += "|" + (sudoku[i][0] == 0 ? " " : sudoku[i][0]) + "|";
			for (int j = 1; j != 9; ++j) {
				s += (sudoku[i][j] == 0 ? " " : sudoku[i][j]) + "|";
			}
			s += "\n";
		}
		s += "fitness = " + this.fitness + "\n";

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

		for (int i = 0; i != 9; ++i) {
			for (int j = 0; j != 9; ++j) {
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
