package com.demo.rs;

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
@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryInfo {


	@XmlElement
	private String categoryName;
	
	@XmlElement
	private String parentId;

	/**
	 * Required for XML
	 */
	public CategoryInfo() {
	}

	/**
	 * Create a VO object.
	 */
	public CategoryInfo(String categoryName, String parentId) {
		this.categoryName = categoryName;
		this.parentId = parentId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
