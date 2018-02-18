package task3.database;

import task3.model.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupService {
	private Connection connection;

	public GroupService(Connection connection) {
		this.connection = connection;
	}

	public void reCreateTable() throws SQLException {
		Statement statement = connection.createStatement();
		statement.execute("DROP TABLE `group`");
		statement.execute("CREATE TABLE `group` (" +
			"  id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE," +
			"  name VARCHAR(3) NOT NULL," +
			"  pupilsCount INT(11) DEFAULT NULL," +
			"  teacherName VARCHAR(255) DEFAULT NULL," +
			"  PRIMARY KEY (id)" +
			" )" +
			" ENGINE = INNODB;");
	}

	public List<Group> getByName(String name) {
		List<Group> result = new ArrayList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(
				"SELECT name, pupilsCount, teacherName FROM groupdb.group WHERE name LIKE ?"
			);
			statement.setString(1, name + "%");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				result.add(new Group(
					rs.getString("name"),
					rs.getInt("pupilsCount"),
					rs.getString("teacherName")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void addGroup(Group group) {
		try {
			PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO `group` (name, pupilsCount, teacherName) VALUES (?, ?, ?)"
			);
			statement.setString(1, group.getName());
			statement.setInt(2, group.getPupils());
			statement.setString(3, group.getTeacherName());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
