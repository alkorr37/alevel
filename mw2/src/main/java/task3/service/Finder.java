package task3.service;

import task3.database.Connector;
import task3.database.GroupService;
import task3.model.Group;

import java.sql.Connection;
import java.util.List;

public class Finder {
	private Connection connection;

	public Finder() {
		this.connection = Connector.getConnection();
	}

	public List<Group> getGroupsByName(String name) {
		GroupService groupService = new GroupService(connection);
		return groupService.getByName(name);
	}
}
