package sudokuSolver;

public class SParam {
	private int solutions;
	// row and column of last not-fixed field of a sudoku (it's not always 8,8)
	private int endRow;
	private int endCol;
	// row and column of first not-fixed field of a sudoku (it's not always 0,0)
	private int startRow;
	private int startCol;
	
	public SParam(SField[][] sudoku) {
		this.solutions = 0;
		
		int[] end = SudokuService.findEndField(sudoku); 
		this.endRow = end[0];
		this.endCol = end[1];
		int[] start = SudokuService.findStartField(sudoku); 
		this.startRow = start[0];
		this.startCol = start[1];
	}
	
	public int getSolutions() {
		return solutions;
	}
	public void incrementSolutions() {
		this.solutions++;
	}
	public int getEndRow() {
		return endRow;
	}
	public int getEndCol() {
		return endCol;
	}
	public int getStartRow() {
		return startRow;
	}
	public int getStartCol() {
		return startCol;
	}
	
}
