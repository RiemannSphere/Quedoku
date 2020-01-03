package view;

import java.awt.AWTEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;

import controller.SController;
import controller.ViewElement;

public class SViewField extends ViewElement {

	private JFormattedTextField textField;
	private int row;
	private int col;
	private int value;
	private boolean fixed;

	private Consumer<AWTEvent> fieldChangeListener = (e) -> {
		// Producer
		new Thread(() -> {
			try {
				String userInput = textField.getText().trim();
				int newValue;
				if (userInput.length() == 0) {
					newValue = 0;
				} else {
					newValue = Integer.valueOf(userInput);
				}
				changeSudokuField(row, col, value, newValue);
			} catch (NumberFormatException ex) {
				changeSudokuField(row, col, value, value);
			}
		}).start();
	};
	private ActionListener fieldChangeOnEnter = (e) -> {
		fieldChangeListener.accept(e);
	};
	private FocusAdapter fieldChangedOnFocusLost = new FocusAdapter() {
		@Override
		public void focusLost(FocusEvent e) {
			fieldChangeListener.accept(e);
		}
	};

	public SViewField(int r, int c, boolean fixed, SController controller) {
		super(controller);
		this.row = r;
		this.col = c;
		textField = new JFormattedTextField();
		textField.setBackground(ConstV.FIELD);
		textField.setBorder(BorderFactory.createEmptyBorder());
		textField.setHorizontalAlignment(JFormattedTextField.CENTER);
		textField.setForeground(ConstV.NUMBER);
		textField.setFont(ConstV.NUMBER_FONT);
		if (!fixed) {
			addListenersAndEnableText();
		} else {
			removeListenersAndDisableText();
		}
	}

	public JFormattedTextField getTextField() {
		return textField;
	}

	public void setValue(int value) {
		this.value = value;
		this.textField.setText(value == 0 ? " " : Integer.toString(value));
	}

	/**
	 * Not only a setter. It also adds/removes listeners if field fixed or not. 
	 * @param fixed
	 */
	public void setFixed(boolean fixed) {
		if( this.fixed == fixed ) {
			return;
		}
		
		if (!fixed) {
			addListenersAndEnableText();
		} else {
			removeListenersAndDisableText();
		}
		this.fixed = fixed;
	}

	private void addListenersAndEnableText() {
		textField.addActionListener(fieldChangeOnEnter);
		textField.addFocusListener(fieldChangedOnFocusLost);
		textField.setEditable(true);
	}

	private void removeListenersAndDisableText() {
		textField.removeActionListener(fieldChangeOnEnter);
		textField.removeFocusListener(fieldChangedOnFocusLost);
		textField.setEditable(false);
	}

	@Override
	public void updateSudokuField(int row, int col, int value) {
		if (this.row == row && this.col == col && !this.fixed) {
			setValue(value);
		}
	}

}
