package com.demo.util;

import java.io.PrintStream;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class LogHandler extends Handler {
	
	private PrintStream out = System.out;
	
	public LogHandler() {
		// defaults
		setFormatter(new SimpleFormatter());
		setLevel(Level.INFO); 
	}

	@Override
	public void publish(LogRecord record) {
		// ensure that this log record should be logged by this Handler
		if (!isLoggable(record))
			return;

		// Output the formatted data to the file
		out.println(getFormatter().format(record));
	}

	@Override
	public void flush() {
		out.flush();
	}

	@Override
	public void close() throws SecurityException {
		// don't close just flush
		out.flush();
	}
}
