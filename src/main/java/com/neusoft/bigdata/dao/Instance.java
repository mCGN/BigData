package com.neusoft.bigdata.dao;

import com.neusoft.bigdata.dao.impl.MongoDao;

public class Instance {
	
	public static MongoDao getNewHouseDao(){
		MongoDao dao=new MongoDao("data", "new_house");
		return dao;
	}
	
	public static MongoDao getoldHouseDao(){
		MongoDao dao=new MongoDao("data", "old_house");
		return dao;
	}
	
	public static MongoDao getRentingHouseDao(){
		MongoDao dao=new MongoDao("data", "renting_house");
		return dao;
	}
	
}
