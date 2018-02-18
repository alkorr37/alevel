package task1;

public class Demo {
	public static void main(String[] args) {
		ArrayList<Integer> arrayList = new ArrayList<>();
		System.out.println("Inititial..");
		System.out.println(arrayList);

		System.out.println();

		System.out.println("Adding to tail..");
		System.out.println("Index: " + arrayList.add(1));
		System.out.println(arrayList);

		System.out.println();

		System.out.println("Adding to concrete position (1)..");
		arrayList.add(16, 15);
		System.out.println(arrayList);

		System.out.println();

		System.out.println("Adding to concrete position (2)..");
		arrayList.add(5, 4);
		System.out.println(arrayList);

		System.out.println();

		System.out.println("Adding to concrete position (3)..");
		arrayList.add(0, 0);
		System.out.println(arrayList);

		System.out.println();

		System.out.println("Getting elem..");
		System.out.println("Index 17: " + arrayList.get(17));

		System.out.println();

		System.out.println("Removing elem..");
		System.out.println("Index 1: " + arrayList.remove(1));
		System.out.println(arrayList);
	}
}
