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
 * <code>UpdateAudit</code> object.
 * 
 * @author Eric R.
 */
@Embeddable
public class UpdateAudit implements Audit, Serializable {
	
	private static final long serialVersionUID = 1L;

	@OneToOne
	@JoinColumn(name="UPDATED_BY")
	private User user;
	
	@Column(name="UPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	/**
	 * Copy with NPE safety.
	 * @param that - the object to copy
	 * @return - a new copy if that is not null, otherwise null.
	 */
	public static UpdateAudit copyOption(UpdateAudit that) {
	  UpdateAudit audit = null;
	  if (that != null) {
	    audit = new UpdateAudit(that);
	  }
	  return audit;
	}
	
	public UpdateAudit() {
		super();
	}
	
	/**
	 * Copy ctor for rate package copy. Private so not available to other entities in 
	 * this package.
	 * @param that
	 */
	private UpdateAudit(UpdateAudit that) {
		super();
		this.date = that.date; // immutable
		this.user = that.user; // user shared
	}
	
	/**
	 * Use when getting the date for create and update audits.
	 * @param user - the user doing the creation
	 * @param date - the date of creation
	 */
	public UpdateAudit(User user, Date date) {
		this.user = user;
		this.date = date;
	}
	
	/**
	 * Use this version when object only has this audit
	 * @param user - the user doing the task
	 */
	public UpdateAudit(User user) {
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