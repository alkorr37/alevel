package com.alkor.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		FileInputStream fis;
		Properties props = new Properties();

		try {
			fis = new FileInputStream("src/main/resources/config.properties");
			props.load(fis);

			return DriverManager.getConnection("jdbc:mysql://localhost:3306/module3",
				props.getProperty("db.username"), props.getProperty("db.password"));
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
