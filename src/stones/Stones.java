package stones;

import java.io.IOException;
import java.io.Serializable;

import consoleui.ConsoleUI;
import core.Field;

public class Stones implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long startMillis;

	private UserInterface userInterface;
	private BestTimes bestTimes;

	private Stones() throws ClassNotFoundException, IOException {
		bestTimes = new BestTimes();
		userInterface = new ConsoleUI();
		Field field = new Field(3, 3);
		System.out.println(bestTimes.toString());
		userInterface.newGameStarted(field);
		System.out.println(bestTimes.toString());
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		new Stones();

	}

	public int getPlayingSeconds() {
		return (int) ((System.currentTimeMillis() - startMillis) / 1000);
	}

}
