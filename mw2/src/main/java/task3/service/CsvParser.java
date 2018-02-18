package task3.service;

import task3.model.Group;

public class CsvParser {
	public static Group getGroup(String csvLine) {
		String[] lines = csvLine.split(",");
		return new Group(lines[0], Integer.parseInt(lines[1]), lines[2]);
	}
}
