package project3;

import java.util.Random;

public class GameController {
	private Board board; /** Variable for the in-game Board object */
	private Random rng; /** Random class to be used for random tile positions */
	private GameStatus status; /** Global game status */
	private int winValue; /** The dynamic win condition, stores the winning value */
	private int boardSize; /** the size of our game board */
	
	public GameController() {
		board = new Board();
		rng = new Random();
		winValue = 2048;
		boardSize = 4;
		
		status = GameStatus.IN_PROGRESS;
		newTile();
	}
	
	public GameController(int boardSize, int winValue) {
		board = new Board(boardSize);
		rng = new Random();
		this.winValue = winValue;
		this.boardSize = boardSize;
		
		newTile();
		status = GameStatus.IN_PROGRESS;
	}
	
	/**
	 * generates a new tile on the board
	 */
	public void newTile() {
		Tile tmp;
		int i, j;
		do {
			i = rng.nextInt(boardSize);
			j = rng.nextInt(boardSize);
			tmp = board.getTile(i, j);
		} while (tmp != null);
		
		board.setTile(i, j, new Tile((rng.nextInt(2) + 1) * 2));
	}
	
	/**
	 * resets the board to its starting position
	 */
	public void reset() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				board.setTile(i, j, null);
			}
		}
		status = GameStatus.IN_PROGRESS;
		newTile();
	}
	
	/**
	 * Checks all tiles, if any are equal to the winning value, the game status is changed
	 */
	private void checkWin() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (board.getValue(i, j) == winValue) {
					status = GameStatus.WON;
				}
			}
		}
	}
	
	/**
	 * Checks all tiles, if all tiles are stuck, the game status is changed to "lost"
	 */
	private void checkLoss() {
		if (!board.hasEmpty()) {
			boolean gameOver = true;
			for (int i = 0; i < boardSize; i++) {
				for (int j = 0; j < boardSize; j++) {
					// all tiles must return false for the game to be over
					if (findSimilarNeighbors(i, j)) {
						gameOver = false;
					}
				}
			}
			if (gameOver) {
				status = GameStatus.LOST;
			}
		}
	}
	
	/**
	 * Given the coordinates of a tile, find if it has any matching neighbors
	 * @param row the first coordinate of the tile to observe
	 * @param col the second coordinate of the tile to observe
	 * @return true if the tile has matching neighbors, false if it does not
	 */
	private boolean findSimilarNeighbors(int row, int col) {
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = col - 1; j <= col + 1; j++) {
				if ((i < 0 || i >= boardSize) || (j < 0 || j >= boardSize)) {
					continue;
				}
				
				// only one of the expressions can be true for the statement to be true
				if (i == row ^ j == col) {
					if (board.getValue(row, col) == board.getValue(i, j)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Given a starting point (which should be 0 and 1, respectively) recurse through the tiles and combine or move them to the left
	 * @param row the starting point for rows
	 * @param col the starting point for columns
	 */
	public void recurseLeft(int row, int col) {
		if (row == boardSize || col == 0) {
			return;
		}
		else if (col == boardSize) {
			recurseLeft(row + 1, 1);
			return;
		}
		
		Tile tmp = board.getTile(row, col);
		if (tmp == null) {
			recurseLeft(row, col + 1);
		}
		else if (board.getTile(row, col - 1) == null) {
			board.setTile(row, col - 1, tmp);
			board.setTile(row, col, null);
			
			recurseLeft(row, col - 1);
		}
		else if (board.getValue(row, col - 1) == tmp.getValue()) {
			board.getTile(row, col - 1).setValue(tmp.getValue() * 2);
			board.setTile(row, col, null);
			
			recurseLeft(row, col - 1);
		}
		
		recurseLeft(row, col + 1);
	}
	
	/**
	 * Given a starting point (which should be 0 and boardSize - 2, respectively) recurse through the tiles and combine or move them to the right
	 * @param row the starting point for rows
	 * @param col the starting point for columns
	 */
	public void recurseRight(int row, int col) {
		if (row == boardSize || col == boardSize - 1) {
			return;
		}
		else if (col == -1) {
			recurseRight(row + 1, boardSize - 2);
			return;
		}
		
		Tile tmp = board.getTile(row, col);
		if (tmp == null) {
			recurseRight(row, col - 1);
		}
		else if (board.getTile(row, col + 1) == null) {
			board.setTile(row, col + 1, tmp);
			board.setTile(row, col, null);
			
			recurseRight(row, col + 1);
		}
		else if (board.getValue(row, col + 1) == tmp.getValue()) {
			board.getTile(row, col + 1).setValue(tmp.getValue() * 2);
			board.setTile(row, col, null);
			
			recurseRight(row, col + 1);
		}
		
		recurseRight(row, col - 1);
	}
	
	/**
	 * Given a starting point (which should be 1 and 0, respectively) recurse through the tiles and combine or move them upwards
	 * @param row the starting point for rows
	 * @param col the starting point for columns
	 */
	public void recurseUp(int row, int col) {
		if (col == boardSize || row == 0) {
			return;
		}
		else if (row == boardSize) {
			recurseUp(1, col + 1);
			return;
		}
		
		Tile tmp = board.getTile(row, col);
		if (tmp == null) {
			recurseUp(row + 1, col);
		}
		else if (board.getTile(row - 1, col) == null) {
			board.setTile(row - 1, col, tmp);
			board.setTile(row, col, null);
			
			recurseUp(row - 1, col);
		}
		else if (board.getValue(row - 1, col) == tmp.getValue()) {
			board.getTile(row - 1, col).setValue(tmp.getValue() * 2);
			board.setTile(row, col, null);
			
			recurseUp(row - 1, col);
		}
		
		recurseUp(row + 1, col);
	}
	
	/**
	 * Given a starting point (which should be boardSize - 2 and 0, respectively) recurse through the tiles and combine or move them downwards
	 * @param row the starting point for rows
	 * @param col the starting point for columns
	 */
	public void recurseDown(int row, int col) {
		if (col == boardSize || row == boardSize - 1) {
			return;
		}
		else if (row == -1) {
			recurseDown(boardSize - 2, col + 1);
			return;
		}
		
		Tile tmp = board.getTile(row, col);
		if (tmp == null) {
			recurseDown(row - 1, col);
		}
		else if (board.getTile(row + 1, col) == null) {
			board.setTile(row + 1, col, tmp);
			board.setTile(row, col, null);
			
			recurseDown(row + 1, col);
		}
		else if (board.getValue(row + 1, col) == tmp.getValue()) {
			board.getTile(row + 1, col).setValue(tmp.getValue() * 2);
			board.setTile(row, col, null);
			
			recurseDown(row + 1, col);
		}
		
		recurseDown(row - 1, col);
	}
	
	public GameStatus getStatus() {
		// order matters, if we can't move but there is a max tile then we've still won
		checkLoss();
		checkWin();
		return status;
	}
	
	public Board getBoard() {
		return board;
	}
}