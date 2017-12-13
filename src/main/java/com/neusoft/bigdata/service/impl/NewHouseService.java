package com.neusoft.bigdata.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.bson.Document;

import com.neusoft.bigdata.dao.impl.Dal;
import com.neusoft.bigdata.domain.KeyValue;

import scala.Tuple2;

public class NewHouseService implements Serializable {

	public NewHouseService (){
		
	}
	
	private static final long serialVersionUID = 1L;

	

	public long getNumByTag(final String city,final String tag) {
		Dal dal=new Dal("127.0.0.1", "data", "new_house");
		JavaRDD<Document> temp= dal.getRDD().filter(new Function<Document, Boolean>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Boolean call(Document v1) throws Exception {
				String c= v1.getString("city");
				if (!v1.containsKey("city")||c==null) {
					return false;
				}else{
					if (c.contains(city)) {
						return true;
					}
					return false;
				}
			}
		}).filter(new Function<Document, Boolean>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Boolean call(Document v1) throws Exception {
				if (v1.getString("tag").contains(tag)) {
					return true;
				}
				return false;
			}
		});
		long result=temp.count();
//		dal.destroy();
		return result;
	}
	
	public List<KeyValue<Integer>> getAvg(){
		Dal dal=new Dal("127.0.0.1", "data", "new_house");
		List<KeyValue<Integer>>result=new ArrayList<KeyValue<Integer>>();
		List<Tuple2<String, Iterable<Document>>>list= dal.getRDD().groupBy(new Function<Document, String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public String call(Document v1) throws Exception {
				String province= v1.getString("province");
				Double price= v1.getDouble("unitPrice");
				if (v1.getString("province")!=null&&price>0) {
					return province;
				}
				return "other";
			}
		}).collect();
		for (Tuple2<String, Iterable<Document>> tuple2 : list) {
			if (tuple2._1.equals("other")) {
				continue;
			}
			Iterator<Document>iterator=tuple2._2.iterator();
			List<Double>prices=new ArrayList<Double>();
			while (iterator.hasNext()) {
				prices.add(iterator.next().getDouble("unitPrice"));
			}
			double sum= dal.getSparkContext().parallelize(prices).reduce(new Function2<Double, Double, Double>() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public Double call(Double v1, Double v2) throws Exception {
					return v1+v2;
				}
			});
			result.add(new KeyValue<Integer>(tuple2._1,(int)sum/prices.size()));
		}
//		dal.destroy();
		return result;
	}
	
}
