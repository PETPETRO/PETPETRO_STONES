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
	private static Stones instance;
	private long startMillis;

	private UserInterface userInterface;
	private BestTimes bestTimes;

	private Stones() throws ClassNotFoundException, IOException {
		bestTimes = new BestTimes();
		instance = this;
		userInterface = new ConsoleUI();
		Field field = new Field(3, 3);
		startMillis = System.currentTimeMillis();
		System.out.println(bestTimes.toString());
		userInterface.newGameStarted(field);
		System.out.println(bestTimes.toString());
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		new Stones();
	}

	public static Stones getInstance() {
		return instance;
	}

	public static void setInstance(Stones instance) {
		Stones.instance = instance;
	}

	public int getPlayingSeconds() {
		return (int) ((System.currentTimeMillis() - startMillis) / 1000);
	}

}
