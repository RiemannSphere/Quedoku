package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
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
	private CheckButton checkButton;

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
		solveButton = new SolveButton(controller);
		checkButton = new CheckButton(controller);
		
		bottom.add(newGameButton.getButton());
		bottom.add(solveButton.getButton());
		bottom.add(checkButton.getButton());
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
		
		createMenu();
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - getHeight()) / 2);
	    setLocation(x, y);
		setVisible(true);
	}

	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("Menu");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        
        JMenuItem manual = new JMenuItem("Manual");
        manual.setMnemonic(KeyEvent.VK_E);
        manual.setToolTipText("Exit application");
        manual.addActionListener((event) -> showManual());
        
        JMenuItem exit = new JMenuItem("Exit");
        exit.setMnemonic(KeyEvent.VK_E);
        exit.setToolTipText("Exit application");
        exit.addActionListener((event) -> System.exit(0));

        fileMenu.add(manual);
        fileMenu.add(exit);
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
	}
	
	private void showManual() {
		JPanel panel = new JPanel(); 
		panel.setLayout(new BorderLayout());
		panel.setBackground(ConstV.BACKGROUND);
		panel.setPreferredSize(ConstV.FRAME_DIMENSION);
		
		JFrame manual = new JFrame();
		manual.setLayout(new FlowLayout(FlowLayout.CENTER));
		manual.setSize(ConstV.FRAME_DIMENSION);
		manual.setResizable(false);
		
		JLabel text = new JLabel();
		text.setForeground(ConstV.TEXT);
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setVerticalAlignment(JLabel.CENTER);
		text.setText("<html><font size=\"4\"><p style=\"text-align: center;\"><span style=\"font-family: &quot;Lucida Console&quot;, Monaco, monospace;\">Welcome to Quedoku - queue-based sudoku game!</span></p>\r\n" + 
				"<p><br></p>" +
				"<p style=\"text-align: center;\"><span style=\"font-family: 'Lucida Console', Monaco, monospace;\">RULES</span></p>\r\n" + 
				"<ul>\r\n" + 
				"  <li style=\"text-align: left;\"><span style=\"font-family: 'Lucida Console', Monaco, monospace;\">Type numbers from 1 to 9 into empty fields.</span></li>\r\n" + 
				"  <li style=\"text-align: left;\"><span style=\"font-family: 'Lucida Console', Monaco, monospace;\">Your task is filling entire grid so that:</span>\r\n" + 
				"    <ul>\r\n" + 
				"      <li style=\"text-align: left;\"><span style=\"font-family: 'Lucida Console', Monaco, monospace;\">numbers in every row don't repeat,</span></li>\r\n" + 
				"      <li style=\"text-align: left;\"><span style=\"font-family: 'Lucida Console', Monaco, monospace;\">numbers in every column don't repeat,</span></li>\r\n" + 
				"      <li style=\"text-align: left;\"><span style=\"font-family: 'Lucida Console', Monaco, monospace;\">numbers in every 3x3 cell don't repeat.</span></li>\r\n" + 
				"    </ul>\r\n" + 
				"  </li>\r\n" + 
				"</ul>\r\n" + 
				"<p style=\"text-align: center;\"><span style=\"font-family: 'Lucida Console', Monaco, monospace;\">HOW-TO</span></p>\r\n" + 
				"<ul>\r\n" + 
				"  <li style=\"text-align: left;\"><span style=\"font-family: 'Lucida Console', Monaco, monospace;\">Type a number.</span></li>\r\n" + 
				"  <li style=\"text-align: left;\"><span style=\"font-family: 'Lucida Console', Monaco, monospace;\">Hit ENTER or just click somewhere else to accept a change.</span></li>\r\n" + 
				"</ul>\r\n" + 
				"<p style=\"text-align: center;\"><span style=\"font-family: 'Lucida Console', Monaco, monospace;\">That's so simple!</span></p>\r\n" + 
				"<p style=\"text-align: right;\"><span style=\"font-family: 'Lucida Console', Monaco, monospace;\">Cheers,</span></p>\r\n" + 
				"<p style=\"text-align: right;\"><span style=\"font-family: 'Lucida Console', Monaco, monospace;\">Piotr Ko³odziejski</span></p>\r\n" + 
				"<p style=\"text-align: right;\"><span style=\"font-family: 'Lucida Console', Monaco, monospace;\">Warsaw, 2020</span></p></font></html>");
		
		panel.add(text, BorderLayout.CENTER);
		manual.getContentPane().add(panel);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - manual.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - manual.getHeight()) / 2);
	    manual.setLocation(x, y);
		
		manual.setVisible(true);
	}
	
}
