package model;

import controller.ModelElement;
import controller.SController;
import model.service.SudokuService;
import model.service.exc.FixedFieldException;
import model.service.exc.InvalidValueException;
import model.service.exc.SudokuUnsolvableException;

public class SudokuModel extends ModelElement {

	private SModelField[][] sudoku;
	private int emptyFields = 3;

	public SudokuModel(SController controller) {
		super(controller);
		sudoku = init();
	}

	private SModelField[][] init(){
		SModelField[][] sudoku = new SModelField[9][9];
		for( int row=0; row!=9; row++ ) {
			for( int col=0; col!=9; col++ ) {
				sudoku[row][col] = new SModelField(row,col,false);
			}	
		}
		return sudoku;
	}
	
	@Override
	public void changeField(int row, int col, int value) {
		try {
			this.sudoku[row][col].setValue(value);
		} catch (FixedFieldException | InvalidValueException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void newGame() {
		sudoku = SudokuService.generateSudoku(emptyFields);
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
	
	@Override
	public void solveCurrent() {
		try {
			sudoku = SudokuService.solveSudoku(sudoku);
		} catch (SudokuUnsolvableException e) {
			sudoku = SudokuService.generateSudoku(emptyFields);
			System.err.println("Sudoku unsolvable!");
		}
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
	
	@Override
	protected boolean isSolved() {
		return SudokuService.isSolved(sudoku);
	}

}
