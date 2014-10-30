/* Log:
 * 
 */
package com.demo.entity;

import java.util.Date;

/**
 * <code>Audit</code> interface to standardize audit operations.
 * 
 * @author Eric R.
 */
public interface Audit {
	
	User getUser();

	void setUser(User user);

	Date getDate();

	void setDate(Date date);
}