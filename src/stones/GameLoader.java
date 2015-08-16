package stones;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import core.Field;

public class GameLoader {

	private static final String GAME_FILE = "stones.bin";

	public GameLoader() {
	}

	public void save(Field field) {
		File file = new File(GAME_FILE);
		try (FileOutputStream out = new FileOutputStream(file); ObjectOutputStream s = new ObjectOutputStream(out)) {
			s.writeObject(field);
			System.out.println("Zapisane");
		} catch (Exception e) {
			e = new Exception("Nieje mozne ulozit subor");
			e.printStackTrace();
			System.err.println(e);
		}
	}

	public Field load() {
		File file = new File(GAME_FILE);
		Field field;
		if (new File("stones.bin").exists()) {

			try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis);) {
				field = (Field) ois.readObject();
				return field;
			} catch (Exception e) {
				e = new Exception("Nieje mozne nacitat subor");
				e.printStackTrace();
				System.err.println(e);
			}
		}
		return null;
	}
}
