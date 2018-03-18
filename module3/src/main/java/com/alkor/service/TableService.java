package com.alkor.service;

import com.alkor.dao.ITableDao;
import com.alkor.model.Table;

import java.util.List;

public class TableService {
	private ITableDao tableDao;

	public TableService(ITableDao tableDao) {
		this.tableDao = tableDao;
	}

	public List<Table> getAllTables() {
		return tableDao.getTables();
	}

	public int addTable(Table table) {
		return tableDao.addTable(table);
	}
}
