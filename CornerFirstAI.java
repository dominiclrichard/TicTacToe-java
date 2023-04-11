package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author dominicrichard
 *
 */
public class CornerFirstAI implements TicTacToeStrategy {
	
	public CornerFirstAI() {
		
	}
	
	@Override
	public OurPoint desiredMove(TicTacToeGame theGame) {
		if (theGame.maxMovesRemaining() == 0)
			throw new IGotNowhereToGoException(" -- Hey there programmer, the board is filled");

		int[] moveArr = aiWinPossibility(theGame);
		
		if (moveArr == null) {
			moveArr = humanWinPossibility(theGame);
			while (moveArr == null || !theGame.available(moveArr[0], moveArr[1])) {
				moveArr = bestMove(theGame);
			}
		}

		int row = moveArr[0];
		int col = moveArr[1];

		OurPoint move = new OurPoint(row, col);
		return move;
	}
	
	private int[] aiWinPossibility(TicTacToeGame game) {
		char[][] board = game.getTicTacToeBoard();

		for (int i = 0; i < 3; i++) {
			int xCounter = 0;
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == 'O') {
					xCounter += 1;
				}
			}
			if (xCounter == 2) {
				int[] move = new int[2];
				move[0] = i;
				for (int j = 0; j < 3; j++) {
					if (game.available(i, j)) {
						move[1] = j;
						return move;
					}
				}
			}
		}

		for (int i = 0; i < 3; i++) {
			int xCounter = 0;
			for (int j = 0; j < 3; j++) {
				if (board[j][i] == 'O') {
					xCounter += 1;
				}
			}
			if (xCounter == 2) {
				int[] move = new int[2];
				move[1] = i;
				for (int j = 0; j < 3; j++) {
					if (game.available(j, i)) {
						move[0] = j;
						return move;
					}
				}
			}
		}

		int xCounter = 0;
		for (int i = 0; i < 3; i++) {
			if (board[i][i] == 'O') {
				xCounter += 1;
			}
		}
		if (xCounter == 2) {
			int[] move = new int[2];
			for (int i = 0; i < 3; i++) {
				if (game.available(i, i)) {
					move[0] = i;
					move[1] = i;
					return move;
				}
			}
		}

		xCounter = 0;
		if (board[0][2] == 'O') {
			xCounter += 1;
		}
		if (board[1][1] == 'O') {
			xCounter += 1;
		}
		if (board[2][0] == 'O') {
			xCounter += 1;
		}
		if (xCounter == 2) {
			int[] move = new int[2];
			if (game.available(0, 2)) {
				move[0] = 0;
				move[1] = 2;
				return move;
			}
			if (game.available(1, 1)) {
				move[0] = 1;
				move[1] = 1;
				return move;
			}
			if (game.available(2, 0)) {
				move[0] = 2;
				move[1] = 0;
				return move;
			}
		}

		return null;
	}

	private int[] humanWinPossibility(TicTacToeGame game) {
		char[][] board = game.getTicTacToeBoard();

		for (int i = 0; i < 3; i++) {
			int xCounter = 0;
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == 'X') {
					xCounter += 1;
				}
			}
			if (xCounter == 2) {
				int[] move = new int[2];
				move[0] = i;
				for (int j = 0; j < 3; j++) {
					if (game.available(i, j)) {
						move[1] = j;
						return move;
					}
				}
			}
		}

		for (int i = 0; i < 3; i++) {
			int xCounter = 0;
			for (int j = 0; j < 3; j++) {
				if (board[j][i] == 'X') {
					xCounter += 1;
				}
			}
			if (xCounter == 2) {
				int[] move = new int[2];
				move[1] = i;
				for (int j = 0; j < 3; j++) {
					if (game.available(j, i)) {
						move[0] = j;
						return move;
					}
				}
			}
		}

		int xCounter = 0;
		for (int i = 0; i < 3; i++) {
			if (board[i][i] == 'X') {
				xCounter += 1;
			}
		}
		if (xCounter == 2) {
			int[] move = new int[2];
			for (int i = 0; i < 3; i++) {
				if (game.available(i, i)) {
					move[0] = i;
					move[1] = i;
					return move;
				}
			}
		}

		xCounter = 0;
		if (board[0][2] == 'X') {
			xCounter += 1;
		}
		if (board[1][1] == 'X') {
			xCounter += 1;
		}
		if (board[2][0] == 'X') {
			xCounter += 1;
		}
		if (xCounter == 2) {
			int[] move = new int[2];
			if (game.available(0, 2)) {
				move[0] = 0;
				move[1] = 2;
				return move;
			}
			if (game.available(1, 1)) {
				move[0] = 1;
				move[1] = 1;
				return move;
			}
			if (game.available(2, 0)) {
				move[0] = 2;
				move[1] = 0;
				return move;
			}
		}

		return null;
	}

	private int[] bestMove(TicTacToeGame game) {

		int[][] bestMoves = { { 0, 0 }, { 1, 1 }, { 2, 2 }, { 0, 2 }, { 2, 0 } };

		ArrayList<Integer> validIndecesOne = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			if (game.available(bestMoves[i][0], bestMoves[i][1])) {
				validIndecesOne.add(i);
			}
		}

		Random random = new Random();

		if (!validIndecesOne.isEmpty()) {
			return bestMoves[random.nextInt(5)];
		}

		int[][] worseMoves = { { 0, 1 }, { 1, 0 }, { 1, 2 }, { 2, 1 } };

		return worseMoves[random.nextInt(4)];

	}

}
