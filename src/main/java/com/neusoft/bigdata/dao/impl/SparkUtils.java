package com.neusoft.bigdata.dao.impl;

import java.net.Socket;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class SparkUtils {
	
	private SparkUtils(){
		
	}

	private static JavaSparkContext context;
	
	
	public static JavaSparkContext getSparkCpntext(SparkConf conf){
		if (context==null) {
			context=new JavaSparkContext(conf);
		}else{
			String currentId=context.getConf().getAppId();
			if (!currentId.equals(conf.getAppId())) {
				context.close();
				context=new JavaSparkContext(conf);
			}
		}
		return context;
	}
	
}
