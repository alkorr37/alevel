package com.alkor.model;

public class Table {
	private long id;
	private String name;
	private int rowCount;
	private int colCount;

	public String getName() {
		return name;
	}

	public Table setName(String name) {
		this.name = name;
		return this;
	}

	public long getId() {
		return id;
	}

	public Table setId(long id) {
		this.id = id;
		return this;
	}


	public int getRowCount() {
		return rowCount;
	}

	public Table setRowCount(int rowCount) {
		this.rowCount = rowCount;
		return this;
	}

	public int getColCount() {
		return colCount;
	}

	public Table setColCount(int colCount) {
		this.colCount = colCount;
		return this;
	}
}
