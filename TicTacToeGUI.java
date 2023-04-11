package views;

import javafx.event.EventHandler;

/**
 * Play TicTacToe the computer that can have different AIs to beat you. 
 * Select the Options menus to begin a new game, switch strategies for 
 * the computer player (BOT or AI), and to switch between the two views.
 * 
 * This class represents an event-driven program with a graphical user 
 * interface as a controller between the view and the model. It has 
 * event handlers to mediate between the view and the model.
 * 
 * This controller employs the Observer design pattern that updates two 
 * views every time the state of the tic tac toe game changes:
 * 
 *    1) whenever you make a move by clicking a button or an area of either view
 *    2) whenever the computer AI makes a move
 *    3) whenever there is a win or a tie
 *    
 * You can also select two different strategies to play against from the menus
 * 
 * @author Rick Mercer
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.CornerFirstAI;
import model.IntermediateAI;
import model.OurObserver;
import model.RandomAI;
import model.TicTacToeGame;

public class TicTacToeGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private TicTacToeGame theGame;
	private MenuBar menuBar;

	private OurObserver currentView;
	private OurObserver buttonView;
	private OurObserver textAreaView;

	private BorderPane window;
	public static final int width = 254;
	public static final int height = 360;

	public void start(Stage stage) {
		stage.setTitle("Tic Tac Toe");
		window = new BorderPane();
		Scene scene = new Scene(window, width, height);
		initializeGameForTheFirstTime();

		// Set up the views
		setMenu();
		window.setTop(menuBar);
		buttonView = new ButtonView(theGame);
		textAreaView = new TextAreaView(theGame);
		
		theGame.addObserver(buttonView);
		theGame.addObserver(textAreaView);
		setViewTo(textAreaView);
		
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Set the game to the default of an empty board and the random AI.
	 */
	public void initializeGameForTheFirstTime() {
		theGame = new TicTacToeGame();
		// This event driven program will always have
		// a computer player who takes the second turn
		theGame.setComputerPlayerStrategy(new RandomAI());
	}

	private void setViewTo(OurObserver newView) {
		window.setCenter(null);
		currentView = newView;
		window.setCenter((Node) currentView);
	}

	private void setMenu() {
		menuBar = new MenuBar();

		MenuItem changeToRand = new MenuItem("RandomAI");
		MenuItem changeToInt = new MenuItem("Intermediate");
		MenuItem changeToCorner = new MenuItem("Corner Strategy");
		Menu strategies = new Menu("Strategies");
		strategies.getItems().addAll(changeToRand, changeToInt, changeToCorner);

		MenuItem changeToTextView = new MenuItem("Text View");
		MenuItem changeToButtonView = new MenuItem("Button View");
		Menu views = new Menu("Views");
		views.getItems().addAll(changeToTextView, changeToButtonView);

		MenuItem newGame = new MenuItem("New Game");

		Menu options = new Menu("Options");

		options.getItems().addAll(newGame, views, strategies);

		menuBar.getMenus().addAll(options);
		
		changeToButtonView.setOnAction(new ButtonViewHandler());
		changeToTextView.setOnAction(new TextViewHandler());
		changeToRand.setOnAction(new RandAIHandler());
		changeToInt.setOnAction(new IntAIHandler());
		newGame.setOnAction(new NewGameHandler());
		changeToCorner.setOnAction(new CornerAIHandler());
		
	}
	
	private class ButtonViewHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			setViewTo(buttonView);
		}
	}
	
	private class TextViewHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			setViewTo(textAreaView);
		}
	}
	
	private class RandAIHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			theGame.setComputerPlayerStrategy(new RandomAI());
		}
	}
	
	private class IntAIHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			theGame.setComputerPlayerStrategy(new IntermediateAI());
		}
	}
	
	private class NewGameHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			theGame.startNewGame();	
		}
	}
	
	private class CornerAIHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			theGame.setComputerPlayerStrategy(new CornerFirstAI());
		}
	}

}