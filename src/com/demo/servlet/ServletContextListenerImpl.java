package com.demo.servlet;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import com.demo.entity.User;
import com.demo.model.SupplyBean;
import com.demo.model.UserBean;
import com.demo.util.LoggerFactory;
import com.demo.util.LoggerUtil;

/**
 * Semi generic application listener to run startup and shutdown code.
 * Add the class and description your web.xml if needed.
 * <code>
 * 		&lt;listener&gt;
 *			&lt;description&gt;Demo Application Listener&lt;/description&gt;
 *			&lt;listener-class&gt;com.demo.servlet.ServletContextListenerImpl&lt;/listener-class&gt;
 *		&lt;/listener&gt;
 * </code>
 * 
 * @author ekr
 *
 */
@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
	private static final String APP_NAME = "demo";
	
	// Root logger for this application
	private static final Logger LOG = LoggerFactory.make("com.demo");
	
	// demo only VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
	@Resource(lookup = "java:jboss/datasources/ExampleDS")
	private DataSource dataSource;
	
	@Inject
	SupplyBean supplyBean;
	
	@Inject
	UserBean userBean;
	
	// demo only VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV

	/**
	 * Called when application is shutdown.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// call shutdown methods here
		
		LOG.info(APP_NAME + " web context shutdown complete.");
	}

	/**
	 * Called when application is started.
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		@SuppressWarnings("unused")
		ServletContext context = event.getServletContext();	
		// do before logging
		LoggerUtil.initLoggers(APP_NAME, LOG);
		LOG.info(APP_NAME + " web context starting ...");
		// call startup methods here
		// demo only ----------------------------------------------------------------
		SchemaLoader schemaLoader = new SchemaLoader(dataSource);
		schemaLoader.load("com/demo/data/drop.sql");
		schemaLoader.load("com/demo/data/create.sql");
		schemaLoader.load("com/demo/data/add-records.sql");
		for (User user : userBean.findActiveUsers()) {
			LOG.info(user.toString());
		}
		// load the application data
		supplyBean.loadSupplyCategories();
		
		
		// demo only ----------------------------------------------------------------
		LOG.info(APP_NAME + " web context initialized.");
	}

}
