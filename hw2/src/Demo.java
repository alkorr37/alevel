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

	private static StringBuilder sb = new StringBuilder();

	private static volatile String input;
	private static String prevInput;

	public static void writeValue() {
		if (input != null && !input.equals(prevInput)) {
			try {
				sb.append(input).append(System.getProperty("line.separator"));
				Files.write(FILE, sb.toString().getBytes(), prevInput != null ? StandardOpenOption.CREATE : StandardOpenOption.TRUNCATE_EXISTING);
				prevInput = input;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(Demo::writeValue, 1, 1, TimeUnit.SECONDS);

		do {
			System.out.print("Please enter line: ");
			input = sc.nextLine();
		} while (!STOP_WORD.equals(input));

		Thread.sleep(1000);
		exec.shutdown();
		exec.awaitTermination(1, TimeUnit.SECONDS);
	}
}
