package com.demo.rs;

import java.math.BigDecimal;

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
@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplyItemInfo {

	@XmlElement
	private Integer sku;
	
	@XmlElement
	private String itemName;
	
	@XmlElement
	private String description;
	
	@XmlElement
	private String units;
	
	@XmlElement
	private BigDecimal unitCost;
    
	@XmlElement
	private String categoryName;

	/**
	 * Required for XML
	 */
	public SupplyItemInfo() {
	}


	public SupplyItemInfo(Integer sku, String itemName, String description,
			String units, BigDecimal unitCost, String categoryName) {
		super();
		this.sku = sku;
		this.itemName = itemName;
		this.description = description;
		this.units = units;
		this.unitCost = unitCost;
		this.categoryName = categoryName;
	}


	public Integer getSku() {
		return sku;
	}


	public void setSku(Integer sku) {
		this.sku = sku;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getUnits() {
		return units;
	}


	public void setUnits(String units) {
		this.units = units;
	}


	public BigDecimal getUnitCost() {
		return unitCost;
	}


	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
