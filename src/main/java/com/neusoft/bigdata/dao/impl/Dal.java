package com.neusoft.bigdata.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;
import org.bson.Document;

import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.ReadConfig;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;


public class Dal implements Serializable{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//	"mongodb://127.0.0.1/test.myCollection"
	private static String uri=null;
	private static  SparkConf conf=new SparkConf();

	private static JavaSparkContext jsc;
	
	/**
	 * javasparkcontext conf
	 */
	public  void Conf(String host,String db,String collection){
		if(jsc!=null){
			jsc.close();
			jsc=null;
		}
		StringBuilder builder=new StringBuilder();
		builder.append("mongodb://").append(host).append("/").append(db).append(".").append(collection);
		uri=builder.toString();
		conf.setMaster("local")
			.setAppName("app name")
			.set("spark.app.id", "app id")
			.set("spark.mongodb.input.uri", uri)
			.set("spark.mongodb.output.uri", uri)
			.set("spark.driver.allowMultipleContexts","true");
		 jsc=new JavaSparkContext(conf);
	}
	
	
	
	private Dal(String host,String db,String collection){
		StringBuilder builder=new StringBuilder();
		builder.append("mongodb://").append(host).append("/").append(db).append(".").append(collection);
		uri=builder.toString();
		conf.setMaster("local")
			.setAppName("app name")
			.set("spark.app.id", "app id")
			.set("spark.mongodb.input.uri", uri)
			.set("spark.mongodb.output.uri", uri)
			.set("spark.driver.allowMultipleContexts","true");
		 jsc=new JavaSparkContext(conf);
	}
	
	public  JavaMongoRDD<Document>getRDD(){
		JavaMongoRDD<Document> rdd=MongoSpark.load(jsc);
		rdd.persist(StorageLevel.MEMORY_ONLY());
		return rdd;
	}
	
	private static Dal instance=null;
	public static synchronized Dal Instance(String host,String db,String collection){
		if (instance==null) {
			instance=new Dal(host, db, collection);
		}
		return instance;
		
	}
	
	/**
	 * 
	 * @return the JavaSparkContext instance or null
	 */
	public  JavaSparkContext getSparkContext(){
		return jsc;
	}
	
	public  void destroy(){
		jsc.close();
//		jsc=null;
	}
	
	public  boolean havaContext(){
		if (jsc==null) {
			return false;
		}
		return true;
	}
		
	private List<Document>read(){
		JavaRDD<Document>rdd= MongoSpark.load(jsc);
		rdd.persist(StorageLevel.MEMORY_ONLY());
		return rdd.collect();
	}
	
	private List<Document>read(String collection){
		Map<String, String>options=new HashMap<String, String>();
		options.put("collection", collection);//修改查询的表
		options.put("readPreference.name", "secondaryPreferred");
		ReadConfig config=ReadConfig.create(jsc).withOptions(options);
		JavaMongoRDD<Document>rdd= MongoSpark.load(jsc,config);
		rdd.persist(StorageLevel.MEMORY_ONLY());
		return rdd.collect();
	}
	
	private void write(List<Document>list){
		JavaRDD<Document> rdd=jsc.parallelize(list);
		MongoSpark.save(rdd);
		jsc.close();
	}
	
	private void write(Document document){
		List<Document>list=new ArrayList<Document>();
		list.add(document);
		write(list);
	}
	
	
	
}
