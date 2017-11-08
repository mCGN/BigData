package com.neusoft.bigdata.dao;

import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.result.DeleteResult;

public interface CURD {
	void add(Map<String, Object>map);
	boolean update(Bson filter,Bson update);
	FindIterable<Document> find(Bson filter);
	DeleteResult delete(Bson filter);
}
