package com.neusoft.bigdata.dao;

import com.neusoft.bigdata.dao.impl.Dao;

public class Instance {
	
	public static Dao getNewHouseDao(){
		Dao dao=new Dao("data", "new_house");
		return dao;
	}
	
	public static Dao getoldHouseDao(){
		Dao dao=new Dao("data", "old_house");
		return dao;
	}
	
	public static Dao getRentingHouseDao(){
		Dao dao=new Dao("data", "renting_house");
		return dao;
	}
	
}
