package com.demo.model;

import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import com.demo.util.LoggerFactory;
import com.demo.util.Timer;

/**
 * Time and log the method.
 * 
 * @author ekr
 *
 */
public class LogInterceptor {
	
	private static Logger LOG = LoggerFactory.make();
	
	@AroundInvoke
	Object log(InvocationContext ctx) throws Exception {
		Object result = null;
		Timer timer = new Timer();
		LOG.info("Job: " + ctx.getMethod().getName() +  " started at " + timer.getStartTime());
		try {
			result = ctx.proceed();
		} catch (Exception e) {
			throw e;
		} finally {
		  timer.stop();
			LOG.info("Job: " + ctx.getMethod().getName() +  " ended at " + timer.getStopTime()
					+ ", Total Time: " + timer.getElapsedTimeAsString());
		}
		return result;
	}
}
