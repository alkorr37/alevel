package task2;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Demo {
	private static volatile boolean isSet = true;
	private static volatile boolean isEnded;

	public static void main(String[] args) {
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);

		pool.scheduleAtFixedRate(() -> {
			if (isEnded) {
				System.out.println(System.lineSeparator() + "'Game' over");
				pool.shutdownNow();
				return;
			}
			if (isSet) {
				isSet = false;
				System.out.println("bamm..");
			} else {
				isEnded = true;
			}
		}, 0 , 2, TimeUnit.SECONDS);

		pool.scheduleAtFixedRate(() -> {
			Scanner sc = new Scanner(System.in);
			if (!isEnded && sc.hasNext()) {
				isSet = true;
			}
		}, 0 , 2, TimeUnit.SECONDS);
	}
}
