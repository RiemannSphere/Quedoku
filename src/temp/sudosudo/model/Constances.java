package model;

import java.awt.Color;

public class Constances {
	public static final String APP_NAME = "Sudo Sudo";
	public static final String LEVEL1 = "EASY";
	public static final String LEVEL2 = "MEDIUM";
	public static final String LEVEL3 = "HARD";
	public static final String NEW_GAME = "New Game";
	public static final String CHECK = "Verify";
	public static final String CORRECT = "CORRECT!";
	public static final String INCORRECT = "Not yet...";
	
	/*
	 * how many times check if sudoku is unique
	 */
	public static final int SEARCH_LIMIT = 20;
	
	// 9 rows, 9 columns, 9 cells
	public static final int MAX_FITNESS = 27;
	public static final int ROWS = 9;
	public static final int COLUMNS = 9;
	public static final int CELLS = 9;
	public static enum LEVEL{
		EASY(50), // ~44 non-empty fields left // 1s
		MEDIUM(48), // ~32 non-empty fields left // 3s
		HARD(46), // ~24 non-empty fields left // 11s
		NEW_GAME(50);
		
		public final int levelCode;

	    private LEVEL(int levelCode) {
	        this.levelCode = levelCode;
	    }
	};
	
	public static Color BORDER = Color.BLACK;
	public static Color SEPARATOR = new Color(150, 150, 150);
	public static Color LIGHTED = new Color(150, 255, 150);
	public static Color RIGID = new Color(200, 200, 200);
	public static Color BACKGROUND = new Color(230, 255, 230);
	public static Color TOP = new Color(47, 115, 47);
	public static final Color LEVEL1_COLOR = new Color(86, 186, 86);
	public static final Color LEVEL2_COLOR = new Color(219, 222, 58);
	public static final Color LEVEL3_COLOR = new Color(209, 54, 54);
	public static final Color DEFAULT_LEVEL_COLOR = new Color(86, 186, 86);
	public static final Color CHECK_COLOR = new Color(219, 222, 58);
	
	public static Long EXEC_TIME_SOLVE;
	
}
