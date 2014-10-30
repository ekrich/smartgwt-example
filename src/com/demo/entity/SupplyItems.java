package com.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "List")
@XmlAccessorType (XmlAccessType.FIELD)
public class SupplyItems {
	
	@XmlElement(name = "supplyItem")
	private List<SupplyItem> supplyItems = new ArrayList<>();

	public SupplyItems() {
		super();
	}

	public List<SupplyItem> getSupplyItems() {
		return supplyItems;
	}

	public void setSupplyItems(List<SupplyItem> supplyItems) {
		this.supplyItems = supplyItems;
	}

	
}
