package view;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import model.Constances;
import model.SudokuPuzzle;

public class SudokuGame {

	private final int FILL = 1;

	private SudokuField[][] sudokuView;
	private SudokuPuzzle sudokuPuzzle;

	public SudokuGame(Constances.LEVEL level) {
		int[][] grid = new SudokuPuzzle(level).getSudoku();
		setSudoku(grid);
		this.sudokuPuzzle = new SudokuPuzzle(grid, level);
	}

	public SudokuField[][] getSudoku() {
		return sudokuView;
	}
	
	public SudokuPuzzle getSudokuPuzzle() {
		return sudokuPuzzle;
	}

	public void setSudoku(int[][] sudokuData) {
		String num;
		int s;
		int topW, leftW, bottomW, rightW;
		int topB, leftB, bottomB, rightB;
		Border border;
		Border white;
		Border black;
		sudokuView = new SudokuField[9][9];
		for (int i = 0; i != 9; i++) {
			for (int j = 0; j != 9; j++) {
				if ((s = sudokuData[i][j]) != 0) {
					num = Integer.toString(s);
				} else {
					num = " ";
				}

				rightW = bottomW = FILL;
				topB = topW = rightB = bottomB = leftB = leftW = 0;
				if (j == 0) {
					leftB = FILL;
					leftW = 0;
				}
				if (j == 8) {
					rightB = FILL;
					rightW = 0;
				}
				if (i == 0) {
					topB = FILL;
					topW = 0;
				}
				if (i == 8) {
					bottomB = FILL;
					bottomW = 0;
				}
				if (j == 2 || j == 5) {
					rightB = FILL;
					rightW = 0;
				}
				if (i == 2 || i == 5) {
					bottomB = FILL;
					bottomW = 0;
				}

				white = BorderFactory.createMatteBorder(topW, leftW, bottomW, rightW, Constances.SEPARATOR);
				black = BorderFactory.createMatteBorder(topB, leftB, bottomB, rightB, Constances.BORDER);
				border = new CompoundBorder(white, black);

				sudokuView[i][j] = new SudokuField(num, border);
			}
		}
	}
	
	

}
