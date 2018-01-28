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

	private static volatile StringBuilder sb = new StringBuilder();
	private static String prevInput;

	public static void writeValue() {
		String curValue = sb.toString();
		if (!curValue.equals(prevInput)) {
			try {
				Files.write(FILE, curValue.getBytes(), prevInput != null ? StandardOpenOption.CREATE : StandardOpenOption.TRUNCATE_EXISTING);
				prevInput = curValue;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(Demo::writeValue, 1, 1, TimeUnit.SECONDS);

		String input;
		do {
			System.out.print("Please enter line: ");
			input = sc.nextLine();
			sb.append(input).append(System.lineSeparator());
		} while (!STOP_WORD.equals(input));

		exec.shutdown();
		exec.awaitTermination(1, TimeUnit.SECONDS);
	}
}
