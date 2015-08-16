package stones;

public class DatabaseSetting {

	public static final String URL = "jdbc:mysql://localhost/stones";
	public static final String USER = "root";
	public static final String PASSWORD = "root";

	public static final String QUERY_CREATE_BEST_TIMES = "CREATE TABLE player_time (name VARCHAR(128) NOT NULL, best_time INT NOT NULL)";
	public static final String QUERY_ADD_BEST_TIME = "INSERT INTO player_time (name, best_time) VALUES (?, ?)";
	public static final String QUERY_SELECT_BEST_TIMES = "SELECT name, best_time FROM player_time";

	private DatabaseSetting() {
	}

}
