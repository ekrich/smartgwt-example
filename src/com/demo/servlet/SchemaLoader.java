package com.demo.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.demo.util.LoggerFactory;

public class SchemaLoader {
	
	private static Logger LOG = LoggerFactory.make();
	private Connection conn;
	private ClassLoader cl;

	public SchemaLoader(DataSource ds) {
		try {
			this.conn = ds.getConnection();
			LOG.info("Connection autocommit: " + conn.getAutoCommit());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		this.cl = Thread.currentThread().getContextClassLoader();
	}
	
	public void load(String fileName) {
		LOG.info("Loading: " +  fileName);
		Statement stmt = getStatement();
		BufferedReader br = getBufferedReader(fileName);
		try {
			String str;
			while((str = br.readLine()) != null) {
				try {
					stmt.executeUpdate(str);
				} catch (SQLException e) {
					LOG.info(e.getMessage());
				}	
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
	
	private Statement getStatement() {
		try {
			return conn.createStatement();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private BufferedReader getBufferedReader(String fileName) {
		InputStream is = cl.getResourceAsStream(fileName);
		return new BufferedReader(new InputStreamReader(is));
	}

}
