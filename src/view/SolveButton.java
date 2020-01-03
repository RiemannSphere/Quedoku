package view;

import javax.swing.JButton;

public class SolveButton extends JButton {
	private static final long serialVersionUID = 1L;

	public SolveButton() {
		setText(ConstV.SOLVE_BUTTON);
		setFont(ConstV.TEXT_FONT);
		setForeground(ConstV.TEXT);
		setBackground(ConstV.BACKGROUND);
		setRolloverEnabled(false);
		setFocusPainted(false);
	}
}
