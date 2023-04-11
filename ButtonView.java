package views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.OurObserver;
import model.TicTacToeGame;

/**
 * @author dominicrichard
 *
 */
public class ButtonView extends BorderPane implements OurObserver {

	private TicTacToeGame theGame;
	private Button[][] buttons;
	private GridPane buttonPane;

	public ButtonView(TicTacToeGame theModel) {
		theGame = theModel;
		buttons = new Button[3][3];
		initializePanel();
	}

	private void initializePanel() {

		Font fontOne = new Font("Courier New", 35);
		Font fontTwo = new Font("American Typewriter", 14);

		buttonPane = new GridPane();

		buttonPane.setHgap(5);
		buttonPane.setVgap(5);
		buttonPane.setPadding(new Insets(17));

		Label gameLabel = new Label("Click to make a move");
		gameLabel.setStyle("fx-font-color: blue");
		gameLabel.setFont(fontTwo);
		gameLabel.setPadding(new Insets(20, 20, 60, 60));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Button button = new Button(Character.toString(theGame.getTicTacToeBoard()[i][j]));
				button.setFont(fontOne);
				buttons[i][j] = button;
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttonPane.add(buttons[i][j], i, j);
			}
		}

		this.setCenter(buttonPane);
		this.setBottom(gameLabel);
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j].setOnAction(new ClickHandler());
			}
		}

	}

	@Override
	public void update(Object theObserved) {
		Font fontOne = new Font("Courier New", 35);
		Font fontTwo = new Font("American Typewriter", 14);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Button button = new Button(Character.toString(theGame.getTicTacToeBoard()[i][j]));
				button.setFont(fontOne);
				buttons[i][j] = button;
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttonPane.add(buttons[i][j], i, j);
			}
		}
		
		if (theGame.stillRunning()) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					buttons[i][j].setOnAction(new ClickHandler());
				}
			}
		}
		
		else {
			
			Label gameLabel = new Label("Click to make a move");
			gameLabel.setStyle("fx-font-color: blue");
			gameLabel.setFont(fontTwo);
			gameLabel.setPadding(new Insets(20, 20, 60, 60));
			
			if (theGame.didWin('X')) {
				gameLabel.setText("X Wins");
				this.setBottom(gameLabel);
				return;
			}
			
			if (theGame.didWin('O')) {
				gameLabel.setText("O Wins");
				this.setBottom(gameLabel);
				return;
			}
			
			gameLabel.setText("Tie");
			this.setBottom(gameLabel);
			
		}
		
	}

	private class ClickHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			Button buttonClicked = (Button) arg0.getSource();

			for (int row = 0; row < 3; row++) {
				for (int col = 0; col < 3; col++) {
					if (buttons[row][col] == buttonClicked) {
						theGame.humanMove(row, col, false);
						update(theGame);
					}
				}
			}
		}
	}
}
