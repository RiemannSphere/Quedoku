package sudokuSolver;

import java.util.Arrays;

public class SPuzzle {
	private SField[][] sudoku;
	
	public SPuzzle(int emptyFields) {
		this.sudoku = GeneratorService.generateSudoku(emptyFields);
	}
	
	public SPuzzle( SField[][] sudoku ) {
		this.sudoku = SudokuService.copySudoku(sudoku);
	}
	
	public SField getField(int row, int col) {
		return sudoku[row][col];
	}
	
	public SField[][] getSudoku() {
		return SudokuService.copySudoku(sudoku);
	}
	
	/**
	 * @throws FixedFieldException
	 * @throws InvalidValueException 
	 */
	public void setField(int row, int col, int value) throws FixedFieldException, InvalidValueException {
		sudoku[row][col].setValue(value);
	}
	
	@Override
	public String toString() {
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
	
	@Override
	public boolean equals(Object o) {

		if (o == this) {
			return true;
		}

		if (!(o instanceof SPuzzle))
			return false;

		SPuzzle other = (SPuzzle) o;

		for (int i = 0; i != 9; ++i) {
			for (int j = 0; j != 9; ++j) {
				if (other.getSudoku()[i][j].getValue() != this.sudoku[i][j].getValue()) {
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
