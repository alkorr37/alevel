package com.alkor.utils;

import com.alkor.model.TableCell;

import java.util.ArrayList;
import java.util.List;

public class JsonHelper {
	public static String[][] generateJson(int rowCount, int colCount, List<TableCell> cells) {
		String[][] data = new String[rowCount][colCount];
		cells.forEach(cell -> data[cell.getRowIdx()][cell.getColIdx()] = cell.getData());
		return data;
	}

	public static List<TableCell> generateTableCells(String[][] data) {
		List<TableCell> result = new ArrayList<>();
		for (int i = 0; i < data.length; i++) {
			String[] curRow = data[i];
			for (int j = 0; j < curRow.length; j++) {
				result.add(new TableCell()
					.setRowIdx(i)
					.setColIdx(j)
					.setData(data[i][j])
				);
			}
		}
		return result;
	}
}
