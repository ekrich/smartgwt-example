package com.demo.rs;

/**
 * Web Service adapter to allow the JPA model to produce
 * info objects (value objects) which are suitable for
 * serialization to JSON. They should also be annotated 
 * to produce XML so both formats can be supported.
 * 
 * @author ekr
 *
 * @param <T> - e.g. UserInfo type for User object
 * TODO make extend InfoObject in the type parameter
 */
public interface WebServiceInfo<T> {
	
	/**
	 * Implement this method to return the corresponding
	 * info object for the bean to serialize to JSON or
	 * XML. e.g. {@code public class User implements WebServiceInfo<UserInfo>, Serializable}
	 * 
	 * @return - the info Object e.g. UserInfo
	 */
	T getWebServiceInfo();

}
