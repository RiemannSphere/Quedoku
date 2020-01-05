package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import controller.SController;
import controller.ViewElement;

public class CheckButton extends ViewElement {

	private JButton button;

	public CheckButton(SController controller) {
		super(controller);
		button = new JButton();
		button.setText(ConstV.CHECK_BUTTON);
		button.setFont(ConstV.TEXT_FONT);
		button.setForeground(ConstV.TEXT);
		button.setBackground(ConstV.BACKGROUND);
		button.setRolloverEnabled(false);
		button.setFocusPainted(false);
		button.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkCurrentSudoku();
			}
		} );
	}
	
	public JButton getButton() {
		return button;
	}
	
	@Override
	protected void sudokuBeenSolved() {
		JFrame currentFrame = (JFrame) SwingUtilities.getRoot(button);
		JOptionPane.showMessageDialog(currentFrame, "You did it! Sudoku has been solved.", ConstV.GAME_NAME,
				JOptionPane.PLAIN_MESSAGE);
	}
}
