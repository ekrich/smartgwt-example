package com.demo.util; 
 
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Logger;



/**
 * Common class of static methods to control formatting of output variables such
 * as Strings, Dates etc..
 */
public class FormatUtil {
    private static final Logger LOG = Logger.getLogger(FormatUtil.class.getName());

    /** DateFormat for "MM/dd/yyyy". */
    public static DateFormat formatSlashedMmDdYyyy() {
        return new SimpleDateFormat("MM/dd/yyyy");
    }

    /** DateFormat for "MM/dd/yy". */
    public static DateFormat formatSlashedMmDdYy() {
        return new SimpleDateFormat("MM/dd/yy");
    }

    /** DateFormat for "YYYYMMDD". */
    public static DateFormat formatYyyyMmDd() {
        return new SimpleDateFormat("yyyyMMdd");
    }

    /** DateFormat for "YY  MMDD". */
    public static DateFormat formatSpacedYyMmDd() {
        return new SimpleDateFormat("yy  MMdd");
    }

    /** DateFormat for "YYYYMM". */
    public static DateFormat formatYyyyMm() {
        return new SimpleDateFormat("yyyyMM");
    }

    /** DateFormat for "YYMMDD". */
    public static DateFormat formatYyMmDd() {
        return new SimpleDateFormat("yyMMdd");
    }

    /** DateFormat for "MMM 'YY" */
    public static DateFormat formatMmmYy() {
        return new SimpleDateFormat("MMM ''yy");
    }

    /** DateFormat for "MMM" */
    public static DateFormat formatMmm() {
        return new SimpleDateFormat("MMM");
    }

    /** The end-of-line for this platform. */
    public static final String EOL = System.getProperty("line.separator");

