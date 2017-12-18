package test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.BSONObject;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.DistinctIterable;
import com.neusoft.bigdata.dao.BeanMapUtils;
import com.neusoft.bigdata.dao.impl.Dao;
import com.neusoft.bigdata.service.utils;
import com.neusoft.bigdata.service.impl.NewHouseService;

import junit.framework.TestCase;
import test.domain.Student;

public class Test extends TestCase {

	public void test() { 

		ObjectId id = new ObjectId();
		System.out.println(id.toString());
	}

	/**
	 * 测试BeanMapUtils
	 */
	public void test1() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "小明");
		map.put("age", 12);
		Student bean = new Student();
		System.out.println(bean.getName());
		BeanMapUtils.mapToBean(map, bean);
		System.out.println(bean.getName());
		System.out.println(bean.getAge());
	}

	public void test2() {
		Document object= utils.getAreaMsg("[ 黄埔 长岭居 ] 长岭居东部禾丰小学旁");
		System.out.println(object.get("_id"));
	}
	
	/**
	 * 不间断空格 测试
	 */
	public void test3(){
		char character='\u00A0';//unicode编码
		String s=character+"棠红棣雪"+character;
		Matcher matcher= Pattern.compile("[^\u00A0]+").matcher(s);
		while (matcher.find()) {
			System.out.println(matcher.group(0));
		}
	}
	
	/**
	 * 小数 正则匹配
	 */
	public void test4(){

		Pattern measurePattern=Pattern.compile("[0-9]+[.]?[0-9]+");
		
		Matcher matcher= measurePattern.matcher("23.33 44.444 22 4.3 33.");
		while (matcher.find()) {
			System.out.println(matcher.group());
		}
		
	}
	
	public void test5(){
		Dao dao=new Dao("data", "city");
		DistinctIterable<String>iterable= dao.distinct("province");
		for (String string : iterable) {
			System.out.println(string);
		}
	}
	
	public void test6(){
		try {
			System.out.println("1");
			Thread.currentThread();
			Thread.sleep(0);
			System.out.println("2");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
