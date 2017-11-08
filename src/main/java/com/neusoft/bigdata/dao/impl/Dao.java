package com.neusoft.bigdata.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.neusoft.bigdata.common.Resources;
import com.neusoft.bigdata.dao.CURD;
import com.neusoft.bigdata.dao.DBUtils;

public class Dao implements CURD {

	private MongoClient client;
	private MongoDatabase db;
	private MongoCollection<Document>collection;
	
	public Dao(String dbName,String collectionName){
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
	
	public synchronized void add(Map<String, Object>map) {
		Document document=new Document(map);
		collection.insertOne(document);
	}
	
	public synchronized void addAll(ArrayList<Document>documents,InsertManyOptions options){
		if (options==null) {
			collection.insertMany(documents);
		}else {
			collection.insertMany(documents, options);
		}
	}

	public synchronized boolean update(Bson filter,Bson update) {
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

	public synchronized DeleteResult delete(Bson filter) {
		DeleteResult result= collection.deleteMany(filter);
		return result;
	}


	public void destroy(){
		client.close();
	}
	
	
	
}
