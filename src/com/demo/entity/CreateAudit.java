/* Log:
 * 
 */
package com.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <code>CreateAudit</code> object.
 * 
 * @author Eric R.
 */
@Embeddable
public class CreateAudit implements Audit, Serializable {
	
	private static final long serialVersionUID = 1L;

	@OneToOne
	@JoinColumn(name="CREATED_BY")
	private User user;
	
	@Column(name="CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	/**
	 * Required by JPA - do not use.
	 */
	public CreateAudit() {
		super();
	}
	
	public CreateAudit(CreateAudit that) {
		super();
		this.date = new Date(that.date.getTime());
		this.user = that.user; // user get copied
	}
	
	/**
	 * Use when getting the date for create and update audits.
	 * @param user - the user doing the creation
	 * @param date - the date of creation
	 */
	public CreateAudit(User user, Date date) {
		this.user = user;
		this.date = date;
	}
	
	/**
	 * Use this version when object only has this audit
	 * @param user - the user doing the task
	 */
	public CreateAudit(User user) {
		this(user, new Date());
	}
	
	public String toStringCsv() {
		return String.format(this.getClass().getSimpleName() + ",%d,%2$tFT%2$tT",user.getId(),date);
	}

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}
}