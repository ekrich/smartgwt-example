package com.demo.model;

import java.security.Principal;
import java.util.logging.Logger;

import javax.ejb.SessionContext;

public class BeanUtil {
	
	private static Logger LOG = com.demo.util.LoggerFactory.make();
	
	private static final String ANONYMOUS = "anonymous";
	
	private BeanUtil(){}
	
	/**
	 * Needed to return a consistent "anonymous' if no
	 * J2EE security or the user name.
	 * 
	 * @param ctx - the EJB session context
	 * @return - userName or anonymous
	 */
	public static String getCallerName(SessionContext ctx) {
		Principal caller = ctx.getCallerPrincipal();
		String callerName = caller.getName();
		if (ANONYMOUS.equalsIgnoreCase(callerName)) {
		  // Glassfish, OC4J, and JBoss
			callerName = callerName.toLowerCase();
		} else if (callerName.contains(ANONYMOUS)) {
			// <anonymous> WebLogic
			callerName = ANONYMOUS;
		} else {
			// log others to trap
			LOG.fine("Caller name: " + callerName);
		}		
		return callerName;
	}

}
