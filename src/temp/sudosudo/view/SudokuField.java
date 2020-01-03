package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.border.Border;

import model.Constances;

public class SudokuField extends JButton {
	
	private final boolean isRigid;
		
	public SudokuField(String digit, Border border) {
		if( digit.equals(" ") )
			isRigid = false;
		else
			isRigid = true;
		this.setText(digit);
		this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
		this.setPreferredSize(new Dimension(40, 40));
		this.setBackground( isRigid ? Constances.RIGID : Constances.BACKGROUND );
		this.setRolloverEnabled(false);
		this.setFocusPainted(false);
		this.removeMouseListener(this.getMouseListeners()[0]);
		this.setBorder(border);
	}

	public void lightOn() {
		this.setBackground(Constances.LIGHTED);
	}

	public void lightOff() {
		this.setBackground(Constances.BACKGROUND);
	}

	public void setNumber(String newDigit) {
		this.setText(newDigit);
	}
	
	public int getNumber() {
		if(this.getText() == " ") {
			return 0;
		}
		return Integer.parseInt(this.getText());
	}

	public boolean isRigid() {
		return isRigid;
	}
	
}
