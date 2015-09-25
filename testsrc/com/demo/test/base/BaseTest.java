package com.demo.test.base;

import static java.lang.System.out;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;


/**
 * Extend this class to get default print outs.
 * 
 * @author ekr
 *
 */
public class BaseTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		out.println("*** SetUpBeforeClass");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		out.println("*** TearDownAfterClass");
	}

	@Before
	public void setUp() throws Exception {	
		out.println("*** SetUp: " + getClass().getName());
	}

	@After
	public void tearDown() throws Exception {
		out.println("*** TearDown: " + getClass().getName());
	}

}
