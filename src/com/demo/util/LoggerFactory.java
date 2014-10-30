package com.demo.util;

import java.util.logging.Logger;

/**
 * Creates a {@link Logger} based on the class that uses this Factory.
 * Returns a logger based on the package name of the class.
 * e.g. com.ray.util.LoggerFactory make a logger com.ray.util
 * @author Hristo, ekr
 *
 */
public class LoggerFactory { 
	// internal logger to see what loggers are being made
	private static Logger LOG;
	static {
		LOG = Logger.getLogger("com.ray.util");
		LOG.setLevel(null); // parent logger level
	}
    
	private LoggerFactory() { 
    }
	
    /**
     * Creates a logger based on the package of the caller's class.
     * 
     * @return - the Logger
     */
    public static Logger make() { 
        Throwable t = new Throwable(); 
        StackTraceElement directCaller = t.getStackTrace()[1];
        String className = directCaller.getClassName();
        int lastDot = className.lastIndexOf(".");
        // default in case the class has no package (default package)
        String packageName = className; 
        if (lastDot != -1) {
        	packageName = className.substring(0, lastDot);
        }
        return makeLogger(packageName);
    }
    
    /**
     * Use if you want to control logger name
     * @param loggerName - usually the package name e.g. com.ray.util
     * @return - the logger
     */
    public static Logger make(String loggerName) { 
    	return makeLogger(loggerName);	
    }

	private static Logger makeLogger(String packageName)
			throws SecurityException {
		LOG.config("LoggerName: " + packageName);
        Logger logger = Logger.getLogger(packageName);
        //use parent logger level
        logger.setLevel(null);
        return logger;
	}
} 
