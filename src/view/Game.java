package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.SController;

/*
 * Creating view requires:
 * 1. wrapping your view components in a ViewElement class
 * 2. adding all elements to controller's list
 * 3. firing an action (having arguments defined in controller's API) inside your component
 */

public class Game extends JFrame {
	private static final long serialVersionUID = 1L;
		
	private BorderLayout layout;
	private Container content;
	private JPanel center;
	private JPanel top;
	private JPanel bottom;
	private Cell[][] cells;
	private SudokuView fields;
	private JLabel gameLabel;
	private NewGameButton newGameButton;
	private SolveButton solveButton;

	{	
		layout = new BorderLayout();
		content = getContentPane();
		// center containing 9 cells containing 9 fields each
		center = new JPanel(new GridLayout(3, 3, ConstV.CELLS_HGAP,ConstV.CELLS_HGAP));
		top = new JPanel(new FlowLayout(FlowLayout.CENTER));
		bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cells = new Cell[3][3];
		for( int r=0; r!=3; r++ ) {
			for( int c=0; c!=3; c++ ) {
				cells[r][c] = new Cell(ConstV.FIELDS_HGAP, ConstV.FIELDS_VGAP);
			}	
		}
		gameLabel = new JLabel(ConstV.GAME_NAME);
		gameLabel.setForeground(ConstV.TEXT);
		gameLabel.setFont(ConstV.TEXT_FONT);
		
		solveButton = new SolveButton();
	}

	public Game(SController controller) {
		setSize(ConstV.FRAME_DIMENSION);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(layout);
		
		fields = new SudokuView(controller);
		
		for( int r=0; r!=3; r++ ) {
			for( int c=0; c!=3; c++ ) {
				for( int i = 3*r; i!=3*r+3; i++ ) {
					for( int j = 3*c; j!=3*c+3; j++ ) {
						cells[r][c].add(fields.getField(i, j).getTextField());
					}	
				}
			}	
		}
		for( int r=0; r!=3; r++ ) {
			for( int c=0; c!=3; c++ ) {
				center.add(cells[r][c]);
			}	
		}
		center.setBackground(ConstV.BACKGROUND);
		
		top.add(gameLabel);
		top.setBackground(ConstV.BACKGROUND);
		top.setSize(ConstV.FRAME_SIZE, ConstV.TOP_HEIGHT);
		
		newGameButton = new NewGameButton(controller);
		bottom.add(newGameButton.getButton());
		bottom.add(solveButton);
		bottom.setBackground(ConstV.BACKGROUND);
		bottom.setSize(ConstV.FRAME_SIZE, ConstV.BOTTOM_HEIGHT);
		
		content.add(center, BorderLayout.CENTER);
		content.add(top, BorderLayout.NORTH);
		content.add(bottom, BorderLayout.SOUTH);
		
		int topHeight = top.getHeight();
		int bottomHeight = bottom.getHeight();
		int centerHeight = ConstV.FRAME_SIZE - topHeight - bottomHeight;
		int centerWidth = centerHeight;
		int margin = (ConstV.FRAME_SIZE - centerWidth)/2;
		center.setBorder(new EmptyBorder(0, margin, 0, margin));
		
	}

}
