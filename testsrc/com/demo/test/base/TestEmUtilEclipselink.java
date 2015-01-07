package com.demo.test.base;

import static java.lang.System.out;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * When upgrading to eclipselink change references form toplink to eclipselink
 * except where noted in the file below (new standard javax.persistence.xxx properties).
 * 
 * @author ekr
 *
 */
public class TestEmUtilEclipselink {
	
	private static EntityManager em;
	private static EntityManagerFactory emf;
	public static boolean multiTest = false;
	
	static final EntityManager getEntityManager() {
		if (em != null) {
			out.println("*** Returning existing EntityManager.");
			return em;
		}
		out.println("*** Creating EntityManager.");
		// default path to generate workspace
		String sqlPath = System.getProperty("alltests.sqlpath", "C:/workspace");
		out.println("Workspace path: " + sqlPath);
		out.println("\tIf not correct set path to your workspace in VM args, e.g. -Dalltests.sqlpath=C:/<your workspace path>");
		//Logger.getLogger("").setLevel(Level.FINEST);
		Map<String,String> conf = new HashMap<String,String>();
		// http://www.oracle.com/technetwork/middleware/ias/toplink-jpa-extensions-094393.html
		// javax.persistence.transactionType
		conf.put("javax.persistence.transactionType", "RESOURCE_LOCAL");
	    // OFF, SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST 
	    conf.put("eclipselink.logging.level", "FINEST");
	    // for the next 4 properties change toplink to javax.persistence
	    conf.put("javax.persistence.jdbc.driver", TestConfig.JDBC_DRIVER);
	    conf.put("javax.persistence.jdbc.url", TestConfig.JDBC_URL);
	    conf.put("javax.persistence.jdbc.user", TestConfig.JDBC_USER);
//	    SecurableObjectHolder holder = new SecurableObjectHolder();
//	    String encryptedPassword = holder.getSecurableObject().encryptPassword(TestConfig.JDBC_PASSWORD);  
	    conf.put("javax.persistence.jdbc.password", TestConfig.JDBC_PASSWORD);
	    // none(for deploy), create-tables, drop-and-create-tables
	    conf.put("eclipselink.ddl-generation", "drop-and-create-tables");
    	// both, database or sql-script
    	conf.put("eclipselink.ddl-generation.output-mode", "sql-script");
    	conf.put("eclipselink.application-location",
    		sqlPath + "/" + TestConfig.PU_NAME + "/" + TestConfig.SQL_DIR);
    	conf.put("eclipselink.create-ddl-jdbc-file-name","create.sql");
    	conf.put("eclipselink.drop-ddl-jdbc-file-name","drop.sql");
    	conf.put("eclipselink.logging.exceptions","true");
    	conf.put("eclipselink.weaving","true");
    	//conf.put("eclipselink.target-database","Oracle11");
    	//conf.put("eclipselink.target-server","WebLogic_10");
    	// append a semicolon at the end of each sql command
    	//conf.put("eclipselink.ddl-generation.table-creation-suffix", ";");
    	// print sql control: false (show param values) true (show ?)
    	conf.put("eclipselink.jdbc.bind-parameters","false");
    	//conf.put("exclude-unlisted-classes","false");
    	try {
			 emf = Persistence.createEntityManagerFactory(TestConfig.PU_NAME, conf);
			
			out.println("*** EMF open: " + emf.isOpen());
		    em = emf.createEntityManager();
		    out.println("*** EM open: " + em.isOpen());
    	} catch (RuntimeException e) {
    		e.printStackTrace();
    		Throwable t = e.getCause();
    		if (t != null) {
    			t.printStackTrace();
    		}
    		throw e;
    	} 
    	return em;
	}
	
	public static final void closeEm() {
		if (!multiTest) {
			if (em != null && em.isOpen()) {
				out.println("*** Closing EntityManagerFactory.");
				if (emf != null && emf.isOpen()) { 
					emf.close();	
				}
				out.println("*** EMF open: " + emf.isOpen());
				out.println("*** EM open: " + em.isOpen());
				emf = null;
				em = null;
			}
		}
	}
}
