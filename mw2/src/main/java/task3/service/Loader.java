package task3.service;

import task3.database.Connector;
import task3.database.GroupService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

public class Loader {
	private Connection connection;

	public Loader() {
		connection = Connector.getConnection();
	}

	public void loadFileToDb(Path path) {
		GroupService groupService = new GroupService(connection);
		try {
			groupService.reCreateTable();
			Files.readAllLines(path).stream().map(CsvParser::getGroup).forEach(groupService::addGroup);

		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
}
