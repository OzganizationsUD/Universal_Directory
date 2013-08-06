package com.ud.database.connect;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class DataBaseConnect {
	@Autowired
	private DataSource ds;
	private Connection con = null;
	private Statement stmt = null;
   
	public DataSource getDs() {
		return ds;
	}

	public void setDs(DataSource ds) {
		this.ds = ds;
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	public void init() throws SQLException {
		
		con = ds.getConnection();
		if (con != null)
			System.out.println("Driver load!!!");
		else
			System.out.println("Driver not found!!!");
		stmt = (Statement) con.createStatement();

		if (stmt != null)
			System.out.println("Connect: ");
		else
			System.out.println("NO connect: ");
	}
}
