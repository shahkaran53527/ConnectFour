import java.util.HashSet;
import java.util.Stack;

import javafx.util.Pair;

public class Game {

	GameButton[][] gameBoard = new GameButton[6][7];
	HashSet<Pair<Integer, Integer>> legalMoves;
	HashSet<Pair<Integer, Integer>> p1Buttons;
	HashSet<Pair<Integer, Integer>> p2Buttons;
	HashSet<Pair<Integer, Integer>> pButtons;
	Stack<Pair<Integer, Integer>> latestMoves;
	int turn;
	
	public Game() {
		legalMoves = new HashSet<>();
		p1Buttons = new HashSet<>();
		p2Buttons = new HashSet<>();
		pButtons = new HashSet<>();
		latestMoves = new Stack<>();
		turn = 1;
	}
	
	public int getTurn() {
		return turn;
	}
	
	public void turnReset() {
		if (turn == 1) {
			turn = 2;
		} else if (turn == 2) {
			turn = 1;
		}
	}
	
	public int otherPlayer() {
		if (turn == 1) {
			return 2;
		} else {
			return 1;
		}
	}
	public boolean makeMove(Pair <Integer, Integer> moveIndex) {
		if (checkLegalMove(moveIndex)) {
			if (turn == 1) {
				((GameButton)gameBoard[moveIndex.getKey()][moveIndex.getValue()]).setStyle("-fx-color: Red");
			} else if (turn == 2) {
				((GameButton)gameBoard[moveIndex.getKey()][moveIndex.getValue()]).setStyle("-fx-color: Green");
			}
			((GameButton)gameBoard[moveIndex.getKey()][moveIndex.getValue()]).setDisable(true);
			legalMoves.remove(moveIndex);
			if (moveIndex.getKey() > 0) {
				legalMoves.add(new Pair<>(moveIndex.getKey() - 1, moveIndex.getValue()));
			}
			latestMoves.push(moveIndex);
			if (turn == 1) {
				p1Buttons.add(moveIndex);
			} else if (turn == 2) {
				p2Buttons.add(moveIndex);
			}
			return true;
		}
		return false;
	}
	
	public boolean checkLegalMove(Pair<Integer, Integer> move) {
		return(legalMoves.contains(move));
	}
	
	public boolean checkWin(Pair<Integer, Integer> move) {
		if (legalMoves.size() == 0) {
			turn = 0;
			return true;
		}
		if (turn == 1) {
			pButtons = p1Buttons;
		} else if (turn == 2){
			pButtons = p2Buttons;
		}
		if (checkDown(move) >= 4 || (checkLeft(move) + checkRight(move)) >= 4 || 
				(checkLD(move) + checkRU(move)) >= 4 || (checkRD(move) + checkLU(move)) >= 4) {
			pButtons = null;
			return true;
		}
		pButtons = null;
		turnReset();
		return false;
	}
	
	public Pair<Integer, Integer> reverse() {
		if (latestMoves.size() == 0) {
			return null;
		}
		Pair<Integer, Integer> lastMove = latestMoves.pop();
		if (turn == 1) {
			p2Buttons.remove(lastMove);
		} else if (turn == 2) {
			p1Buttons.remove(lastMove);
		}
		gameBoard[lastMove.getKey()][lastMove.getValue()].setDisable(false);
		gameBoard[lastMove.getKey()][lastMove.getValue()].setStyle("");
		legalMoves.remove(new Pair<Integer, Integer>(lastMove.getKey() - 1, lastMove.getValue()));
		legalMoves.add(new Pair<>(lastMove.getKey(), lastMove.getValue()));
		turnReset();
		return lastMove;
	}
	
	private int checkDown(Pair<Integer, Integer> button) {
		if (!pButtons.contains(button)) {
			return 0;
		}
		return 1 + checkDown(new Pair<>(button.getKey() + 1, button.getValue()));
	}
	
	private int checkLeft(Pair<Integer, Integer> button) {
		if (!pButtons.contains(button)) {
			return 0;
		}
		return 1 + checkLeft(new Pair<>(button.getKey(), button.getValue() - 1));
	}
	
	private int checkRight(Pair<Integer, Integer> button) {
		if (!pButtons.contains(button)) {
			return -1;
		}
		return 1 + checkRight(new Pair<>(button.getKey(), button.getValue() + 1));
	}
	
	private int checkLD(Pair<Integer, Integer> button) {
		if (!pButtons.contains(button)) {
			return 0;
		}
		return 1 + checkLD(new Pair<>(button.getKey() + 1, button.getValue() - 1));
	}
	
	private int checkRU(Pair<Integer, Integer> button) {
		if (!pButtons.contains(button)) {
			return -1;
		}
		return 1 + checkRU(new Pair<>(button.getKey() - 1, button.getValue() + 1));
	}
	
	private int checkRD(Pair<Integer, Integer> button) {
		if (!pButtons.contains(button)) {
			return 0;
		}
		return 1 + checkRD(new Pair<>(button.getKey() + 1, button.getValue() + 1));
	}
	
	private int checkLU(Pair<Integer, Integer> button) {
		if (!pButtons.contains(button)) {
			return -1;
		}
		return 1 + checkLU(new Pair<>(button.getKey() - 1, button.getValue() - 1));
	}

	
	
	
}
