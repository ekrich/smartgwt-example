
package com.demo.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Some useful string manipulation methods.
 *
 * @author hristo, ekr
 */
final public class StringUtils {
    
    /** @return {@code true} if the string is {@null} or with length 0. */
    static public boolean isEmptyOrNull(String s){
        return s==null || s.length()==0;
    }
    
    static public boolean isAnyEmptyOrNull(String... strings){
        for (String s:strings)
            if(isEmptyOrNull(s))
                return true;
        
        return false;
    }
    
    static public boolean areAllEmptyOrNull(String... ss){
        for (String s:ss)
            if(isEmptyOrNull(s))
                return false;
        
        return true;
    }
    
    static public CharSequence escapeSingleQuote(CharSequence s){       
        StringBuilder r = new StringBuilder(s.length()+10);
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);           
            if(c=='\'')r.append('\\');                
            r.append(c); 
        }
        return r.toString();
    }
    
    

	 
	 
	 /**
	  * Convert a map into a JavaScript object literal.
	  * @param enums
	  * @return
	  */
	 public static String asJavaObjectLiteral(Map<?,?> map){
		 if(map==null||map.isEmpty())
			 return "{}";
		 StringBuilder builder = new StringBuilder("{");
	    	int currentPosition = 1;
			for(Map.Entry<?, ?> entry:map.entrySet()){
				builder.append(entry.getKey()).append(":\"").append(entry.getValue()).append("\"");
				if(currentPosition++<map.size())
					builder.append(",");
			}
			builder.append("}");
			return builder.toString();
	 }
	 
	 /**
	  * Replaces '*'to'%' to be compatible with Oracle's wild-card search.
	  * @param filter
	  * @param convertToUpper
	  * @return
	  */
	 public static String processOracleWildcardString(String filter, boolean convertToUpper){
			if(StringUtils.isEmptyOrNull(filter))
				return filter;
			String result = convertToUpper? filter.toUpperCase():filter;
			return result.replace('*', '%')+"%";
	 }
	 
    /** So no one can instantiate it */
    private StringUtils() {}
    
    /**
     * Replaces html related tags
     * @param str
     * @return
     */
    public static String removeHtmlTags(String str){
    	if(str == null)
    		return null;
    	
    	return str.replaceAll("(?i)</?(FONT|DIV|span|P|BR)[^>]*>", "").replaceAll("&nbsp;", "");
    }
    
    public static long strToLong(String str) {
        try {
            Long result = Long.valueOf(str);
            return result.longValue();
        } catch (NumberFormatException e) {
            return -1;
        }
    }

 /*
     * Find if String contains only a specific char - useful in checking if only oracle wildcards
     * @author: Vasan
     */
    public static boolean containsOnlyChar(String str, char c) {
    	for (int i =0;i<str.length(); i++) {
    		if (str.charAt(i) != c) return false;
    	}
    	return true;
    }
    
    public static int strToInt(String str) {
        try {
            Integer result = Integer.valueOf(str);
            return result.intValue();
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Make a string from the list.
     * @param list - the list to process, null becomes null, and calls toString() on objects.
     * @return a string with the supplied string separator
     */
    public static <T> String mkString(Collection<T> list, String separator) {
    	StringBuilder s = new StringBuilder();
    	Iterator<T> it = list.iterator();
    	if (it.hasNext()) {
    		s.append(it.next());
    	}
    	while (it.hasNext()) {
    		s.append(separator).append(it.next());
    	}
    	return s.toString();
    }
    
    /**
     * Makes a string from a list with given separator
     * @param separator
     * @param list
     * @return
     */
    public static <T> String mkString(String separator, Object... list) {
      return mkString(list, separator);
    }
    
    
    /**
     * Make a string from the list.
     * @param list - the list to process, null becomes null, and calls toString() on objects.
     * @return a string with the default separator ","
     */
    public static <T> String mkString(Collection<T> list) {
    	return mkString(list, ",");
    }
    
    /**
     * Make a string from an array.
     * @param array - the array to process, null becomes null, and calls toString() on objects.
     * @return a string with the supplied string separator
     */
	public static <T> String mkString(T[] array, String separator) {
    	List<T> list = Arrays.asList(array);
    	return mkString(list, separator);
    }
    
	 /**
     * Make a string from an array.
     * @param array - the array to process, null becomes null, and calls toString() on objects.
     * @return a string with the default separator ","
     */
	public static <T> String mkString(T[] array) {
    	return mkString(array, ",");
    }
	
	/**
	 * Wrap a string with characters
	 * @param lhs
	 * @param str
	 * @param rhs
	 * @return
	 */
	public static String wrapString(Character lhs, String str, Character rhs) {
    	return wrapString(lhs.toString(), str, rhs.toString());
    }
	/**
	 * Wrap a string with strings
	 * @param lhs
	 * @param str
	 * @param rhs
	 * @return
	 */
	public static String wrapString(String lhs, String str, String rhs) {
    	return lhs + str + rhs;
    }
	
	/**
	 * Create a list such as (a,b,c)
	 * @param args
	 * @return
	 */
	public static String createListString(Object... args) {
    	return wrapString('(', mkString(args), ')');
    }
	/**
	 * For a class Foo with args "Bar" and 1, Foo(Bar,1) as a string
	 * @param obj - e.g. an instance of class Foo - typically 'this'.
	 * @param args - object properties
	 * @return
	 */
	public static <T> String createToString(Object obj, Object... args) {
    	return obj.getClass().getSimpleName() + createListString(args);
    }
	
	/**
	 * Replaces a character at a specified position
	 * @param s
	 * @param pos
	 * @param c
	 * @return
	 */
	public static String replaceCharAt(String s, int pos, char c) {
	   return s.substring(0,pos) + c + s.substring(pos+1);
	}
}
