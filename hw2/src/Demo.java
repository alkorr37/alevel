import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Demo {
	private static final Path FILE = Paths.get("output.txt");
	private static final String STOP_WORD = "quit";
	private static ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
	private static volatile String input;
	private static String prevInput;

	public static void writeValue() {
		if (input != null && !input.equals(prevInput)) {
			try {
				Files.write(FILE, input.getBytes(), FILE.toFile().exists() ? StandardOpenOption.TRUNCATE_EXISTING : StandardOpenOption.CREATE_NEW);
				prevInput = input;
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (STOP_WORD.equals(input)) {
				exec.shutdown();
			}
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		exec.scheduleAtFixedRate(Demo::writeValue, 1, 1, TimeUnit.SECONDS);

		do {
			System.out.print("Please enter line: ");
			input = sc.nextLine();
		} while (!STOP_WORD.equals(input));
	}
}
