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

	private void generate() {

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

		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < columnCount - 1; c++) {
				int row = new Random().nextInt(rowCount);
				int column = new Random().nextInt(columnCount);
				Tile tile = tiles[row][column];
				tiles[row][column] = tiles[r][c];
				tiles[r][c] = tile;
			}
		}
	}

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

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

}
