package com.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "List")
@XmlAccessorType (XmlAccessType.FIELD)
public class SupplyCategories {
	
	@XmlElement(name = "supplyCategory")
	private List<SupplyCategory> supplyCategories = new ArrayList<>();

	public SupplyCategories() {
		super();
	}

	public List<SupplyCategory> getSupplyCategories() {
		return supplyCategories;
	}

	public void setSupplyCategories(List<SupplyCategory> supplyCategories) {
		this.supplyCategories = supplyCategories;
	}
	
}
