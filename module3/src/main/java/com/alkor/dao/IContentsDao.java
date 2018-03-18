package com.alkor.dao;

import com.alkor.model.TableCell;

import java.util.List;

public interface IContentsDao {
	List<TableCell> getCells(int tableId);

	void setCells(int tableId, int rowCount, int colCount, List<TableCell> cells);
}
