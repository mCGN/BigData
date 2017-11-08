package com.neusoft.bigdata.domain;

public class OldHouse extends Base{
	

//	public  int code;
	
	public  OldHouse() {
//		this.code=constant.CODE_OLDHOUSE;
	}
	
	
	
	public OldHouse(String name, String tag, String unitPrice, String totalPrice, String type, String address,
			String area, String floor, String yrb) {
		this();
		this.name = name;
		this.tag = tag;
		this.unit_price = unitPrice;
		this.total_price = totalPrice;
		this.type = type;
		this.address = address;
		this.area = area;
		this.floor = floor;
		this.yrb = yrb;
//		this.url=url;
	}
	
//	public String url;

	public String name;

	public String tag;

	public String unit_price;
	
	public String total_price;

	public String type;//户型

	public String address;

	public String area;//面积
	
	public String floor;//楼层
	
	public String yrb;//建造时间

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



	public String getTotalPrice() {
		return total_price;
	}



	public void setTotalPrice(String totalPrice) {
		this.total_price = totalPrice;
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



	public String getFloor() {
		return floor;
	}



	public void setFloor(String floor) {
		this.floor = floor;
	}



	public String getYrb() {
		return yrb;
	}



	public void setYrb(String yrb) {
		this.yrb = yrb;
	}
	
	
}
