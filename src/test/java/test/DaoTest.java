package test;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.neusoft.bigdata.dao.impl.Dao;

public class DaoTest {

	public static void main(String[] args) {
		Dao dao=new Dao("test", "students");
		
//		HashMap<String, Object>map=new HashMap<String, Object>();
//		map.put("name", "锤子");
//		map.put("age", 12);
//		dao.Add(map);
		
		Document filter =new Document();
		filter.put("age", new Document("$gt",10));
		FindIterable<Document>result= dao.find(filter);
		for (Document document : result) {
			System.out.println(document.getString("name"));
		}
	}

}
