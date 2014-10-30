package com.demo.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class DateUtils {
	
	private DateUtils(){throw new AssertionError();}
	
	public static String formatMMDDYYYY(Date date, String nullValue){
		return date==null?nullValue:String.format("%tD",date);
	}
	
	public static String formatMMDDYYYY(Date date){
		return formatMMDDYYYY(date,null);
	}

	/**
	 * ISO format <a href="http://en.wikipedia.org/wiki/ISO_8601">ISO 8601</a>
	 */
	public static String formatYYYYMM(Date date){
		return String.format("%1$tY-%1$tm", date);
	}

	/**
	 * ISO format <a href="http://en.wikipedia.org/wiki/ISO_8601">ISO 8601</a>
	 */
	public static String formatYYYYMM(int year, int month){
		return String.format("%d-%02d", year, month);
	}
	
	public static String formatDateTimeISO8601(Date date){
		return date==null? null: String.format("%1$tY-%1$tm-%1$tdT%1tT", date);
	}
	
  /**
   * Duration in the form - PTHH:mm:ss.mmm
   * P is period or duration and T designates
   * a time in ISO8601 format.
   * 
   * @param date - created from milliseconds
   * @return - duration up to 24hrs max
   */
  public static String formatDuration(long millis) {
    if (millis < 0) {
      throw new IllegalArgumentException(String.format("Duration: %d is < 0", millis));
    }

//    long days = TimeUnit.MILLISECONDS.toDays(millis);
//    millis -= TimeUnit.DAYS.toMillis(days);
    long hours = TimeUnit.MILLISECONDS.toHours(millis);
    millis -= TimeUnit.HOURS.toMillis(hours);
    long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
    millis -= TimeUnit.MINUTES.toMillis(minutes);
    long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
    millis -= TimeUnit.SECONDS.toMillis(seconds);

    return String.format("PT%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
  }
	
	public static String formatTimeStampMMDDYY(Date date){
		return date==null? null: String.format("%1$tm/%1$td/%1$ty:%1tT", date);
	}
	
	public static String formDollarsInK(final Double dollarAmount){
		return dollarAmount!=null?String.format("$%,.0f", dollarAmount/1000.00):"";
	}
	
	public static String formDollarsAndCents(final Double dollarAmount){
		return dollarAmount!=null?String.format("$%,.2f", dollarAmount):"";
	}
	
	public static int calculateOpen(Long openDate, Long closeDate){
		long difference = Math.abs(openDate - closeDate);
		int dayDiff = (int) Math.floor(difference / 1000/ 60 / 60 / 24);				
		return dayDiff;
	}
	
	public static int getCurrentYear() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);
		return year;
	}
	
	public static int getYearMonth(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH); //zero based
		int yearMonth = year * 100 + month + 1;
		return yearMonth;
	}
	
	public static int getYear(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		return year;
	}	
}
