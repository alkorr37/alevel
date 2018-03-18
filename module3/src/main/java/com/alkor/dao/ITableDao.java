package com.alkor.dao;

import com.alkor.model.Table;

import java.util.List;

public interface ITableDao {
	public int addTable(Table table);

	public void removeTable(long id);

	public List<Table> getTables();

	public Table getTableById(int id);

	public void updateTable(Table table);
}
