package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class ConstV {
	private static final double SCALE = 1.4;
	public static final String GAME_NAME = "Sudoku";
	public static final String NEW_GAME_BUTTON = "New Game";
	public static final String SOLVE_BUTTON = "Solve";
	public static final int FRAME_SIZE = (int)Math.round(450*SCALE);
	public static final int TOP_HEIGHT = (int)Math.round(30*SCALE);
	public static final int BOTTOM_HEIGHT = (int)Math.round(30*SCALE);
	public static final Dimension FRAME_DIMENSION = new Dimension(FRAME_SIZE, FRAME_SIZE);
	public static final Color BACKGROUND = new Color(46, 54, 66);// 34, 40, 49
	public static final Color FIELD = new Color(26, 29, 36);
	public static final Color NUMBER = new Color(239, 187, 53);
	public static final Color TEXT = new Color(250, 235, 215);
	public static final Font NUMBER_FONT = new Font("DejaVu Sans Mono", Font.BOLD, (int)Math.round(15*SCALE));
	public static final Font TEXT_FONT = new Font("DejaVu Sans Mono", Font.BOLD, (int)Math.round(16*SCALE));
	public static final int FIELDS_HGAP = (int)Math.round(1*SCALE);
	public static final int FIELDS_VGAP = (int)Math.round(1*SCALE);
	public static final int CELLS_HGAP = (int)Math.round(5*SCALE);
	public static final int CELLS_VGAP = (int)Math.round(5*SCALE);
}
