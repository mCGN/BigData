package com.neusoft.bigdata.dao.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.neusoft.bigdata.common.Resources;
import com.neusoft.bigdata.dao.BeanMapUtils;
import com.neusoft.bigdata.dao.DBUtils;

public class MongoDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MongoClient client;
	private MongoDatabase db;
	private MongoCollection<Document>collection;
	
	public MongoDao(String dbName,String collectionName){
		try {
			String[]s=Resources.getResourceAsString("db.txt").split(":");
			String host=s[0];
			int port=Integer.parseInt(s[1]);
			client=DBUtils.connect(host,port);
			db=client.getDatabase(dbName);
			collection=db.getCollection(collectionName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public  void insert(Map<String, Object>map) {
		Document document=new Document(map);
		collection.insertOne(document);
	}
	
	public  void insert(Document document) {
//		Document document=new Document(map);
		collection.insertOne(document);
	}
	
	 public <T> void insert(T obj) {
		Document doc=new Document(BeanMapUtils.beanToMap(obj));
		collection.insertOne(doc);
	}
	
	public <T> void insertAll(ArrayList<T>documents){
		for (T document : documents) {
			insert(document);
		}
	}

	public  boolean update(Bson filter,Bson update) {
		UpdateResult result= collection.updateMany(filter, update);
		return result.isModifiedCountAvailable();
	}

	public FindIterable<Document> find(Bson filter) {
		FindIterable<Document> result= collection.find(filter);
		return result;
	}
	
	public <T> FindIterable<T>find(Bson filter,Class<T>resultClass){
		return collection.find(filter, resultClass);
	}

	public  DeleteResult delete(Bson filter) {
		DeleteResult result= collection.deleteMany(filter);
		return result;
	}

	public DistinctIterable<String> distinct(String fieldName){
		return collection.distinct(fieldName, String.class);
	}

	public void destroy(){
		client.close();
		
	}
	
	public MongoCollection<Document>getCollection(){
		return collection;
	}
	
	
	
}
