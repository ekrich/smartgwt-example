package com.demo.rs;

import java.util.ArrayList;
import java.util.List;

/**
 * Utils to convert Entity beans to web service "Info" objects.
 * 
 * @author ekr
 *
 */
public class InfoUtils {
	
	private InfoUtils(){}
	
	/**
	 * Convert one Entity bean to an Info bean.
	 * 
	 * @param bean - the entity bean
	 * @return - an Info bean
	 */
	public static <T, U extends WebServiceInfo<T>> T convert(U bean) {
		return bean.getWebServiceInfo();
	}
	
	public static <T, U extends WebServiceInfo<T>> List<T> convert(List<U> beans) {
		List<T> infos = new ArrayList<>();
		for(U bean : beans) {
			infos.add(convert(bean));
		}
		return infos;
	}

}
