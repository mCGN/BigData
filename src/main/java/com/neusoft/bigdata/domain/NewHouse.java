package com.neusoft.bigdata.domain;

public class NewHouse extends Base {

//	public int code;
	
	public NewHouse() {
//		this.code=constant.CODE_NEWHOUSE;
	}

	public NewHouse(String name, String tag, String unitPrice, String type, String address, String area) {
		this();
		this.name = name;
		this.tag = tag;
		this.unit_price = unitPrice;
		this.type = type;
		this.address = address;
		this.area = area;
//		this.url=url;
	}
	
//	public String url;

	public String name;

	public String tag;

	public String unit_price;

	public String type;//户型

	public String address;

	public String area;//面积

	@Override
	public String toString() {
		return "NewHouse [url=" + url + ", name=" + name + ", tag=" + tag + ", unitPrice="
				+ unit_price + ", type=" + type + ", address=" + address + ", area=" + area + "]";
	}

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

	public String getUnitPrice() {
		return unit_price;
	}

	public void setUnitPrice(String unitPrice) {
		this.unit_price = unitPrice;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	
}