  /**
   * Parse a String into a Date.
   * @param sdf - the DateFormat used for parsing, lenient.
   * @param dateStr - the input string.
   * @return - the date
   * @throws RuntimeException for parsing exceptions.
   */
	public static Date parseDate(DateFormat sdf, String dateStr) {
	  try {
      return sdf.parse(dateStr);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
	}
	
	public static Date parseDateSlashedMmDdYyyy(String dateStr) {
	 return parseDate(formatSlashedMmDdYyyy(), dateStr);
	}

	public static String formatDate(String formatString, Date date) {
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		if (date == null) {
			return "";
		}
		return format.format(date);
	}


	private static SimpleDateFormat getTimestampFormat() {
		String defaultTimestampFormat = "MM/dd/yyyy HH:mm:ss";		
		return new SimpleDateFormat(defaultTimestampFormat);
	}
	/**
	 * Formats a date object based on the format setting
	 * in compass.timestampFormat
	 * @params date date object to format
	 */ 
	public static String formatTimestamp(Date date) {
		if (date == null) {
			return "";
		}
		return getTimestampFormat().format(date);
	}

	
	/**
	 * Returns a date formatted as "YYYYMMDDHHMI"
	 * @param date
	 * @return
	 */
	public static String simpleDateAndTime(Date date){
		StringBuffer sb = new StringBuffer(8);
		
		if(date != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int year 		= cal.get(Calendar.YEAR);
			int month 		= cal.get(Calendar.MONTH) + 1;
			int day 		= cal.get(Calendar.DAY_OF_MONTH);
			int hour		= cal.get(Calendar.HOUR_OF_DAY);
			int minute		= cal.get(Calendar.MINUTE);
			sb.append("" + year).append(format2(month)).append(format2(day))
			  .append(format2(hour)).append(format2(minute));
		}else{
			sb.append("YYYYMMDDHHMI");
		}
		return sb.toString();
	}
	
	
	/**
	 * Converts a time interval expressed in milliseconds to the following format:
	 * <PRE>
	 * 		HH:MM:SS.SSS
	 * </PRE>
	 * @param time the time interval as a long
	 * @return a String representation of the time interval 
	 */
	public static StringBuffer ms2time(long time){
		long msPerMinute = 60 * 1000;
		long msPerHour = 60 * msPerMinute;
		StringBuffer sb = new StringBuffer(15);
		long hours = time / msPerHour;
		long ms = time % msPerHour;
		long minutes = ms / msPerMinute;
		ms = ms % msPerMinute;
		long seconds = ms / 1000;
		ms = ms % 1000;

		if(hours != 0){
			
			if(hours < 10)
				sb.append('0');
			sb.append(hours).append(':');
		}else
			sb.append("00:");

		if(minutes != 0){
			if(minutes < 10)
				sb.append('0');
			sb.append(minutes).append(':');
		}else
			sb.append("00:");
		
		if(seconds < 10.0)
			sb.append('0');
		sb.append(seconds).append('.');
		
		if(ms < 10)
			sb.append("00");
		else if(ms < 100)
			sb.append('0');
		return sb.append(ms);
	}
	
	/**
	 * Formats an number into a string with commas.
	 * @param n the number to be formatted as a long (or int)
	 * @return the integer formatted with commas
	 */
	public static String commatize(long n){
		boolean isNegative = false;
		if (n < 0) {
			isNegative = true;
			n *= -1;
		}
		StringBuffer sb = new StringBuffer("" + n);
		for(int i = sb.length() - 3; i > 0; i -= 3){
			sb.insert(i, ',');
		}
		return (isNegative ? "-" : "") + sb.toString();
	}
	
	/**
	 * Formats a number (long or int) to a currency String representation as follows:
	 * <PRE>
	 *     $[dollar amount grouped with commas].[cents amount leading filled with zeroes to two places]
	 * </PRE>
	 * @return formatted amount as a String
	 */
	public static String format$(long n){
		return ((n != 0L) ? ("$" + commatize(n / 100) + "." + format2(n % 100)) : " ");
	}

	/**
	 * Converts a currency String into a number representing the number of cents in the amount.
	 * Deals with leading dollar sign ('$') and decimal point.  When decimal point is not in
	 * original string, a whole dollar amount is assumed and the specified amount is multiplied
	 * by 100.  When the currency string fraction part contains more than two digits, the amount
	 * is truncated to two digits after the decimal point before conversion.  There is no attempt
	 * to round franctions of a cent.
	 * @param sin the currency string to be unformatted
	 * @return the unformatted value as a long representing the total number of cents in sin
	 * @throws NumberFormatException if sin does not yield a parsable long.
	 */
	public static long unFormat$(String sin){
		
		if(sin == null || sin.trim().length() == 0){
			return 0L;
		}
		String s = (sin.startsWith("$")) ? sin.substring(1) : sin;
		int dot = s.indexOf('.');
		int fLen = s.length() - dot;
		
		
		if(dot < 0){
			//no decimal point, assume dollar value
			s = s.substring(0, dot) + "00";
		}else if(fLen == 1){
			//decimal point at end, delete it
			s = s.substring(0, dot);
		}else if(fLen == 2){
			//one digit after decimal point
			s = s.substring(0, dot) + s.substring(dot + 1) + "0";
		}else if(fLen > 2){
			//more than one digit after decimal point
			s = s.substring(0, dot) + s.substring(dot + 1, dot + 3);
		}
		
		return Long.parseLong(s);
			
	}
	
	/**
	 * Utility method to add leading zeroes to format integers to two characters.
	 * @param i the integer to be formatted
	 * @return the formatted integer in a StringBuffer
	 */
	public static StringBuffer format2(long i){
		StringBuffer sb = new StringBuffer(2);
		if(i < 10 ){
			sb.append('0');
		} 
		return sb.append(i);
	}

	/**
	 * Utility method to add leading zeroes to format integers to two characters.
	 * @param i the integer to be formatted
	 * @return the formatted integer in a StringBuffer
	 */
	public static StringBuffer format3(int i){
		StringBuffer sb = new StringBuffer(2);
		if(i < 100){
			sb.append('0');
		}
		if(i < 10 ){
			sb.append('0');
		} 
		return sb.append(i);
	}
	
	/**
	 * Adds leading characters to a String as required to achieve a specified width.
	 * @param c the pad character to use
	 * @param width the desired width
	 * @param value unformatted String
	 * @return a String of the specified length (width)
	 */
	public static String fillStart(char c, int width, String value){
		StringBuffer sb = new StringBuffer().append(value);
		while(sb.length() < width){
			sb.insert(0, c);
		}
		return sb.toString();
	}
	
	/**
	 * Adds trailing characters to a String as required to achieve a specified width.
	 * @param c the pad character to use
	 * @param width the desired width
	 * @param value unformatted String
	 * @return a String of the specified length (width)
	 */
	public static String fillEnd(char c, int width, String value){
		StringBuffer sb = new StringBuffer().append(value);
		while(sb.length() < width){
			sb.append(c);
		}
		return sb.toString();
	}

    /**
     * Adds trailing characters to a String as required to achieve a specified width.
     * If the String is over the specified width, truncate to the width.
     * @param c the pad character to use
     * @param width the desired width
     * @param value unformatted String
     * @return
     */
    public static String fillEndOrTruncate(char c, int width, String value) {
        return fillEnd(c, width, value).substring(0, width);
    }

	/**
	 * Centers a String by padding enough of a specified character before and after
	 * the String to fill it to a specified width.
	 * @param c the pad character
	 * @param width the desired width
	 * @param value the initial String
	 * @return
	 */
	public static String fillEnds(char c, int width, String value){
		return fillEnd(c, width, (fillStart(c, ((width + value.length()) / 2), value)));
	}
	
	/**
	 * Centers a String by padding enough of a specified character before and after
	 * the String to fill it to a specified width.
	 * @param c the pad character
	 * @param width the desired width
	 * @param value the initial String
	 * @return
	 */
	public static String center(char c, int width, String value){
		return fillEnds(c, width, value);
	}

	/**
	 * Formats a String representation of a decimal number to a stated precision and total width.  First the
	 * substring following the last (right-most) decimal point is right filled with zeroes to the desired
	 * precision.  The remainder (most-significant) portion or the original String is then combined with
	 * the formatted fraction part and the result is left-filled with space characters to the desired total width.
	 * When the original String's only decimal point is the first character in the String, the whole number part
	 * will be created as a single '0' character preceeded by whatever space fill is required to achieve the
	 * desired total with.
	 * @param width the total number of characters desired in the formatted String
	 * @param precision the number of digits to the right of the decimal point in the formatted String
	 * @param s the String to be formatted
	 * @return the formatted String
	 */
	public static String decimalAlign(int width, int precision, String s){
		String fraction = "";
		String whole    = "0.";
		int dot = s.lastIndexOf('.');
		if(dot >= 0){
			whole    = s.substring(0, dot + 1);
			fraction = s.substring(dot + 1);
		}
		
		if(fraction.length() > precision){
			fraction = fraction.substring(0, precision);
		}
		return fillStart(' ', width, (whole + fillEnd('0', precision, fraction)));
	}
	
    /**
     * Return the memory in a simple format.
     * The format is used memory/total memory/max memory.
     * Each memory is formatted using {@link #toSimpleBytes(long)}.
     * 
     * @return The simple memory.
     * @author shinobu
     * @since RELEASE6-0
     */
    public static String simpleMemory() {
        StringBuilder memory = new StringBuilder();
        
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        
        memory.append(toSimpleBytes(usedMemory));
        memory.append("/");
        memory.append(toSimpleBytes(totalMemory));
        memory.append("/");
        memory.append(toSimpleBytes(maxMemory));
        
        return memory.toString();
    }
    
    /**
     * Formats bytes to a simple format.
     * The number of bytes will be suffixed by "B", "MB", "GB".
     * The number will always be 0 ~ 2048.
     * 
     * @param bytes The bytes.
     * @return The simple bytes.
     * @author shinobu
     * @since RELEASE6-0
     */
    private static String toSimpleBytes(long bytes) {
        String suffix = "B";
        if (bytes > 2048) {
            bytes /= 1024;
            suffix = "KB";
            
            if (bytes > 2048) {
                bytes /= 1024;
                suffix = "MB";
                
                if (bytes > 2048) {
                    bytes /= 1024;
                    suffix = "GB";
                }
            }
        }
        
        return String.format("%1$d%2$s", bytes, suffix);
    }
    
	/**
	 * Generates a text string containing current memory usage status as total heap size, free, percent used.
	 * Optionally, the string may be displayed in the system log.  A prefix strig may be provided to identify
	 * the source in the log output.
	 * @param s a String to prefix the memory usage display string
	 * @param logIt when true, the result String will be displayed as an info line in the system log
	 * @return a String containing the displayed memory usage
	 */
	public static String displayMemory(String s, boolean logIt){
		Runtime runtime 		= Runtime.getRuntime();
		long totalMemory 		= runtime.totalMemory();
		long freeMemory  		= runtime.freeMemory();
		int fracUsed 			= (int)(((totalMemory - freeMemory) * 10000L)/totalMemory);
		String percentUsed 		= (fracUsed / 100) + "." + fillStart('0',2,"" + (fracUsed % 100)) + "%";
		
		String t = s + " Mem usage:"   
				 + "  Total = " + fillStart(' ',12,commatize(totalMemory))
				 + " Free = " + fillStart(' ',12,commatize(freeMemory))
				 + " Used = " + fillStart(' ',3,percentUsed);
		if(logIt){
			LOG.info(t);
		}
		return t;
	}
	/**
	 * Generates a text string containing current memory usage.
	 * @param s a String to prefix the memory usage display string
	 * @return a String containing the displayed memory usage
	 * @see #displayMemeory(Strings, boolean logIt);
	 */
	public static String displayMemory(String s){
		return displayMemory(s, false);
	}
	/**
	 * Generates a StringBuffer of the specified size filled with spaces.
	 * @param length the size of the StringBuffer
	 */
	public static StringBuffer getSpaceFilledStringBuffer(int length){
		StringBuffer sb = new StringBuffer(length);
		
		for(int i = 0; i < length; i++){
			sb.append(" ");
		}
		return sb;	
	}
	
	/**
	 * Replaces & < > " and ' with &-escape sequences required by HTML and XML.
	 * @param s the String to be processed
	 * @return a String with the above characters replaced with the corresponding excape sequence
	 * or an empty string if s is null.
	 * 
	 */
	public static String escapeText(String s){
		
		if(s == null){
			return ("");
		}
		
		if(s.indexOf('&') != -1 || s.indexOf('<') != -1 || s.indexOf('>')  != -1 
								|| s.indexOf('"') != -1	|| s.indexOf('\'') != -1){
			StringBuffer sb = new StringBuffer(s.length() + 6);
			
			for(int i = 0; i < s.length(); i++){
				char c = s.charAt(i);
				switch(c){
					case '&':
						sb.append("&amp;");
						break;
					case '<':
						sb.append("&lt;");
						break;
					case '>':
						sb.append("&gt;");
						break;
					case '"':
						sb.append("&quot;");
						break;
					case '\'':
						sb.append("&apos;");
						break;
					default:
						sb.append(c); 
				} 
			}
			return sb.toString();
		}
		return s;
		
	}
	
	/**
	 * Replaces &-excape sequences required by HTML and XML with the character represented by the
	 * escape sequence (& < > " and ').
	 * @param String the String to be unescaped
	 * @return a string witht the escaped characters replaced with the above characters
	 */
	public static String unescapeText(String s){
		StringBuffer sb = new StringBuffer();
		int oldIndex = 0;
		int index = s.indexOf('&');
		
		while(index >= 0){
			sb.append(s.substring(oldIndex, index));
			
			if(s.startsWith("&amp;", index)){
				sb.append('&');
				oldIndex 	= index + 5;
			}else if(s.startsWith("&lt;", index)){
				sb.append('<');
				oldIndex 	= index + 4;
			}else if(s.startsWith("&gt;", index)){
				sb.append('>');
				oldIndex 	= index + 4;
			}else if(s.startsWith("&quote;", index)){
				sb.append('"');
				oldIndex 	= index + 7;
			}else if(s.startsWith("&apos;", index)){
				sb.append('\'');
				oldIndex 	= index + 6;
			}else{
				sb.append('&');
				oldIndex	= index + 1;
			}
			index = s.indexOf("&", oldIndex);			
		}
		sb.append(s.substring(oldIndex));
		return sb.toString();
	}
	
	/**
	 * Utility method that corrects the lower portion (normally eleven characters) of serial numbers.
	 * The process does the following:  (1) preserves all trailing spaces; (2) preserves all consecutive, 
	 * non-space characters immediatly to the left of any trailing spaces; (3) converts all characters 
	 * to the left of the right-most, non-space field to zeroes.
	 * @param s the lower portion of the serial number to be corrected.
	 * @return the corrected serial number as a String
	 */
	public static String correctSerialNumber(String s){						//Example:    'VP17Eb61b2570bbbb'
																			//char pos:    01234567890123456
		if(s.length() == 17){
			String t = s.substring(6);
			int i = t.length();
			char c = t.charAt(--i);
			
			while(i > 0 && c == ' '){
				c = t.charAt(--i);
			}
			while(i > 0 && c != ' '){
				c = t.charAt(--i);
			}
			if(c == ' '){
				i++;
			}
			s = s.substring(0,6) + fillStart('0',11,t.substring(i));
		}
		return s;
	}
	
	/**
	 * Assures the separator characters in a file path are correct for the current system and
	 * assures that the file path ends with a file separator charactor.
	 * @param path a String
	 * @return a String or null if path is null
	 */
	public static String fixDirNameSep(String path){
		String s = null;
		
		if(path != null){
			s = path.replace('\\', File.separator.charAt(0));
			s = s.replace('/', File.separator.charAt(0));
			s = (s.endsWith(File.separator) ? s : s + File.separator);
		}
		return s;
	}
	
	/**
	 * Checks a an object's toString() result for all numeric (from '0' to '9', inclusive) characters.
	 * @param o the Object to be checked
	 * @return true if all characters in s are numeric digits
	 */
	public static boolean isInteger(Object o){
		
		if(o == null || o.toString().length() == 0){
			return false;
		}
		String s = o.toString();
		for(int i = 0; i < s.length(); i++){
			
			if(s.charAt(i) < '0' || '9' < s.charAt(i)){
				return false;
			}
		}
		return true;
	}
	

    /**
     * Formats a date object to "YYYYMMDD".
     * @param date
     * @return
     */
    public static String formatYyyyMmDd(Date date) {
        if (date == null) {
            return "";
        }
        return formatYyyyMmDd().format(date);
    }

    /**
     * Formats a date object to "YYMMDD".
     * @param date
     * @return
     */
    public static String formatYyMmDd(Date date) {
        if (date == null) {
            return "";
        }
        return formatYyMmDd().format(date);
    }

    /**
     * Formats a date object to "YYYYMM".
     * @param date
     * @return
     */
    public static String formatYyyyMm(Date date) {
        if (date == null) {
            return "";
        }
        return formatYyyyMm().format(date);
    }
    
	/**
	 * Formats a date object to "MMM 'yy".
	 * @param date
	 * @return
	 */
	public static String formatMmmYy(Date date) {
		if (date == null) {
			return "";
		}
		return formatMmmYy().format(date);
	}
	
	/**
	 * Formats a date object to "MMM 'yy".
	 * @param date
	 * @return
	 */
	public static String formatMmm(Date date) {
		if (date == null) {
			return "";
		}
		return formatMmm().format(date);
	}	

    /**
     * Formats a date object to "MM/dd/yy".
     * 
     * @param date
     * @return
     * @author shinobu
     * @since RELEASE4-12
     */
    public static String formatMdDdYySlashed(Date date) {
        if (date == null) {
            return "";
        }
        return formatSlashedMmDdYy().format(date);
    }

	
	public static String booleanToString(boolean b){
	
		if (b){
			return new String("YES");
		} 
		return new String("NO"); 
				
	}

	public static String booleanToYNString(boolean b){
	
		if (b)
			return "Y";
		
		return "N"; 
				
	}

	public static String[] tokenize(String string, String delimiter) {
		StringTokenizer st = new StringTokenizer(string, delimiter);
		int tokenCount = st.countTokens();
		String[] token = new String[tokenCount];

		for (int i = 0, j = tokenCount; i < j; i++) {
			token[i] = st.nextToken();
		}

		return token;
	}
	

}
