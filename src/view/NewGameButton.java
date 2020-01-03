package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import controller.SController;
import controller.ViewElement;

public class NewGameButton extends ViewElement {

	private JButton button;
	
	public NewGameButton(SController controller) {
		super(controller);
		button = new JButton();
		button.setText(ConstV.NEW_GAME_BUTTON);
		button.setFont(ConstV.TEXT_FONT);
		button.setForeground(ConstV.TEXT);
		button.setBackground(ConstV.BACKGROUND);
		button.setRolloverEnabled(false);
		button.setFocusPainted(false);
//		button.addMouseListener( new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//			}
//		} );
		button.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		} );
	}
	
	public JButton getButton() {
		return button;
	}
	
}
