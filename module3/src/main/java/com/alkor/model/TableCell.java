package com.alkor.model;

public class TableCell {
	private int rowIdx;
	private int colIdx;
	private String data;

	public int getRowIdx() {
		return rowIdx;
	}

	public TableCell setRowIdx(int rowIdx) {
		this.rowIdx = rowIdx;
		return this;
	}

	public int getColIdx() {
		return colIdx;
	}

	public TableCell setColIdx(int colIdx) {
		this.colIdx = colIdx;
		return this;
	}

	public String getData() {
		return data;
	}

	public TableCell setData(String data) {
		this.data = data;
		return this;
	}
}
