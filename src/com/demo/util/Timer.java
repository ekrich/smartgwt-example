package com.demo.util;

import java.util.Date;

/**
 * Doesn't handle if timer is not started.
 * Not thread safe.
 * 
 * @author ekr
 *
 */
public class Timer {
	 
	long start;
	long stop = 0;
	
	/**
	 * Create and start timer.
	 */
	public Timer() {
		this(true);
	}
	
	/**
	 * Create timer and control whether the time starts
	 * @param start - if false do not start timer, if true same as default constructor.
	 */
	public Timer(boolean start) {
		if (start) {
			start();
		}
	}
	
	/**
	 * Must call to restart timer.
	 */
	public void start() {
		start = getCurrentMillis();
	}
	
	public void stop() {
		stop = getCurrentMillis();
	}
	
	/**
	 * Can only be called once, timer resets.
	 * @return - the elapsed time in millis.
	 */
	public long getElapsedTime() {
		if(stop == 0) {
			stop();
		}
		// timer can be reused
		long elapsed = stop - start;
		stop = 0;
		return elapsed;
	}
	
	/**
	 * Can only be called once, timer resets.
	 * Gets the elapsed time in the following format:
	 * <pre>
	 * 		HH:MM:SS.SSS
	 * </pre>
	 */
	public String getElapsedTimeAsString() {
		return FormatUtil.ms2time(getElapsedTime()).toString();
	}
	
	/**
	 * Start in ISO8601 Date Time format.
	 * ISO format <a href="http://en.wikipedia.org/wiki/ISO_8601">ISO 8601</a>
	 */
	public String getStartTime() {
		return DateUtils.formatDateTimeISO8601(new Date(start));
	}
	
	/**
	 * Start in ISO8601 Date Time format.
	 * ISO format <a href="http://en.wikipedia.org/wiki/ISO_8601">ISO 8601</a>
	 */
	public String getStopTime() {
		return DateUtils.formatDateTimeISO8601(new Date(stop));
	}
	
	private long getCurrentMillis() {
		return System.currentTimeMillis();
	}
}
