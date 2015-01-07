package com.demo.test.base;

import static java.lang.System.out;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;


/**
 * Extend this class to get an entity manager.
 * Change below to Eclipselink or Hibernate Util.
 * 
 * @author ekr
 *
 */
public class BaseJpaTest {
	protected static EntityManager em;
	protected static JpaImpl jpaImpl;
	enum JpaImpl {hibernate, eclipselink}
	
	@Rule public TestName name = new TestName();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		out.println("*** SetUpBeforeClass");
		jpaImpl = JpaImpl.valueOf(System.getProperty("jpa.impl", JpaImpl.hibernate.name()));
		out.println("JPA Implementation: " + jpaImpl);
    out.println("\tSet VM args in Run Configuration to change, e.g. -Djpa.impl=eclipselink");
    if (jpaImpl == JpaImpl.hibernate) {
	    em = TestEmUtilHibernate.getEntityManager();
    } else {
      em = TestEmUtilEclipselink.getEntityManager();
    }
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		out.println("*** TearDownAfterClass");
		if (jpaImpl == JpaImpl.hibernate) {
      TestEmUtilHibernate.closeEm();
    } else {
      TestEmUtilEclipselink.closeEm();
    }
		TestEmUtilHibernate.closeEm();
	}

	@Before
	public void setUp() throws Exception {	
		out.println();
		out.println("*** SetUp:    " + getClass().getName() + " -> " + name.getMethodName());
	}

	@After
	public void tearDown() throws Exception {
		out.println("*** TearDown: " + getClass().getName()  + " -> " + name.getMethodName());
	}

}
