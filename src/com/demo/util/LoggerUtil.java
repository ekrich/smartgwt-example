package com.demo.util;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class LoggerUtil {
	/**
	 * Standard Web App JNDI Naming Prefix
	 */
	public static final String NAMING_ENV_PREFIX = "java:comp/env/";
	/**
	 * Standard name to put in web.xml for one logger hierarchy
	 */
	public static final String LOG_LEVEL_NAME = "java.util.logging.level";
	
	private LoggerUtil(){}
	
	/**
	 * Add the following to the web.xml<br />
	 *
	 * <pre>
	 * &lt;env-entry&gt;
	 *    &lt;description&gt;Set the application logging level to one of the 
	 *    following: SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST, ALL, OFF&lt;/description&gt;
	 *    &lt;env-entry-name&gt;java.util.logging.level&lt;/env-entry-name&gt;
	 *    &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
	 *    &lt;env-entry-value&gt;INFO&lt;/env-entry-value&gt;
	 * &lt;/env-entry&gt;
	 * </pre>
	 * 
	 * @param jndiLocation - for default described above use no arg version
	 * 				of this method
	 * @return - the logging level
	 */
	public static Level getLoggerLevel(String jndiLocation) {
		String logLevel = null;
		try {
			Context ctx = new InitialContext();
			logLevel = (String)ctx.lookup(jndiLocation);
		} catch (NamingException e){
			String msg = "JNDI lookup failed for " + jndiLocation 
				+ ". Please check web.xml for env-entry";
			throw new IllegalStateException(msg, e);
		}	
		// lookup the level from the string representation
		return Level.parse(logLevel);
	}
	/**
	 * Lookup logger level from default location, NAMING_ENV_PREFIX + LOG_LEVEL_NAME.
	 * 
	 * @return - the logged level
	 */
	public static Level getLoggerLevel() {
		return getLoggerLevel(NAMING_ENV_PREFIX + LOG_LEVEL_NAME);
	}
	
	/**
	 * Set the logger to the level specified
	 * 
	 * @param logger - the logger to set the level on
	 * @param level - the level desired for output of the logger
	 */
	public static void setLoggerLevel(Logger logger, Level level) {		
		// set the level of the logger
		logger.info("Logger: " + logger.getName() + ", Level: " + level);
		logger.setLevel(level);	
	}
	
	/**
	 * Set the logger to the level specified in web.xml
	 * 
	 * @param logger - the logger to set the level on
	 * @param level - the level desired for output of the logger
	 */
	public static void setLoggerLevel(Logger logger) {		
		// set the level of the logger
		Level level = getLoggerLevel();
		setLoggerLevel(logger, level);	
	}
	
	/**
	 * Initializes the logger with a custom formatter and handler.
	 * 
	 * @param appName - if appName is face the log entry starts with [face]
	 * @param logger - the logger to set up
	 */
	public static void initLogger(String appName, Logger logger) {
		// TODO need more research http://www.javalobby.org/java/forums/t18515.html
		//http://publib.boulder.ibm.com/infocenter/wasinfo/v6r0/index.jsp?topic=%2Fcom.ibm.websphere.express.doc%2Finfo%2Fexp%2Fae%2Frtrb_createformatter.html
		// should be able to use property file like log4j
		// http://www.javapractices.com/topic/TopicAction.do?Id=143
		// ClassLoader cl = Thread.currentThread().getContextClassLoader();
		// InputStream is = cl.getResourceAsStream("logging.properties");
		// LogManager.getLogManager().readConfiguration(is);
		
		// set up custom handler
		Handler handler = new LogHandler();
		
		// set up custom formatter
		Formatter formatter = new LogFormatter(appName);
		
		// add formatter and/or filter to handler
		handler.setFormatter(formatter);
		
		// add handler to logger - don't if this class is not reloaded - logger is static
		int numHandlers = logger.getHandlers().length;
		if (numHandlers == 0) {
			logger.addHandler(handler);
		}
		
		// avoid sending the logs upstream
		logger.setUseParentHandlers(false);
		
		logger.info("Logger initialized: " + logger.getName());
	}
	
	/**
	 * Set loggers with all the same level
	 * 
	 * @param appName - the app name to prefix to log [appName]
	 * @param level - level to set logger
	 * @param loggers - list of logger(s)
	 */
	public static void initLoggersWithLevel(String appName, Level level, Logger... loggers) {
		for(Logger logger : loggers) {
			initLogger(appName, logger); // do first for formatting
			setLoggerLevel(logger, level);		
		}	
	}
	/**
	 * Init loggers with the default level described in {@link #getLoggerLevel()}
	 * 
	 * @param appName - the app name to prefix to log [appName]
	 * @param loggers - list of logger(s)
	 */
	public static void initLoggers(String appName, Logger... loggers) {
		initLoggersWithLevel(appName, getLoggerLevel(), loggers);
	}

}
