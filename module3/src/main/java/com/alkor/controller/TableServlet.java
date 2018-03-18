package com.alkor.controller;

import com.alkor.dao.TableDao;
import com.alkor.database.Connector;
import com.alkor.model.Table;
import com.alkor.service.TableService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/table")
public class TableServlet extends HttpServlet {

	private Connection connection;
	private TableService tableService;
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void init() {
		connection = Connector.getConnection();
		this.tableService = new TableService(new TableDao(connection));
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json");
		resp.getWriter().print(mapper.writeValueAsString(tableService.getAllTables()));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json");
		Table table = mapper.readValue(req.getReader(), Table.class);
		resp.getWriter().print(mapper.writeValueAsString(tableService.addTable(table)));
	}
}
