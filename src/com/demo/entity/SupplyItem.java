package com.demo.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.demo.rs.SupplyItemInfo;
import com.demo.rs.WebServiceInfo;
import com.demo.util.StringUtils;


@XmlRootElement(name = "supplyItem")
@XmlAccessorType (XmlAccessType.FIELD)
@NamedQueries({
	@NamedQuery(name="SupplyItem.findAll", 
		query="select s from SupplyItem s"),
	@NamedQuery(name="SupplyItem.findByCategory",
		query="select s from SupplyItem s"
			+ " where s.supplyCategory.categoryName = :categoryName")
})
@Entity
@Table(name="SUPPLY_ITEM")
public class SupplyItem implements WebServiceInfo<SupplyItemInfo>{
	
	@XmlElement(name = "SKU")
	@Id
	@Column(length=10)
	private Integer sku;
	
	@XmlElement
	@Column(name="ITEM_NAME", length=128)
	private String itemName;
	
	@XmlElement
	@Column(length=2000)
	private String description;
	
	@XmlElement
	@Column(length=5)
	private String units;
	
	@XmlElement
	@Column(name="UNIT_COST", precision = 19, scale = 2)
	private BigDecimal unitCost;
	
    @XmlElement
    @Transient
	private String category;
    
    @XmlElement
    @ManyToOne(fetch= FetchType.LAZY, cascade=CascadeType.ALL)
    private SupplyCategory supplyCategory;
    
	
	public SupplyItem() {}
	
	@Override
	public String toString() {
		return StringUtils.createToString(this, sku, itemName, description, units, unitCost, category);
	}
	
	@Override
	public SupplyItemInfo getWebServiceInfo() {
		return new SupplyItemInfo(sku, itemName, description, units, unitCost, supplyCategory.getCategoryName());
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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getSku() {
		return sku;
	}

	public void setSku(Integer sku) {
		this.sku = sku;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public SupplyCategory getSupplyCategory() {
		return supplyCategory;
	}

	public void setSupplyCategory(SupplyCategory supplyCategory) {
		this.supplyCategory = supplyCategory;
	}

}
