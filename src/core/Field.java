package core;

import java.io.Serializable;
import java.util.Random;

import consoleui.MyException;

public class Field implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Tile[][] tiles;

	private final int rowCount;

	private final int columnCount;
	private GameState state = GameState.PLAYING;

	/**
	 * Constructor
	 * 
	 * @param rowCount
	 * @param columnCount
	 */
	public Field(int rowCount, int columnCount) {

		if (rowCount < 1) {
			System.err.println("Pocet riadkov musi byt vacsi ako nula!");
			System.exit(0);

		} else if (columnCount < 1) {
			System.err.println("Pocet stpcov musi byt vacsi ako nula!");
			System.exit(0);
		}
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		tiles = new Tile[rowCount][columnCount];
		generate();
	}

	/**
	 * Generate new field
	 */
	public void generate() {

		for (int c = 0; c < 1; c++) {
			int row = new Random().nextInt(rowCount);
			int column = new Random().nextInt(columnCount);
			tiles[row][column] = new Clue();
		}
		int i = 1;
		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < columnCount; c++) {
				if (!(tiles[r][c] instanceof Clue)) {
					tiles[r][c] = new Stone(i++);
				}
			}
		}

		shuffleStones();

	}

	/**
	 * Shuffle stones in game field
	 */
	public void shuffleStones() {
		int count = rowCount * columnCount * 100;
		for (int i = 0; i < count; i++) {
			for (int r = 0; r < rowCount; r++) {
				for (int c = 0; c < columnCount; c++) {
					if (tiles[r][c] instanceof Clue) {

						int move = new Random().nextInt(5);
						switch (move) {
						case 1:
							if (r != 0) {
								moveDown(r, c);
								break;
							}
						case 2:
							if (r != rowCount - 1) {
								moveUp(r, c);
								break;
							}
						case 3:
							if (c != columnCount - 1) {
								moveLeft(r, c);
								break;
							}
						case 4:
							if (c != 0) {
								moveRight(r, c);
								break;
							}
						default:
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Move stone in field to the left
	 * 
	 * @param row
	 * @param column
	 */
	public void moveLeft(int row, int column) {
		try {
			Tile tile = tiles[row][column];
			tiles[row][column] = tiles[row][column + 1];
			tiles[row][column + 1] = tile;
		} catch (Exception e) {
			e = new MyException("You can not move left now!");
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Move stone in field to the right
	 * 
	 * @param row
	 * @param column
	 */
	public void moveRight(int row, int column) {
		try {
			Tile tile = tiles[row][column];
			tiles[row][column] = tiles[row][column - 1];
			tiles[row][column - 1] = tile;
		} catch (Exception e) {
			e = new MyException("You can not move right now!");
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Move stone in field up
	 * 
	 * @param row
	 * @param column
	 */
	public void moveUp(int row, int column) {
		try {
			Tile tile = tiles[row][column];
			tiles[row][column] = tiles[row + 1][column];
			tiles[row + 1][column] = tile;
		} catch (Exception e) {
			e = new MyException("You can not move up now!");
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Move stone in field down
	 * 
	 * @param row
	 * @param column
	 */
	public void moveDown(int row, int column) {
		try {
			Tile tile = tiles[row][column];
			tiles[row][column] = tiles[row - 1][column];
			tiles[row - 1][column] = tile;
		} catch (Exception e) {
			e = new MyException("You can not move down now!");
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Check if game is solved
	 * 
	 * @param field
	 * @return true if game is solved, false otherwise
	 */
	public boolean isSolved(Field field) {
		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < (columnCount); c++) {
				Tile tile = field.getTile(r, c);
				if ((tile instanceof Stone && ((Stone) tile).getValue() == (r * columnCount + c + 1))
						|| (tile instanceof Clue && r == rowCount - 1 && c == columnCount - 1)) {
				} else
					return false;
			}
		}
		return true;
	}

	/**
	 * Get state of game
	 * 
	 * @return GameState
	 */
	public GameState getState() {
		return state;
	}

	/**
	 * Set state of game
	 * 
	 * @param state
	 */
	public void setState(GameState state) {
		this.state = state;
	}

	/**
	 * Get tile on row and column form field
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	/**
	 * Get count of rows in field
	 * 
	 * @return count of rows
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * Get count of columns in field
	 * 
	 * @return count of columns
	 */
	public int getColumnCount() {
		return columnCount;
	}

}
