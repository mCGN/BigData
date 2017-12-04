package test;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.neusoft.bigdata.dao.impl.Dao;

import junit.framework.TestCase;

public class _idTest extends TestCase {
	
	public void test(){
		Dao dao=new Dao("test", "students");
		Document filter =new Document();
		filter.put("age", new Document("$gt",10));
		FindIterable<Document>result= dao.find(filter);
		for (Document document : result) {
			System.out.println(document.getObjectId("_id"));
		}
	}
	
}
