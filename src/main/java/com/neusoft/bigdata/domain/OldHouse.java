package com.neusoft.bigdata.domain;

import org.bson.types.ObjectId;

public class OldHouse extends House{
	
	
	public  OldHouse() {
		super();
	}
	
	
	
	public OldHouse(String name, String tag, String unitPrice, String totalPrice, String type, String address,
			String measure, String floor, String yrb,ObjectId areaId) {
//		this();
		super(name, tag,  unitPrice,  type,  address,areaId);
		this.total_price = totalPrice;
		this.measure = measure;
		this.floor = floor;
		this.yrb = yrb;
//		this.url=url;
	}
	
//	public String url;

	
	public String total_price;

	public String measure;//面积
	
	public String floor;//楼层
	
	public String yrb;//建造时间




	public String getTotalPrice() {
		return total_price;
	}



	public void setTotalPrice(String totalPrice) {
		this.total_price = totalPrice;
	}

	public String getMeasure() {
		return measure;
	}



	public void setMeasure(String measure) {
		this.measure = measure;
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
