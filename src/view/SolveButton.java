package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import controller.SController;
import controller.ViewElement;

public class SolveButton extends ViewElement {

	private JButton button;

	public SolveButton(SController controller) {
		super(controller);
		button = new JButton();
		button.setText(ConstV.SOLVE_BUTTON);
		button.setFont(ConstV.TEXT_FONT);
		button.setForeground(ConstV.TEXT);
		button.setBackground(ConstV.BACKGROUND);
		button.setRolloverEnabled(false);
		button.setFocusPainted(false);
		button.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				solveCurrentSudoku();
			}
		} );
	}
	
	public JButton getButton() {
		return button;
	}
}
