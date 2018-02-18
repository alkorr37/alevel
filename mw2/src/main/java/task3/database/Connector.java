package task3.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static task3.utils.Constants.*;

public class Connector {
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
