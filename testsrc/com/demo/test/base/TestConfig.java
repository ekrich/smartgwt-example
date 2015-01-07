package com.demo.test.base;

/**
 * Fill in information and check in with your project.
 * 
 * @author ekr
 *
 */
public class TestConfig {
	private TestConfig(){}
	public static final String PU_NAME = "demo";
	public static final String SQL_DIR = "src/com/demo/data";
	public static final String JDBC_USER = "facetadm";
	public static final String JDBC_PASSWORD = "p3pt0";
	public static final String JDBC_URL = "jdbc:oracle:thin:@//devrac01-v.sas.ray.com:1521/n3_serv_dev";
	public static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
}
