package test.rdd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;

import junit.framework.TestCase;
import scala.Tuple2;
import test.domain.AvgCount;

public class Test extends TestCase{
	
	public static void main(String[] args) {
//		test1();
		test2();
	}
	
	public static void test1(){
		int length=10;
		List<Tuple2<String, Integer>>list=new ArrayList<Tuple2<String,Integer>>();
		for (int i = 0; i < length; i++) {
			list.add(new Tuple2<String, Integer>(i+"", i));
		}
		SparkContext conf=new SparkContext("local","app name");
		JavaSparkContext sc=new JavaSparkContext(conf);
		JavaPairRDD<String, Integer>rdd= sc.parallelizePairs(list);
		AvgCount zeroValue=new AvgCount(1,0);
		
		Function2<AvgCount, Tuple2<String, Integer>, AvgCount> seqOp=new Function2<AvgCount, Tuple2<String,Integer>, AvgCount>() {
			
			public AvgCount call(AvgCount v1, Tuple2<String, Integer> v2) throws Exception {
				v1.num+=v2._2;
				v1.count=v1.count+1;
				return v1;
			}
		};
		Function2<AvgCount, AvgCount, AvgCount> combOp=new Function2<AvgCount, AvgCount, AvgCount>() {
			/***
			 * v1 是初始值
			 * v2 是输入数据经过计算的到的结果
			 */
			public AvgCount call(AvgCount v1, AvgCount v2) throws Exception {
//				v1.num+=v2.num;
//				v1.count+=v2.count;
				System.out.println(v1.num+"----"+v1.count);
				System.out.println(v2.num+"----"+v2.count);
				return v1;
			}
		};
		AvgCount i= rdd.aggregate(zeroValue, seqOp, combOp);
//		System.out.println("num="+i.num+";count="+i.count);
	}
	
	/**
	 * rdd groupBy 测试
	 */
	public static void test2(){
		int length=10;
		List<Tuple2<String, BSONObject>>list=new ArrayList<Tuple2<String,BSONObject>>();
		for (int i = 0; i < length; i++) {
			list.add(new Tuple2<String, BSONObject>(i+"", new BasicBSONObject("num", i)));
		}
		SparkContext conf=new SparkContext("local","app name");
		JavaSparkContext sc=new JavaSparkContext(conf);
		JavaPairRDD<String, BSONObject>rdd= sc.parallelizePairs(list);
		JavaPairRDD<String, Iterable<Tuple2<String, BSONObject>>>result= rdd.groupBy(new Function<Tuple2<String,BSONObject>, String>() {

			public String call(Tuple2<String, BSONObject> v1) throws Exception {
				if (Integer.valueOf(v1._2.get("num").toString())>5) {
					return "大";
				}else {
					return "小";
				}
//				return null;
			}
		});
		List<Tuple2<String, Iterable<Tuple2<String, BSONObject>>>> a= result.collect();
		for (Tuple2<String, Iterable<Tuple2<String, BSONObject>>> tuple2 : a) {
			Iterator<Tuple2<String, BSONObject>>iterator=tuple2._2.iterator();
			System.out.println(tuple2._1);
			while (iterator.hasNext()) {
				Tuple2< String, BSONObject>item= iterator.next();
				System.out.println(item._1+"-----"+item._2);
			} 
		}
		
	}
}
