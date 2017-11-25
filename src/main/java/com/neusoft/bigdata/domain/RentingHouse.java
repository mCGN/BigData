package com.neusoft.bigdata.domain;

import org.bson.types.ObjectId;

public class RentingHouse  extends House{

	public RentingHouse(){
		super();
	}
	
	
	public RentingHouse(String name, String tag, String unitPrice, String type, String address, String floor,ObjectId areaId){
//		this();
		super(name, tag,  unitPrice,  type,  address,areaId);
		this.floor = floor;
//		this.url=url;
	}


	public String floor;//楼层

	


	public String getFloor() {
		return floor;
	}


	public void setFloor(String floor) {
		this.floor = floor;
	}
	
	
	
}
