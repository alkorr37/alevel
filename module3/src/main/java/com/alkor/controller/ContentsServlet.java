package com.alkor.controller;

import com.alkor.dao.ContentsDao;
import com.alkor.dao.TableDao;
import com.alkor.database.Connector;
import com.alkor.service.ContentsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/table/*")
public class ContentsServlet extends HttpServlet {
	private Connection connection;
	private ContentsService contentsService;
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
	public void init() throws ServletException {
		connection = Connector.getConnection();
		this.contentsService = new ContentsService(new TableDao(connection), new ContentsDao(connection));
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		String[][] data = contentsService.getTableData(Integer.parseInt(pathInfo.split("/")[1]));
		resp.setContentType("application/json");
		resp.getWriter().print(mapper.writeValueAsString(data));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		String[][] data = mapper.readValue(req.getReader(), String[][].class);
		contentsService.setTableData(Integer.parseInt(pathInfo.split("/")[1]), data);
		resp.setStatus(HttpServletResponse.SC_OK);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		contentsService.removeTable(Integer.parseInt(pathInfo.split("/")[1]));
		resp.setStatus(HttpServletResponse.SC_OK);
	}
}
