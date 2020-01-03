package main;

import javax.swing.SwingUtilities;

import controller.SController;
import model.SudokuModel;
import view.Game;

public class Main {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SController controller = new SController();
				
				Game view = new Game(controller);
				view.setVisible(true);
				
				SudokuModel model = new SudokuModel(controller);
			}
		});
	}

}
