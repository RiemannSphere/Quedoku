package sudokuSolver;

public class SField {
	private int value;
	private final boolean fixed;
	
	public SField(int value, boolean fixed) {
		this.value = value;
		this.fixed = fixed;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) throws FixedFieldException, InvalidValueException {
		if( !this.fixed )
			if( value < 1 || value > 9 )
				throw new InvalidValueException("Value must be in range <1,9>. Tried to insert: " + value);
			else
				this.value = value;
		else
			throw new FixedFieldException("Cannot change fixed sudoku field. Tried to insert: " + value);
	}
	
	public void reset() throws FixedFieldException {
		if( !this.fixed )
			this.value = 0;
		else
			throw new FixedFieldException("Cannot change fixed sudoku field. Tried to reset.");
	}

	public boolean isFixed() {
		return fixed;
	}
	
}
