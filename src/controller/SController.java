package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SController {

	// All commands available as an events invoking certain methods. String values are arbitrary. 
	public static final String FROM_V_NEW_GAME = "createNewSudoku";
	public static final String FROM_V_SOLVE = "solveSudoku";
	public static final String FROM_V_CHANGE_FIELD = "changeModelFieldValue";
	public static final String EXIT_GAME = "exitGame";
	public static final String FROM_M_UPDATE_FIELD = "updateViewFieldValue";
	public static final String FROM_M_UPDATE_SUDOKU = "updateSudokuView";
	

	public static final String SEPARATOR = ";";
	public static final String CHANGE_FIELD_FORMAT = "%s" + SEPARATOR + "%d" + SEPARATOR + "%d" + SEPARATOR + "%d"
			+ SEPARATOR + "%d";
	public static final String UPDATE_FIELD_FORMAT = "%s" + SEPARATOR + "%d" + SEPARATOR + "%d" + SEPARATOR + "%d";
	public static final String UPDATE_SUDOKU_FORMAT = "%s" + SEPARATOR + "%s" + SEPARATOR + "%s";
	public static final String NEW_GAME_FORMAT = "%s";
	public static final String SOLVE_FORMAT = "%s"; // current sudoku from model will be solved. it isn't transfering sudoku from view.  

	private List<ModelElement> model;
	private List<ViewElement> view;

	public static final int QUEUE_SIZE = 10;
	public static final int OFFER_TIMEOUT = 100;
	private BlockingQueue<String> queue;

	public SController() {
		this.queue = new ArrayBlockingQueue<String>(QUEUE_SIZE);

		this.model = new ArrayList<>();
		this.view = new ArrayList<>();

		// Consumer
		new Thread(new Runnable() {
			@Override
			public void run() {
				String command;
				try {
					while (!(command = queue.take()).equals(EXIT_GAME)) {
						System.out.println("consumed -> " + command);
						for (ModelElement m : model) {
							m.executeCommand(command);
						}
						
						for (ViewElement v : view) {
							v.executeCommand(command);
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	public void addModel(ModelElement m) {
		m.setQueue(queue);
		this.model.add(m);
	}

	public void addView(ViewElement v) {
		v.setQueue(queue);
		this.view.add(v);
	}
}
