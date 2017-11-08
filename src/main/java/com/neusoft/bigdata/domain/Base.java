package com.neusoft.bigdata.domain;

public class Base {
//	public String _id;
	
	public long time_stamp;
	
	public String url;
	
	public void set(String url,long timeStamp){
		this.url=url;
		this.time_stamp=timeStamp;
	}

//	public String get_id() {
//		return _id;
//	}
//
//	public void set_id(String _id) {
//		this._id = _id;
//	}

	public long getTimeStamp() {
		return time_stamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.time_stamp = timeStamp;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
