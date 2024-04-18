package project3;

public class Tile {
	private int value;
	
	public Tile() {
		value = 2;
	}
	
	public Tile(int value) {
		if (!power2(value)) {
			throw new IllegalArgumentException();
		}
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	/**
	 * Sets the value of the tile to a power of 2, if the value is not a power of 2 throw an error
	 * @param value the inputted value for setting
	 */
	public void setValue(int value) {
		if (!power2(value)) {
			throw new IllegalArgumentException();
		}
		this.value = value;
	}
	
	/**
	 * Checks to see if an inputted value is a power of 2
	 * @param value the number being checked for being a power of 2
	 * @return true if the number is a power of 2, false if it is not
	 */
	static boolean power2(double value) {
		if (value == 2.0) {
			return true;
		}
		else if (value > 2.0) {
			return power2(value / 2);
		}
		else {
			return false;
		}
	}
	
	public String toString() {
		return "" + value;
	}
}