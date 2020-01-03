package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import model.Constances;

/*
 * TO DO:
 * 1. check if win -> button CHECK
 * 2. immediate solve -> button SOLVE
 * 3. immediate level choose -> second tread preparing levels for future usage 
 * 	(one easy, one medium, one hard)
 * 4. dropdown menu -> info about author, Sudoku rules, new game, exit, my results
 * 5. timer -> new thread
 * 6. results (how many easy/medium/hard levels, times of solving) 
 */

public class SudokuFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private final Integer FRAME_SIZE = 450;
	private final Integer RIGHT_WIDTH = 80;
	private final Integer BOTTOM_HEIGHT = 50;
	private final Integer PADDING = 30;
	private final Dimension FRAME_DIMENSION = new Dimension(FRAME_SIZE, FRAME_SIZE);

	private BorderLayout layout;
	private Container content;
	private JPanel center;
	private JPanel bottom;
	private JPanel right;
	private JPanel top;

	private JLabel lblWelcome;

	private SudokuField fieldClicked;
	private SudokuField fieldLighted;

	private SudokuNumber numberClicked;
	private SudokuNumber numberLighted;

	// de facto place where state of game is stored
	private SudokuGame puzzle;
	{
		layout = new BorderLayout();
		content = getContentPane();
		center = new JPanel(new GridLayout(9, 9, 0, 0));
		bottom = new JPanel(new GridLayout(1, 9, 0, 0));
		right = new JPanel(new GridLayout(3, 1, 0, 20));
		top = new JPanel(new GridLayout(1, 1, 0, 0));
		lblWelcome = new JLabel(Constances.APP_NAME, SwingConstants.CENTER);
		puzzle = new SudokuGame(Constances.LEVEL.NEW_GAME);
		numberClicked = null; // nothing been clicked yet
		fieldClicked = null; // nothing been clicked yet
		numberClicked = null; // nothing been clicked yet
		numberLighted = null; // nothing been clicked yet
	}

	public SudokuFrame() {
		setSize(FRAME_DIMENSION);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(layout);

		center.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
		center.setBackground(Constances.BACKGROUND);

		bottom.setBackground(Constances.BACKGROUND);
		bottom.setPreferredSize(new Dimension(FRAME_SIZE, BOTTOM_HEIGHT));

		right.setBackground(Constances.BACKGROUND);
		right.setBorder(BorderFactory.createEmptyBorder(PADDING, 0, PADDING, 0));
		right.setPreferredSize(new Dimension(RIGHT_WIDTH, FRAME_SIZE));

		top.setBackground(Constances.TOP);
		top.setPreferredSize(new Dimension(FRAME_SIZE, 30));

		setCenter(puzzle.getSudoku());

		for (int i = 0; i != 9; i++) {
			SudokuNumber number = new SudokuNumber(Integer.toString(i + 1));
			number.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					/*
					 * turn light on or off
					 */
					numberClicked = (SudokuNumber) e.getSource();

					if (numberClicked.equals(numberLighted)) {
						numberClicked.lightOff();
						numberLighted = null;
						numberClicked = null;
					} else {
						if (numberLighted != null) {
							numberLighted.lightOff();
						}
						numberClicked.lightOn();
						numberLighted = numberClicked;
					}
					/*
					 * in case of match, change field's number
					 */
					if (fieldLighted != null && numberLighted != null) {
						fieldLighted.setNumber(numberLighted.getNumber());
						fieldLighted.lightOff();
						fieldLighted = null;
						numberLighted.lightOff();
						numberLighted = null;
					}
				}

			});
			bottom.add(number);
		}

		right.add(new LevelChooser(Constances.NEW_GAME,this));
//		right.add(new LevelChooser(Constances.LEVEL2,this));
//		right.add(new LevelChooser(Constances.LEVEL3,this));
		right.add(new CheckButton(Constances.CHECK,this));
		

		lblWelcome.setForeground(Constances.BACKGROUND);
		top.add(lblWelcome);

		content.setBackground(Constances.BACKGROUND);
		content.add(top, BorderLayout.NORTH);
		content.add(center, BorderLayout.CENTER);
		content.add(bottom, BorderLayout.SOUTH);
		content.add(right, BorderLayout.EAST);

		setVisible(true); // shows app
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(SudokuFrame::new);
	}
	
	public void setCenter(SudokuField[][] newSudokuData) {
		center.removeAll();
		content.remove(center);
		for (int i = 0; i != 9; i++) {
			for (int j = 0; j != 9; j++) {
				if (!newSudokuData[i][j].isRigid())
					newSudokuData[i][j].addMouseListener(new MouseAdapter() {

						@Override
						public void mouseClicked(MouseEvent e) {
							/*
							 * turn light on or off
							 */
							fieldClicked = (SudokuField) e.getSource();

							if (fieldClicked.equals(fieldLighted)) {
								fieldClicked.lightOff();
								fieldLighted = null;
								fieldClicked = null;
							} else {
								if (fieldLighted != null) {
									fieldLighted.lightOff();
								}
								fieldClicked.lightOn();
								fieldLighted = fieldClicked;
							}

							/*
							 * in case of match, change field's number
							 */
							if (fieldLighted != null && numberLighted != null) {
								fieldLighted.setNumber(numberLighted.getNumber());
								fieldLighted.lightOff();
								fieldLighted = null;
								numberLighted.lightOff();
								numberLighted = null;
							}
						}
					});
				center.add(newSudokuData[i][j]);
				content.add(center, BorderLayout.CENTER);
				setVisible(true);
			}
		}
	}
	
	public SudokuGame getPuzzle() {
		return puzzle;
	}

}
