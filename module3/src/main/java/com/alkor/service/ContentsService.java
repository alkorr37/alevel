package com.alkor.service;

import com.alkor.dao.IContentsDao;
import com.alkor.dao.ITableDao;
import com.alkor.model.Table;
import com.alkor.utils.JsonHelper;

public class ContentsService {
	private IContentsDao contentsDao;
	private ITableDao tableDao;

	public ContentsService(ITableDao tableDao, IContentsDao contentsDao) {
		this.contentsDao = contentsDao;
		this.tableDao = tableDao;
	}

	public String[][] getTableData(int id) {
		Table table = tableDao.getTableById(id);
		return JsonHelper.generateJson(table.getRowCount(), table.getColCount(), contentsDao.getCells(id));
	}

	public void removeTable(int id) {
		tableDao.removeTable(id);
	}

	public void setTableData(int id, String[][] data) {
		int rowCount = data.length;
		int colCount = 0;
		if (data.length > 0) {
			colCount = data[0].length;
		}

		contentsDao.setCells(id, rowCount, colCount, JsonHelper.generateTableCells(data));
	}
}
