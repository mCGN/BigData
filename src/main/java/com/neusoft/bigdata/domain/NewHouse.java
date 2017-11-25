package com.neusoft.bigdata.domain;

import org.bson.types.ObjectId;

public class NewHouse extends House {

	public NewHouse() {
		super();
	}

	public NewHouse(String name, String tag, String unitPrice, String type, String address, String measure,ObjectId areaId) {
//		this();
		super(name, tag,  unitPrice,  type,  address,areaId);
		this.name = name;
		this.tag = tag;
		this.unit_price = unitPrice;
		this.type = type;
		this.address = address;
		this.measure = measure;
	}

//	public String name;
//
//	public String tag;
//
//	public String unit_price;
//
//	public String type;//户型
//
//	public String address;

	public String measure;//面积

	@Override
	public String toString() {
		return "NewHouse [url=" + url + ", name=" + name + ", tag=" + tag + ", unitPrice="
				+ unit_price + ", type=" + type + ", address=" + address + ", area=" + measure + "]";
	}

	

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String area) {
		this.measure = area;
	}

	
}
