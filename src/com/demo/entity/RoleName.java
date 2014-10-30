package com.demo.entity;

import java.util.EnumSet;


/**
 * Maps to first two attributes/columns of Role
 * which starts at 1
 * @author ekr
 *
 */
public enum RoleName {
	// Note: please edit / refactor the static Strings below
	READ_ONLY("Read Only"),
	ADMIN("Admin"),
	IT_ADMIN("IT Admin");

	// Note: please maintain these with the above for use in @RolesAllowed in the backend code
	public static final String READ_ONLY_STR = "READ_ONLY";
	public static final String ADMIN_STR = "ADMIN";
	public static final String IT_ADMIN_STR = "IT_ADMIN";
	
	// useful sets
	public static final EnumSet<RoleName> ADMINS = EnumSet.of(ADMIN, IT_ADMIN);
	
	// enum vars
	private final String label;

	RoleName(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static RoleName findByLabel(String label){
		for(RoleName value : RoleName.values()) {
			if(value.getLabel().equalsIgnoreCase(label)) {
				return value;
			}
		}
		return null; // not found
	}
	
	@Override
	public String toString() {
		return getLabel();
	}

	public static RoleName[] getAdminRoles() {
		return getRolesAsArray(RoleName.ADMINS);
	}
	
	private static RoleName[] getRolesAsArray(EnumSet<RoleName> enumSet) {
		return enumSet.toArray(new RoleName[enumSet.size()]);
	}

}
