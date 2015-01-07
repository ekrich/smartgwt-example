package com.demo.test.base;

import static java.lang.System.out;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Hibernate setup
 * 
 * @author ekr
 * 
 */
public class TestEmUtilHibernate {

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
    
    Map<String, String> conf = new HashMap<String, String>();
    conf.put("javax.persistence.transactionType", "RESOURCE_LOCAL");
    conf.put("javax.persistence.jtaDataSource", "");
    //conf.put("javax.persistence.excludeUnlistedClasses", "false");
    conf.put("javax.persistence.provider", "org.hibernate.ejb.HibernatePersistence");
    conf.put("javax.persistence.jdbc.driver", TestConfig.JDBC_DRIVER);
    conf.put("javax.persistence.jdbc.url", TestConfig.JDBC_URL);
    conf.put("javax.persistence.jdbc.user", TestConfig.JDBC_USER);
    conf.put("javax.persistence.jdbc.password", TestConfig.JDBC_PASSWORD);
    conf.put("hibernate.archive.autodetection", "class");
    conf.put("hibernate.connection.autocommit", "false");
    // uncomment if desired
    //conf.put("show_sql", "true");
    //conf.put("hibernate.format_sql", "true");
    //conf.put("use_sql_comments", "true");
    conf.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
    final String path = sqlPath + "/" + TestConfig.PU_NAME + "/" + TestConfig.SQL_DIR;
    
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
