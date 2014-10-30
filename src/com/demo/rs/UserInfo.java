package com.demo.rs;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Web Service VO
 * 
 * @author ekr
 * 
 */
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private String employeeId;

	@XmlElement
	private String firstName;

	@XmlElement
	private String lastName;
	
	@XmlElement
	private String fullName;

	@XmlElement
	private String emailAddress;

	/**
	 * Required for XML
	 */
	public UserInfo() {
	}

	/**
	 * Create a PoolInfo object.
	 * 
	 * @param code
	 * @param description
	 * @param odcId
	 */
	public UserInfo(String employeeId, String firstName, String lastName,
			String fullName, String emailAddress) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.emailAddress = emailAddress;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}
