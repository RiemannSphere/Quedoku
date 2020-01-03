package model;

public class SModelField {

	private int row;
	private int col;
	private int value;
	private boolean fixed;

	/**
	 * Empty field with no value
	 * 
	 * @param row
	 * @param col
	 * @param fixed
	 */
	public SModelField(int row, int col, boolean fixed) {
		if (row >= 0 && row <= 8 && col >= 0 && col <= 8) {
			this.row = row;
			this.col = col;
		} else {
			System.err.println("Wrong coordinates. Row and column should be in range <0,8>");
		}
		this.value = 0;
		this.fixed = fixed;
	}

	/**
	 * Proper constructor with value setting
	 * 
	 * @param row
	 * @param col
	 * @param value
	 * @param fixed
	 */
	public SModelField(int row, int col, int value, boolean fixed) {
		this(row, col, fixed);
		if (value >= 0 && value <= 9)
			this.value = value;
		else
			System.err.println(
					this.getClass() + ": Wrong value. Sudoku fields contain numbers in range <1,9> and 0 for empty field");
	}

	public void setValue(int value) {
		if (value >= 0 && value <= 9 && !fixed)
			this.value = value;
		else
			System.err.println(
					this.getClass() + ": Wrong value. Sudoku fields contain numbers in range <1,9>  and 0 for empty field or field is fixed.");
	}

	public int getValue() {
		return value;
	}
	
	public boolean isFixed() {
		return fixed;
	}
}
