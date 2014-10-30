package com.demo.entity;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedQueries({
		@NamedQuery(name = "Role.findRole", 
			query = "SELECT r FROM Role r" +
				" where r.name = :name "),
		@NamedQuery(name = "Role.findAll", 
			query = "SELECT r FROM Role r") 
})

/**
 * Constants should be Enum and the DB contains 8 as well - not used.
 */
@Entity		
@Table(name="ROLE")
@SequenceGenerator(name="ROLE", sequenceName="ROLE_SEQ", allocationSize=1)
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Comparator<? super Role> ROLE_NAME_COMPARATOR = new RoleNameComparator();
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ROLE")
	private Long id;
	
	@Column(name="NAME", length=40)
	@Enumerated(EnumType.STRING)
	private RoleName name;
	
	public Role() {
		super();
	}
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name.toString();
	}



	public String toStringCsv() {
		return String.format("Role,%d,%s",id,name);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public RoleName getName() {
		return name;
	}

	public void setName(RoleName name) {
		this.name = name;
	}
	
	/**
	 * Comparator that sort by ordinal
	 */
	public static class RoleNameComparator implements Comparator<Role> {

		@Override
		public int compare(Role o1, Role o2) {
			int i;
			// compare type
			Integer ordinal1 = o1.getName().ordinal();
			Integer ordinal2 = o2.getName().ordinal();		
			i = ordinal1.compareTo(ordinal2);
			return i;
		}
		
	}
	
}
