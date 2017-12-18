package com.neusoft.bigdata.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.bson.Document;

import com.neusoft.bigdata.dao.impl.Dal;
import com.neusoft.bigdata.domain.KeyValue;

import scala.Tuple2;

public class NewHouseService implements Serializable {

	static Dal dal;

	public NewHouseService() {
		dal = Dal.Instance("127.0.0.1", "data", "new_house");
	}

	private static final long serialVersionUID = 1L;

	public HashMap<String, Integer> getMaxAndMinPrice(final String city) {
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		// Dal dal=Dal.Instance("127.0.0.1", "data", "new_house");
		JavaRDD<Double> rdd = dal.getRDD().filter(new Function<Document, Boolean>() {

			public Boolean call(Document v1) throws Exception {
				// TODO Auto-generated method stub
				if (!v1.containsKey("city")) {
					return false;
				} else {
					String s = v1.getString("city");
					if (s != null && (s.equals(city) || s.contains(city))) {
						if (v1.getDouble("unitPrice") > 0) {
							return true;
						}
					}
				}
				return false;
			}
		}).map(new Function<Document, Double>() {

			public Double call(Document v1) throws Exception {
				return v1.getDouble("unitPrice");
			}
		});
		double max = rdd.reduce(new Function2<Double, Double, Double>() {

			public Double call(Double v1, Double v2) throws Exception {
				return Math.max(v1, v2);
			}
		});
		double min = rdd.reduce(new Function2<Double, Double, Double>() {

			public Double call(Double v1, Double v2) throws Exception {
				return Math.min(v1, v2);
			}
		});
		result.put("max", (int) max);
		result.put("min", (int) min);
		// dal.destroy();
		return result;
	}

	public HashMap<String, Long> getNumByTag(final String city, final String tag) {
		// Dal dal=Dal.Instance("127.0.0.1", "data", "new_house");
		HashMap<String, Long> result = new HashMap<String, Long>();
		JavaRDD<Document> temp1 = dal.getRDD().filter(new Function<Document, Boolean>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Boolean call(Document v1) throws Exception {
				String c = v1.getString("city");
				if (!v1.containsKey("city") || c == null) {
					return false;
				} else {
					if (c.contains(city)) {
						return true;
					}
					return false;
				}
			}
		});
		result.put("all", temp1.count());
		JavaRDD<Document> temp2 = temp1.filter(new Function<Document, Boolean>() {

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
		result.put("count", temp2.count());
		// dal.destroy();
		return result;
	}

	public List<KeyValue<Integer>> getAvg() {
		// Dal dal=Dal.Instance("127.0.0.1", "data", "new_house");
		List<KeyValue<Integer>> result = new ArrayList<KeyValue<Integer>>();
		JavaRDD<Document> rdd = dal.getRDD();
		List<Tuple2<String, Iterable<Document>>> list = rdd.groupBy(new Function<Document, String>() {
			// 分组
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public String call(Document v1) throws Exception {
				String province = v1.getString("province");
				Double price = v1.getDouble("unitPrice");
				if (v1.getString("province") != null && price > 0) {
					return province;
				}
				return "other";
			}
		}).collect();
		for (Tuple2<String, Iterable<Document>> tuple2 : list) {
			// 循环遍历，统计平均数
			if (tuple2._1.equals("other")) {
				continue;
			}
			Iterator<Document> iterator = tuple2._2.iterator();
			List<Double> prices = new ArrayList<Double>();
			while (iterator.hasNext()) {
				prices.add(iterator.next().getDouble("unitPrice"));
			}
			double sum = dal.getSparkContext().parallelize(prices).reduce(new Function2<Double, Double, Double>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public Double call(Double v1, Double v2) throws Exception {
					return v1 + v2;
				}
			});
			result.add(new KeyValue<Integer>(tuple2._1, (int) sum / prices.size()));
		}
		// dal.destroy();
		return result;
	}

	public Integer getCityAvg(final String city) {
		int result = 0;
		JavaRDD<Document> rdd = dal.getRDD();
		if (city != null) {
			rdd = rdd.filter(new Function<Document, Boolean>() {

				public Boolean call(Document v1) throws Exception {
					if (v1.containsKey("city")) {
						String c = v1.getString("city");
						if (c != null && c.equals(city) && v1.getDouble("unitPrice") > 0) {
							return true;
						}
					}
					return false;
				}
			});
			JavaRDD<Double> temp = rdd.map(new Function<Document, Double>() {

				public Double call(Document v1) throws Exception {

					return v1.getDouble("unitPrice");
				}
			});
			double sum = temp.reduce(new Function2<Double, Double, Double>() {

				public Double call(Double v1, Double v2) throws Exception {

					return v1 + v2;
				}
			});
			result = (int) (sum / temp.count());
		}
		return result;
	}

	public List<KeyValue<Integer>> priceGrouping(final String city) {
		List<KeyValue<Integer>> result = new ArrayList<KeyValue<Integer>>();
		JavaRDD<Document> rdd = dal.getRDD().filter(new Function<Document, Boolean>() {

			public Boolean call(Document v1) throws Exception {
				if (v1.containsKey("city")) {
					String c = v1.getString("city");
					if (c != null && c.equals(city) && v1.getDouble("unitPrice") > 0) {
						return true;
					}
				}
				return false;
			}
		});
		JavaPairRDD<String, Iterable<Document>> temp = rdd.groupBy(new Function<Document, String>() {

			public String call(Document v1) throws Exception {
				double price = v1.getDouble("unitPrice");
				if (price < 10000) {
					return "10k以下";
				} else if (price > 10000 && price < 20000) {
					return "10k-20k";
				} else if (price > 20000 && price < 30000) {
					return "20k-30k";
				} else if (price > 30000 && price < 40000) {
					return "30k-40k";
				} else if (price > 40000 && price < 50000) {
					return "40k-50k";
				} else {
					return "50k以上";
				}
			}
		});
		List<Tuple2<String, Iterable<Document>>>list=temp.collect();
		for (Tuple2<String, Iterable<Document>> tuple2 : list) {
			int count=0;
			Iterator<Document>iterator=tuple2._2.iterator();
			while (iterator.hasNext()) {
				iterator.next();
				count++;
			}
			result.add(new KeyValue<Integer>(tuple2._1, count));
		}
		return result;
	}

}
