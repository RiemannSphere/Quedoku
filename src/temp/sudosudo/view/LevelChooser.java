package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import model.Constances;

public class LevelChooser extends JButton {
	public LevelChooser(String text, SudokuFrame frame) {
		this.setText(text);
		this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
		this.setPreferredSize(new Dimension(40, 40));
		this.setBackground(Constances.BACKGROUND);
		this.setRolloverEnabled(false);
		this.setFocusPainted(false);
		this.removeMouseListener(this.getMouseListeners()[0]);
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Constances.LEVEL level;
				switch(((JButton)e.getSource()).getText()) {
				case Constances.LEVEL1 :
					level = Constances.LEVEL.EASY;
					break;
				case Constances.LEVEL2 :
					level = Constances.LEVEL.MEDIUM;
					break;
				case Constances.LEVEL3 : 
					level = Constances.LEVEL.HARD;
					break;
				default:
					level = Constances.LEVEL.NEW_GAME;
				}
				//frame.getPuzzle().setSudoku( new SudokuPuzzle(level).getSudoku() );
				
				frame.setCenter(new SudokuGame(level).getSudoku());
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				switch(getText()) {
				case Constances.LEVEL1:
					setForeground(Constances.LEVEL1_COLOR);
					break;
				case Constances.LEVEL2:
					setForeground(Constances.LEVEL2_COLOR);
					break;
				case Constances.LEVEL3:
					setForeground(Constances.LEVEL3_COLOR);
					break;
				default :
					setForeground(Constances.DEFAULT_LEVEL_COLOR);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				setForeground(Color.BLACK);
			}
		});
		this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constances.BORDER));
	}
}
