package com.demo.entity;

/**
 * Used for Yes/No Flag
 * 
 * Use Annotation Enumerated(EnumType.STRING) for String stored
 * or leave off EnumType for 0, 1 = No, Yes = false, true
 * 
 * @author ekr.
 *
 */
public enum YesNoFlag {
	N("No", false), 
	Y("Yes", true);
	
	private final String label;
	private final boolean asBoolean;
	
	YesNoFlag(String label, boolean asBoolean) {
        this.label = label;
        this.asBoolean = asBoolean;
  }
	
	public String getLabel() {
		return label;
	}
	
	/**
	 * Find enum based on label
	 * @param label Yes or No (Case Ignored)
	 * @return - the enum value
	 */
	public static YesNoFlag findByLabel(String label){
		for(YesNoFlag flag : YesNoFlag.values()) {
			if(flag.getLabel().equalsIgnoreCase(label)) {
				return flag;
			}
		}
		return null; // not found
	}

  public boolean asBoolean() {
    return asBoolean;
  }
	
}
