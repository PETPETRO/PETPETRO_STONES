package stones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Player times.
 */
public class BestTimes implements Iterable<BestTimes.PlayerTime> {
	/** List of best player times. */
	private static List<PlayerTime> playerTimes = new ArrayList<PlayerTime>();

	/**
	 * Returns an iterator over a set of best times.
	 * 
	 * @return an iterator
	 */
	public Iterator<PlayerTime> iterator() {
		return playerTimes.iterator();
	}

	/**
	 * Adds player time to best times.
	 * 
	 * @param name
	 *            name of the player
	 * @param time
	 *            player time in seconds
	 */
	public void addPlayerTime(String name, int time) {
		insertToDB(new PlayerTime(name, time));
	}

	/**
	 * Returns a string representation of the object.
	 * 
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		selectFromDB();
		String playList = "";
		Iterator<PlayerTime> iterator = iterator();
		for (int i = 0; i < 20; i++) {
			playList += "-";
		}
		playList += "\n";
		playList += String.format("%1$1s %2$-10s %3$5s %4$1s %n", "|", "Name", "Time", "|");
		for (int i = 0; i < 20; i++) {
			playList += "-";
		}
		playList += "\n";
		while (iterator.hasNext()) {
			PlayerTime p = iterator.next();
			playList += String.format("%1$1s %2$-10s %3$5s %4$1s %n", "|", p.name, p.time, "|");
		}
		for (int i = 0; i < 20; i++) {
			playList += "-";
		}

		return playList;
	}

	private void selectFromDB() {
		try (Connection connection = DriverManager.getConnection(DatabaseSetting.URL, DatabaseSetting.USER,
				DatabaseSetting.PASSWORD);
				Statement stm = connection.createStatement();
				ResultSet rs = stm.executeQuery(DatabaseSetting.QUERY_SELECT_BEST_TIMES);) {
			playerTimes.clear();
			while (rs.next()) {
				PlayerTime pt = new PlayerTime(rs.getString(1), rs.getInt(2));
				playerTimes.add(pt);
			}
			Collections.sort(playerTimes);

		} catch (Exception e) {
			System.out.println("Exception occured during loading high score to database: " + e.getMessage());
		}
	}

	private void insertToDB(PlayerTime playerTime) {
		try (Connection connection = DriverManager.getConnection(DatabaseSetting.URL, DatabaseSetting.USER,
				DatabaseSetting.PASSWORD);
				PreparedStatement pstm = connection.prepareStatement(DatabaseSetting.QUERY_ADD_BEST_TIME);) {

			try (Statement stm = connection.createStatement();) {
				stm.executeUpdate(DatabaseSetting.QUERY_CREATE_BEST_TIMES);
			} catch (Exception e) {
				// do not propagate exception, table may already exist
			}

			pstm.setString(1, playerTime.getName());
			pstm.setInt(2, playerTime.getTime());
			pstm.execute();

		} catch (Exception e) {
			System.out.println("Exception occured during saving high score to database: " + e.getMessage());
		}
	}

	/**
	 * Player time.
	 */
	public static class PlayerTime implements Comparable<PlayerTime> {
		/** Player name. */
		private final String name;

		/** Playing time in seconds. */
		private final int time;

		/**
		 * Constructor.
		 * 
		 * @param name
		 *            player name
		 * @param time
		 *            playing game time in seconds
		 */
		public PlayerTime(String name, int time) {
			this.name = name;
			this.time = time;

		}

		public String getName() {
			return name;
		}

		public int getTime() {
			return time;
		}

		@Override
		public int compareTo(PlayerTime o) throws NullPointerException {

			int t1 = this.getTime();
			int t2 = o.getTime();
			if (t1 == t2) {
				return 0;
			} else if (t1 < t2) {
				return -1;
			} else if (t1 > t2) {
				return 1;
			} else {
				throw new NullPointerException("Compared object is null !");
			}
		}
	}
}
