package com.demo.test.base;

import static java.lang.System.out;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Get a JDBC Connection
 * 
 * @author ekr
 *
 */
public class TestJdbcUtil {
	
	private static Connection conn;
	public static boolean multiTest = false;
	static {
		try {
			Class.forName(TestConfig.JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			out.println("*** Problem loading driver during initialization.");
			e.printStackTrace();
		}
	}
	
	static final Connection getConnection() {
		if (conn != null) {
			out.println("*** Returning existing Connection.");
			return conn;
		}
		out.println("*** Creating Connection.");
		try {
			conn = DriverManager.getConnection(TestConfig.JDBC_URL, TestConfig.JDBC_USER, TestConfig.JDBC_PASSWORD);
		} catch (SQLException e) {
			out.println("Unable to create connection.");
			e.printStackTrace();
		}
    	return conn;
	}
	
	public static final void closeConn() {
		if (!multiTest) {
			try {
				if (conn != null && !conn.isClosed()) {
					out.println("*** Closing Connection.");
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				out.println("Problem closing connection.");
				e.printStackTrace();
			}
		}
	}
}
