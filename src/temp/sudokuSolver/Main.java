package sudokuSolver;

public class Main {

	public static void main(String[] args) {
		SPuzzle p = new SPuzzle(30);
		System.out.println(p.toString());
		try {
			SolverService.solve(p).toString();
		} catch (SudokuUnsolvableException e) {
			System.out.println(e.getMessage());
		}
	}

}
