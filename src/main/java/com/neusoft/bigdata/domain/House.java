package com.neusoft.bigdata.domain;

import org.bson.types.ObjectId;

import com.neusoft.bigdata.crawler.core.domain.Base;

public class House extends Base {
	
	public House(){
		super();
	}
	
	public House(String name, String tag, Double unitPrice, String type, String address){
		super();
		this.name = name;
		this.tag = tag;
		this.unitPrice = unitPrice;
		this.type = type;
		this.address = address;
//		this.areaId=areaId;
	}
	
	public String name;

	public String tag;

	public Double unitPrice;

	public String type;//户型

	public String address;
	
//	public ObjectId areaId;
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

//	public ObjectId getAreaId() {
//		return areaId;
//	}
//
//	public void setAreaId(ObjectId areaId) {
//		this.areaId = areaId;
//	}

	
	
	
	
}
