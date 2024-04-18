package project3;

import weiss.util.LinkedList;

public class Board {
	private LinkedList<LinkedList<Tile>> board; /** the doubly linked lists which store our tiles */
	private int size; /** the length of the linked list to avoid using .size() */
	
	public Board() {
		size = 4;
		board = new LinkedList<LinkedList<Tile>>();
		for (int i = 0; i < size; i++) {
			board.add(new LinkedList<Tile>());
			for (int j = 0; j < size; j++) {
				board.get(i).add(null);
			}
		}
	}
	
	public Board(int size) {
		if (size < 4 || size > 10) {
			throw new IllegalArgumentException();
		}
		
		this.size = size;
		board = new LinkedList<LinkedList<Tile>>();
		
		for (int i = 0; i < size; i++) {
			board.add(new LinkedList<Tile>());
			for (int j = 0; j < size; j++) {
				board.get(i).add(null);
			}
		}
	}
	
	/**
	 * Returns true or false based on whether the board is full
	 * @return true if the board has null tiles, false if it is full
	 */
	public boolean hasEmpty() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (getTile(i, j) == null) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns a tile from the board given its coordinates
	 * @param row the first coordinate of the tile
	 * @param col the second coordinate of the tile
	 * @return the Tile object at the given position
	 */
	public Tile getTile(int row, int col) {
		if ((row >= size || row < 0) || (col >= size || col < 0)) {
			throw new IllegalArgumentException();
		}
		
		return board.get(row).get(col);
	}
	
	/**
	 * Sets the tile at the given coordinates to the given Tile object
	 * @param row the first coordinate of the tile to alter
	 * @param col the second coordinate of the tile to alter
	 * @param t the Tile object to be set
	 */
	public void setTile(int row, int col, Tile t) {
		if ((row >= size || row < 0) || (col >= size || col < 0)) {
			throw new IllegalArgumentException();
		}

		board.get(row).set(col, t);
	}
	
	/**
	 * Gets the value of the tile at the given coordinates, if the tile is null, return -1
	 * @param row the first coordinate of the tile to check
	 * @param col the second coordinate of the tile to check
	 * @return the value of the tile at the given coordinates
	 */
	public int getValue(int row, int col) {
		if ((row >= size || row < 0) || (col >= size || col < 0)) {
			throw new IllegalArgumentException();
		}
		
		if (board.get(row).get(col) == null) {
			return -1;
		}
		else {
			return board.get(row).get(col).getValue();
		}
	}
	
	public int getSize() {
		return size;
	}
	
	/**
	 * prints the board to the terminal in a grid for testing
	 */
	public void printBoard() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (getValue(i, j) == -1) {
					System.out.print("  | ");
				}
				else {
					System.out.print("" + getValue(i, j) + " | ");
				}
			}
			System.out.println();
		}
	}
}