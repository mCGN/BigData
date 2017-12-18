package com.neusoft.bigdata.crawler.core.domain;

import org.bson.Document;
import org.bson.types.ObjectId;

public class Base {
//	public ObjectId _id;
	
	public String url;
	
	protected Base(){
//		set_id(new ObjectId());
	}
	
//	public ObjectId get_id() {
//		return _id;
//	}
//
//	public void set_id(ObjectId _id) {
//		this._id = _id;
//	}

	public String getUrl() {
		return url;
	}
	

	public void setUrl(String url){
		this.url=url;
	}


	
	
	
}
