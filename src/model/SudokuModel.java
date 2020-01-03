package model;

import controller.ModelElement;
import controller.SController;

public class SudokuModel extends ModelElement {

	private SModelField[][] sudoku;

	public SudokuModel(SController controller) {
		super(controller);
		newGame();
	}

	@Override
	public void changeField(int row, int col, int value) {
		this.sudoku[row][col].setValue(value);
	}

	@Override
	public void newGame() {
		sudoku = SudokuService.generateSudoku();
		int[][] values = new int[9][9];
		boolean[][] fixed = new boolean[9][9];
		for( int row=0; row!=9; row++ ) {
			for( int col=0; col!=9; col++ ) {
				values[row][col] = sudoku[row][col].getValue();
				fixed[row][col] = sudoku[row][col].isFixed();
			}	
		}
		updateSudokuViewPuzzle(values,fixed);
	}

}
