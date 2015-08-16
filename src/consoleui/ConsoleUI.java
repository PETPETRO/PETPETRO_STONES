package consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.Clue;
import core.Field;
import core.GameState;
import stones.BestTimes;
import stones.GameLoader;
import stones.Stones;
import stones.UserInterface;

public class ConsoleUI implements UserInterface {

	private Field field;
	private GameLoader game = new GameLoader();

	/** Input reader. */
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private long startMillis;

	/**
	 * Reads line of text from the reader.
	 * 
	 * @return line as a string
	 */
	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public void newGameStarted(Field field) {
		BestTimes best = new BestTimes();
		startMillis = System.currentTimeMillis();
		System.out.println("Vitaj: " + System.getProperty("user.name"));
		// this.field = field;
		Field loadGame = game.load();
		// Field newGame = this.field;

		if (loadGame != null) {
			System.out.println("Do you want to load game from file ? Y/N");
			String input = readLine().toUpperCase();
			do {
				if (input == "N") {
					this.field = field;
				} else if (input == "Y") {
					this.field = loadGame;
				} else {
					System.out.println("Do you want to load game from file ? Y/N");
				}
			} while (input == "N" || input == "Y");
			this.field = loadGame;
		} else
			this.field = field;

		do {
			update();
			processInput();
			if (field.isSolved(this.field)) {

				field.setState(GameState.SOLVED);

			}

		} while (field.getState() == GameState.PLAYING);

		if (field.getState() == GameState.SOLVED) {
			update();
			int time = (int) ((System.currentTimeMillis() - startMillis) / 1000);
			System.out.println("VYHRAL SI !!!!!!");
			System.out.println("Zadaj svoje meno: ");
			String meno = readLine().toString();
			best.addPlayerTime(meno, time);
		}

	}

	private void processInput() {
		String input = readLine();
		try {
			handleInput(input);
		} catch (Exception ex) {
			ex.getMessage();
			System.err.println(ex);
		}
	}

	void handleInput(String input) throws MyException, ClassNotFoundException, IOException {
		input = input.toUpperCase();
		Pattern p = Pattern.compile("W|S|D|A|UP|DOWN|RIGHT|LEFT");
		Matcher m = p.matcher(input);
		if (m.matches()) {
			if (input.trim().equals("W") || input.trim().equals("UP")) {
				for (int r = 0; r < field.getRowCount(); r++) {
					for (int c = 0; c < field.getColumnCount(); c++) {
						if (field.getTile(r, c) instanceof Clue) {
							field.moveUp(r, c);
							return;
						}
					}
				}

			} else if (input.trim().equals("S") || input.trim().equals("DOWN")) {
				for (int r = 0; r < field.getRowCount(); r++) {
					for (int c = 0; c < field.getColumnCount(); c++) {
						if (field.getTile(r, c) instanceof Clue) {
							field.moveDown(r, c);
							return;
						}
					}
				}
			} else if (input.trim().equals("A") || input.trim().equals("LEFT")) {
				for (int r = 0; r < field.getRowCount(); r++) {
					for (int c = 0; c < field.getColumnCount(); c++) {
						if (field.getTile(r, c) instanceof Clue) {
							field.moveLeft(r, c);
							return;
						}
					}
				}
			} else if (input.trim().equals("D") || input.trim().equals("RIGHT")) {
				for (int r = 0; r < field.getRowCount(); r++) {
					for (int c = 0; c < field.getColumnCount(); c++) {
						if (field.getTile(r, c) instanceof Clue) {
							field.moveRight(r, c);
							return;
						}
					}
				}
			} else {
				throw new MyException("Neda sa");
			}
		} else if (input.trim().equals("X") || input.trim().equals("EXIT")) {
			System.err.println("Ukoncil si hru");
			game.save(this.field);
			System.exit(0);
		} else if (input.trim().equals("N") || input.trim().equals("NEW")) {
			Stones.setInstance(null);
			Stones.main(null);
		} else {
			throw new MyException("Nespravny vstup");
		}
	}

	@Override
	public void update() {
		int rowCount = field.getRowCount();
		int columnCount = field.getColumnCount();
		StringBuilder builder = new StringBuilder();
		Formatter formatter = new Formatter(builder);
		int space = ((columnCount * 3 + 1) - 6) / 2;
		formatter.format("%" + space + "s", " ");
		formatter.format("%6s", "STONES");
		formatter.format("%" + space + "s", " \n");

		for (int i = 0; i <= (columnCount * 3 + 1); i++) {
			formatter.format("%1s", "-");
		}
		formatter.format("%n");
		for (int row = 0; row < rowCount; row++) {
			formatter.format("|");
			for (int column = 0; column < columnCount; column++) {

				formatter.format("%3s", field.getTile(row, column));
			}
			formatter.format("|%n");
		}

		for (int i = 0; i <= (columnCount * 3 + 1); i++) {
			formatter.format("%1s", "-");
		}

		System.out.print(builder);
		formatter.close();
		System.out.println(
				"\nn (new)   – new game \nx (exit)  – exit game   \nw (up)    – move stone up \ns (down)  – move stone down \na (left)  – move stone left \nd (right) – move stone right");
		System.out.println("\nPLAYING TIME:" + Stones.getInstance().getPlayingSeconds());

	}

}
