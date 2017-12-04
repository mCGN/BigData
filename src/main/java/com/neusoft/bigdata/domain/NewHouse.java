package com.neusoft.bigdata.domain;

import org.bson.types.ObjectId;

public class NewHouse extends House {

	public NewHouse() {
		super();
	}

	public NewHouse(String name, String tag, Double unitPrice, String type, String address, String measure,String province,String city,String area) {
//		this();
		super(name, tag,  unitPrice,  type,  address);
		this.name = name;
		this.tag = tag;
		this.unitPrice = unitPrice;
		this.type = type;
		this.address = address;
		this.measure = measure;
		this.province=province;
		this.city=city;
		this.area=area;
		
	}

	public String measure;//面积

	public String province;
	
	public String city;
	
	public String area;
	
	@Override
	public String toString() {
		return "NewHouse [url=" + url + ", name=" + name + ", tag=" + tag + ", unitPrice="
				+ unitPrice + ", type=" + type + ", address=" + address + ", area=" + measure + "]";
	}

	

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String area) {
		this.measure = area;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
}
