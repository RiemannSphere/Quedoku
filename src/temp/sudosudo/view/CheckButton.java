package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import model.Constances;
import service.GridService;

public class CheckButton extends JButton {
	public CheckButton(String text, SudokuFrame frame) {
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
				int[][] grid = GridService.view2grid(frame.getPuzzle().getSudoku());
				int fitness = GridService.evaluateFitness(grid);
				if( Constances.MAX_FITNESS == fitness ) {
					setText(Constances.CORRECT);
				}else {
					setText(Constances.INCORRECT);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setForeground(Constances.CHECK_COLOR);
				if( getText() != Constances.CORRECT )
					setText(text);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setForeground(Color.BLACK);
			}
		});
		this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constances.BORDER));
	}
}
