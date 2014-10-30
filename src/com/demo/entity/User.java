package com.demo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.demo.rs.UserInfo;
import com.demo.rs.WebServiceInfo;
import com.demo.util.StringUtils;

@NamedQueries({
	@NamedQuery(name="User.findAll",
		query="SELECT distinct u FROM User u" +
			" inner join fetch u.roles" +
			" where u.employeeId not in ('anonymous','system')" +
			" order by u.lastName"),
	@NamedQuery(name="User.findActive",
		query="SELECT distinct u FROM User u" +
			" inner join fetch u.roles" +
			" where u.employeeId not in ('anonymous','system')" +
			" and u.activeFlag = com.demo.entity.YesNoFlag.Y" +
			" order by u.lastName"),
	@NamedQuery(name="User.findByEmployeeId",
		query="SELECT u FROM User u" +
			" WHERE u.employeeId = :employeeId"),
	@NamedQuery(name="User.findByEmployeeIdActive",
		query="SELECT u FROM User u" +
			" WHERE u.employeeId = :employeeId" +
			" and u.activeFlag = com.demo.entity.YesNoFlag.Y")
})

/**
 * Entity implementation class for Entity: User
 * USER is Oracle Keyword
 */
@Entity
@Table(name="USERS")
@SequenceGenerator(name="USERS", sequenceName = "USERS_SEQ", allocationSize=1)
public class User implements WebServiceInfo<UserInfo>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USERS")
	private Long id;
	
	@Column(name="EMPLOYEE_ID", length=25, nullable=false, unique=true)
	private String employeeId;
	
	@Column(name="FIRST_NAME", length=75, nullable=false)
	private String firstName;
	
	@Column(name="LAST_NAME", length=75, nullable=false)
	private String lastName;
	
	@Column(name="EMAIL_ADDRESS", length=255, nullable=false)
	private String emailAddress;
		
	@Column(name="ACTIVE_FLAG", nullable=false, length=1)
	@Enumerated(EnumType.STRING)
	private YesNoFlag activeFlag;
	
	@Embedded
	private CreateAudit createAudit;
	
	@Embedded
	private UpdateAudit updateAudit;
	
	@Transient
	private String createAuditEmployeeId; // for load purposes
	
	@OneToMany
	@JoinTable(name="USER_ROLE", 
		joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
		inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"))
	private List<Role> roles = new ArrayList<Role>();
	
	
	public User() {
		super();
	}

	/**
	 * Get the persons full name.
	 * @return e.g. Jim Cheever
	 */
	public String getFullName () {
		return firstName + " " + lastName;
	}
	
	 /**
   * Get the persons full name with Id.
   * @return e.g. Jim Cheever (0176932)
   */
  public String getFullNameEmpId () {
    return getFullName() + " (" + employeeId + ")";
  }
  
  /**
  * Get the persons full name with Id.
  * @return e.g. Jim Cheever <jcheever@raytheon.com>
  */
 public String getFullNameEmail () {
   return getFullName() + " <" + emailAddress + ">";
 }
	
	/**
	 * Get the persons name last, then first.
	 * @return e.g. Cheever, Jim
	 */
	public String getLastCommaFirstName () {
		return lastName + ", " + firstName;
	}
	
	/**
	 * This method returns a transient property.
	 * @return - the audit employee id if set
	 */
	public String getCreateAuditEmployeeId() {
		return createAuditEmployeeId;
	}

	public void setCreateAuditEmployeeId(String createAuditEmployeeId) {
		this.createAuditEmployeeId = createAuditEmployeeId;
	}
	
	public String toStringCsv() {
		return String.format("User,%d,%s,%s,%s",id,firstName,lastName,employeeId);
	}
	
	@Override
	public String toString() {
		 return StringUtils.createToString(this, employeeId, firstName, lastName/*, getRoles()*/);
	}
	
	@Override
	public UserInfo getWebServiceInfo() {
		return new UserInfo(employeeId, firstName, lastName, getFullName(), emailAddress);
	}
	
	// generated
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public YesNoFlag getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(YesNoFlag activeFlag) {
		this.activeFlag = activeFlag;
	}

	public CreateAudit getCreateAudit() {
		return createAudit;
	}

	public void setCreateAudit(CreateAudit createAudit) {
		this.createAudit = createAudit;
	}
	public UpdateAudit getUpdateAudit() {
		return updateAudit;
	}
	public void setUpdateAudit(UpdateAudit updateAudit) {
		this.updateAudit = updateAudit;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
