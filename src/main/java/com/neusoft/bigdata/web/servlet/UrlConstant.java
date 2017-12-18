package com.neusoft.bigdata.web.servlet;

public class UrlConstant {
	
	private static final String NEW_HOUSE="https://gz.fang.anjuke.com";
	private static final String OLD_HOUSE="";
	private static final String RENTING_HOUSE="";
	
	public static String getUrl(String action){
		String result=null;
		if (action.equals("new_house")) {
			result=NEW_HOUSE;
		}else if(action.equals("old_house")) {
			result=OLD_HOUSE;
		}else if(action.equals("renting_house")) {
			result=RENTING_HOUSE;
		}
		return result;
	}
}
