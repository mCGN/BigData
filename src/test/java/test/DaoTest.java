package test;

import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.neusoft.bigdata.dao.impl.SparkMongoUtils;
import com.neusoft.bigdata.dao.impl.MongoDao;

import junit.framework.TestCase;
import test.domain.Student;

public class DaoTest extends TestCase {

	public void find() {
		MongoDao dao = new MongoDao("test", "students");
		Document filter = new Document();
		filter.put("age", new Document("$gt", 10));
		FindIterable<Document> result = dao.find(filter);
		for (Document document : result) {
			System.out.println(document.getString("name"));
		}
	}

	public void add() {
		MongoDao dao = new MongoDao("test", "students");
		Student student=new Student("红汪汪", 999);
		
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("name", "艾琳");
//		map.put("age", 18);
		dao.insert(student);
	}
	
	public void addAll() {
		MongoDao dao = new MongoDao("test", "students");
		Student student=new Student("红汪汪", 999);
		Student student2=new Student("kot", 123);
		ArrayList<Student>list=new ArrayList<Student>();
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("name", "艾琳");
//		map.put("age", 18);
		dao.insert(student);
	}
	
	public void test1(){
		SparkMongoUtils dal=SparkMongoUtils.Instance("127.0.0.1", "test", "students");
//		dal.write(new Document("name", "楼下小黑"));
	}

}
