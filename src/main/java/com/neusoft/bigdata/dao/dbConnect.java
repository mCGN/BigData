package com.neusoft.bigdata.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import com.google.gson.Gson;
import com.mongodb.MongoClient;

public class dbConnect {
	
	private static MongoClient client;
	
	public static void getDB(String name){
		
	}
	
	public MongoClient getMongoClient(){
		HashMap< String, String>map=getHostAndPort();
		MongoClient client=new MongoClient(map.get("host"),Integer.parseInt(map.get("port")));
		return client;
	}
	
	private HashMap<String, String>getHostAndPort(){
		InputStream is= getClass().getResourceAsStream("db.json");
		byte[]buffer=new byte[]{};
		String str=null;
		try {
			is.read(buffer);
			str=new String(buffer);
			Gson gson=new Gson();
			return  gson.fromJson(str, HashMap.class);
		} catch (IOException e) {
			System.out.println("db 配置错误");
			e.printStackTrace();
		}
		return null;
	}
	
}
