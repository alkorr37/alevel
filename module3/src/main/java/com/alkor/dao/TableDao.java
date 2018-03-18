package com.alkor.dao;

import com.alkor.model.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableDao implements ITableDao {
	private Connection connection;

	public TableDao(Connection connection) {
		this.connection = connection;
	}

	private Table extractTable(ResultSet rs) throws SQLException {
		return new Table()
			.setId(rs.getLong("id"))
			.setName(rs.getString("name"))
			.setRowCount(rs.getInt("row_count"))
			.setColCount(rs.getInt("col_count"));
	}

	@Override
	public int addTable(Table table) {
		try (PreparedStatement stmt = connection.prepareStatement(
			"INSERT INTO tables (name, col_count, row_count) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, table.getName());
			stmt.setInt(2, table.getColCount());
			stmt.setInt(3, table.getRowCount());
			if (stmt.executeUpdate() == 0) {
				throw new SQLException("Couldn't add table");
			}

			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				} else {
					throw new SQLException("Couldn't add table, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public void removeTable(long id) {
		try (PreparedStatement stm = connection.prepareStatement(
			"DELETE FROM tables WHERE id=?")) {
			stm.setLong(1, id);
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Table> getTables() {
		List<Table> tables = new ArrayList<>();

		try (PreparedStatement stm = connection.prepareStatement("SELECT id, name, row_count, col_count FROM tables")) {
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				tables.add(extractTable(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tables;
	}

	public void updateTable(Table todo) {

	}

	@Override
	public Table getTableById(int id) {
		try (PreparedStatement stm = connection.prepareStatement("SELECT id, name, row_count, col_count FROM tables WHERE id = ?")) {
			stm.setInt(1, id);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				return extractTable(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
