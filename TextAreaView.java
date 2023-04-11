package views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.OurObserver;
import model.TicTacToeGame;

/**
 * @author dominicrichard
 *
 */
public class TextAreaView extends BorderPane implements OurObserver {

	private TicTacToeGame theGame;
	private TextField rowText;
	private TextField colText;
	private TextArea boardView;
	private Button makeMove;

	public TextAreaView(TicTacToeGame theModel) {
		theGame = theModel;
		initializePanel();
	}

	private void initializePanel() {

		boardView = new TextArea();
		Font font = new Font("Courier New", 35);
		boardView.setFont(font);
		boardView.setText(theGame.toString());
		boardView.setEditable(false);
		this.setBottom(boardView);
		
		GridPane buttonPane = new GridPane();
		
		buttonPane.setHgap(10);
		buttonPane.setVgap(10);
		
		rowText = new TextField();
		rowText.setMaxWidth(50);
		rowText.setMinWidth(50);
		colText = new TextField();
		colText.setMaxWidth(50);
		colText.setMinWidth(50);
		
		Label rowLabel = new Label("row");
		Label colLabel = new Label("column");
		
		makeMove = new Button("Make Move");
		buttonPane.setPadding(new Insets(5));
		
		buttonPane.add(rowText, 2, 1);
		buttonPane.add(colText, 2, 2);
		buttonPane.add(rowLabel, 3, 1);
		buttonPane.add(colLabel, 3, 2);
		buttonPane.add(makeMove, 3, 3);
		
		this.setCenter(buttonPane);
		
		makeMove.setOnAction(new ClickHandler());
	}

	// This method is called by Observable's notifyObservers()
	@Override
	public void update(Object observable) {
		
		if (!theGame.stillRunning()) {
			makeMove.setText(makeMove.getText());
			return;
		}
		
		String rowTxt = rowText.getText().toString().strip();
		String colTxt = colText.getText().toString().strip();
		
		int row = 0;
		int col = 0;
		
		try {
			row = Integer.parseInt(rowTxt);
		}
		catch (NumberFormatException e) {
			makeMove.setText("Invalid Choice");
			return;
		}
		
		try {
			col = Integer.parseInt(colTxt);
		}
		catch (NumberFormatException e) {
			makeMove.setText("Invalid Choice");
			return;
		}
		
		if (row > 2 || row < 0) {
			makeMove.setText("Invalid Choice");
			return;
		}
		
		if (col > 2 || col < 0) {
			makeMove.setText("Invalid Choice");
			return;
		}
		
		if (!theGame.available(row, col)) {
			makeMove.setText("Invalid Choice");
			return;
		}
		
		theGame.humanMove(row, col, false);
		boardView.setText(theGame.toString());
		Font font = new Font("Courier New", 35);
		boardView.setFont(font);
		
		
		if (theGame.didWin('X')) {
			makeMove.setText("X Wins");
			rowText.setText("");
			colText.setText("");
			rowText.setEditable(false);
			colText.setEditable(false);
			return;
		}
		
		if (theGame.didWin('O')) {
			makeMove.setText("O Wins");
			rowText.setText("");
			colText.setText("");
			rowText.setEditable(false);
			colText.setEditable(false);
			return;
		}
		
		if (!theGame.stillRunning()) {
			makeMove.setText("Tie");
			rowText.setText("");
			colText.setText("");
			rowText.setEditable(false);
			colText.setEditable(false);
			return;
		}
		
		rowText.setText("");
		colText.setText("");
		makeMove.setText("Make Move");
		
	}
	
	private class ClickHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			update(theGame);
		}
		
	}
	
}