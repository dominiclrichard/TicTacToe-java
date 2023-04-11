package views;

import java.util.Scanner;

import model.TicTacToeGame;

public class ConsoleView {
	
	public static void main(String[] args) {
		TicTacToeGame game = new TicTacToeGame();
		runGame(game);
	}
	
	private static void runGame(TicTacToeGame game) {
		boolean running = true;
		Scanner inputScan = new Scanner(System.in);
		while (running == true) {
			System.out.print("Enter row and column: ");
			String input = inputScan.nextLine();
			int[] rowCol = inputToRC(input);
			game.humanMove(rowCol[0], rowCol[1], !running);
			System.out.println(game);
			System.out.println();
			running = game.stillRunning();
			if (game.didWin('X')) {
				System.out.println("X wins");
				inputScan.close();
				return;
			}
			
			if (game.didWin('O')) {
				System.out.println("O wins");
				inputScan.close();
				return;
			}
		}
		inputScan.close();
		System.out.println("Tie");
	}
	
	private static int[] inputToRC(String input) {
		String[] inputArr = input.split(" ");
		int[] rowCol = new int[2];
		rowCol[0] = Integer.parseInt(inputArr[0]);
		rowCol[1] = Integer.parseInt(inputArr[1]);
		return rowCol;
		
	}

}
