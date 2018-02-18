package task3;

import task3.model.Group;
import task3.service.Finder;
import task3.service.Loader;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Demo {
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static Loader loader = new Loader();
	private static Finder finder = new Finder();

	private static void clearConsole() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	private static void findMenuProcessor() {
		clearConsole();
		System.out.println("Enter group name: ");
		Scanner sc = new Scanner(System.in);
		String groupName = sc.next();
		List<Group> groups = finder.getGroupsByName(groupName);
		for (Group group : groups) {
			System.out.println(group);
		}
		if (groups.size() == 0) {
			System.out.println("No groups found..");
		}
		System.out.println("Press any key to continue..");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void mainMenuProcessor() {
		clearConsole();

		do {
			System.out.println("1. Load file with clearing data");
			System.out.println("2. Find data about group");
			System.out.println("3. Exit");

			Scanner sc = new Scanner(System.in);
			String choice = sc.next();
			switch (choice) {
				case "1":
					loader.loadFileToDb(Paths.get("E:/Work/alevel/mw2/src/main/resources/data.csv"));
					break;
				case "2":
					findMenuProcessor();
					break;
				case "3":
					return;
			}

		} while (true);
	}

	public static void main(String[] args) {
		mainMenuProcessor();
	}
}
