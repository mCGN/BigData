package com.neusoft.bigdata.dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;

public class DBUtils {
	static MongoClientOptions options;

	public static MongoClient connect(String host,int port) {
		try {
			MongoClientOptions.Builder build = new MongoClientOptions.Builder();
			build.connectionsPerHost(50);
			build.threadsAllowedToBlockForConnectionMultiplier(50);
			build.connectTimeout(1 * 60 * 1000);
			build.maxWaitTime(2 * 60 * 1000);
			options = build.build();
			
			MongoClient client = new MongoClient(host, port);
			return client;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	
	
}