package com.demo.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Used by the LoggerFactory to create a custom logging output.
 * 
 * @author ekr 
 *
 */
public class LogFormatter extends Formatter {
	
	private final DateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd kk:mm:ss,SSS");
	
	@SuppressWarnings("unused")
	private final String lineSeparator = System.getProperty("line.separator");
	
	private final String appName;
	/**
	 * Create a log formatter that includes the application name passed in.
	 * 
	 * @param appName - to print in log messages
	 */
	public LogFormatter(String appName) {
		super();
		this.appName = appName;
	}

	@Override
	public String format(LogRecord record) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(appName);
		sb.append("] ");
		sb.append(df.format(new Date(record.getMillis())));
		sb.append(" ");
		sb.append(String.format("%5s", record.getLevel()));
		sb.append(" ");
		//jdk src SimpleFormatter modified
		if (record.getSourceClassName() != null) {
			String className = record.getSourceClassName();
			String shortClassName = className.substring(className.lastIndexOf(".") + 1, className.length());
		    sb.append(shortClassName);
		} else {
		    sb.append(record.getLoggerName());
		}
		sb.append(" - ");
		sb.append(record.getMessage());
		//sb.append(lineSeparator);
		//jdk src
		if (record.getThrown() != null) {
			StringWriter sw = new StringWriter();
		    PrintWriter pw = new PrintWriter(sw);
		    record.getThrown().printStackTrace(pw);
		    pw.close();
			sb.append(sw.toString());
		}
		return sb.toString();
	}
//TODO this is faster than above - can we use it?
//	public class MessageFormatFormatter extends Formatter {
//		 
//		private static final MessageFormat messageFormat = new MessageFormat("{0}[{1}|{2}|{3,date,h:mm:ss}]: {4} \n");
//		
//		public MessageFormatFormatter() {
//			super();
//		}
//		
//		@Override public String format(LogRecord record) {
//			Object[] arguments = new Object[6];
//			arguments[0] = record.getLoggerName();
//			arguments[1] = record.getLevel();
//			arguments[2] = Thread.currentThread().getName();
//			arguments[3] = new Date(record.getMillis());
//			arguments[4] = record.getMessage();
//			return messageFormat.format(arguments);
//		}	
//	 
//	}
}