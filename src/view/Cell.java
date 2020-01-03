package view;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class Cell extends JPanel {
	private static final long serialVersionUID = 1L;

	public Cell(int hgap, int vgap) {
		setLayout(new GridLayout(3, 3, hgap, vgap));
		setBackground(ConstV.BACKGROUND);
	}
	
}
