package com.alkor.dao;

import com.alkor.model.TableCell;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContentsDao implements IContentsDao {
	private Connection connection;

	public ContentsDao(Connection connection) {
		this.connection = connection;
	}

	private TableCell extractCell(ResultSet rs) throws SQLException {
		return new TableCell()
			.setColIdx(rs.getInt("col_idx"))
			.setRowIdx(rs.getInt("row_idx"))
			.setData(rs.getString("text"));
	}

	@Override
	public List<TableCell> getCells(int tableId) {
		List<TableCell> cells = new ArrayList<>();

		try (PreparedStatement stm = connection.prepareStatement("SELECT row_idx,col_idx,text FROM data WHERE table_id = ?")) {
			stm.setInt(1, tableId);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				cells.add(extractCell(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cells;
	}

	private void clearData(int tableId) {
		try (PreparedStatement stm = connection.prepareStatement("DELETE FROM data WHERE table_id = ?")) {
			stm.setInt(1, tableId);
			stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void updateTableInfo(int rowCount, int colCount, int tableId) {
		try (PreparedStatement stm =
			     connection.prepareStatement("UPDATE tables SET row_count = ?, col_count = ? WHERE id = ?")) {
			stm.setInt(1, rowCount);
			stm.setInt(2, colCount);
			stm.setInt(3, tableId);
			stm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setCells(int tableId, int rowCount, int colCount, List<TableCell> cells) {
		clearData(tableId);
		updateTableInfo(rowCount, colCount, tableId);
		try (PreparedStatement stm =
			     connection.prepareStatement("INSERT data (row_idx, col_idx, text, table_id) VALUES (?, ?, ?, ?)")) {
			for (TableCell cell : cells) {
				stm.setInt(1, cell.getRowIdx());
				stm.setInt(2, cell.getColIdx());
				stm.setString(3, cell.getData());
				stm.setInt(4, tableId);
				stm.addBatch();
			}
			stm.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
