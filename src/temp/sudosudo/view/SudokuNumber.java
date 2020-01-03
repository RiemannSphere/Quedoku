package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

import model.Constances;

public class SudokuNumber extends JButton {
	
	private String number;
	
	public SudokuNumber(String number) {
		this.number = number;
		this.setText(number);
		this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
		this.setPreferredSize(new Dimension(40, 40));
		this.setBackground(Constances.BACKGROUND);
		this.setRolloverEnabled(false);
		this.setFocusPainted(false);
		this.removeMouseListener(this.getMouseListeners()[0]);
		this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Constances.BORDER));
	}

	public void lightOn() {
		this.setBackground(Constances.LIGHTED);
	}

	public void lightOff() {
		this.setBackground(Constances.BACKGROUND);
	}
	
	public String getNumber() {
		return number;
	}
}
