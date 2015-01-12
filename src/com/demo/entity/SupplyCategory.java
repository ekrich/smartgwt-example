package com.demo.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.demo.rs.CategoryInfo;
import com.demo.rs.WebServiceInfo;
import com.demo.util.StringUtils;


@XmlRootElement(name = "supplyCategory")
@XmlAccessorType (XmlAccessType.FIELD)
@NamedQueries({
	@NamedQuery(name="SupplyCategory.findAll", 
			query="select s from SupplyCategory s"),
	@NamedQuery(name="SupplyCategory.find", 
	query="select s from SupplyCategory s"
			+ " where s.categoryName = :categoryName")
})
@Entity
@Table(name="SUPPLY_CATEGORY")
public class SupplyCategory implements WebServiceInfo<CategoryInfo>{
	@XmlElement
	@Id
	@Column(name="CATEGORY_NAME", length=128)
	private String categoryName;
	
	@XmlElement(name = "parentID")
	@Transient
	private String parentId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	SupplyCategory parent; 
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
    private Set<SupplyCategory> supplyCategories = new HashSet<>();

	
	public SupplyCategory() {
	}
	
	public void addSupplyCategory(SupplyCategory supplyCategoryChild) {
		supplyCategories.add(supplyCategoryChild);
	}
	
	public void setParentAndChild(SupplyCategory parent) {
		setParent(parent);
		if (parent != null) {
			parent.addSupplyCategory(this);
		}
	}
	
	@Override
	public CategoryInfo getWebServiceInfo() {
		// avoid NPE until I can fix load
		String parentCategoryName = (parent == null ? null : parent.categoryName);
		return new CategoryInfo(categoryName, parentCategoryName);
	}
	
	@Override
	public String toString() {
		return StringUtils.createToString(this, parent, categoryName);
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

	public SupplyCategory getParent() {
		return parent;
	}

	public void setParent(SupplyCategory parent) {
		this.parent = parent;
	}

	public Set<SupplyCategory> getSupplyCategories() {
		return supplyCategories;
	}

	public void setSupplyCategories(Set<SupplyCategory> supplyCategories) {
		this.supplyCategories = supplyCategories;
	}
	
}
