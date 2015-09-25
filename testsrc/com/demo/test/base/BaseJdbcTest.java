package com.demo.test.base;

import static java.lang.System.out;

import java.sql.Connection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;


/**
 * Extend this class to get an entity manager.
 * 
 * @author ekr
 *
 */
public class BaseJdbcTest {
	protected static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		out.println("*** SetUpBeforeClass");
	    conn = TestJdbcUtil.getConnection();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		out.println("*** TearDownAfterClass");
		TestJdbcUtil.closeConn();
	}

	@Before
	public void setUp() throws Exception {	
		out.println();
		out.println("*** SetUp: " + getClass().getName());
	}

	@After
	public void tearDown() throws Exception {
		out.println("*** TearDown: " + getClass().getName());
	}

}
