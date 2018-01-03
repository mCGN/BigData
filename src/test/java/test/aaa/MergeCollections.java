package test.aaa;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.neusoft.bigdata.dao.impl.MongoDao;

public class MergeCollections {
	
	public static void main(String[] args) {
//		merge();
	}
	
	//将city和new_house合并
	public static void merge(){
		MongoDao dao1=new MongoDao("data", "city");
		MongoDao dao=new MongoDao("data", "new_house");
		Document filter=new Document();
		FindIterable<Document>iterable= dao.find(filter);
		for (Document document : iterable) {
			ObjectId areaId= document.getObjectId("areaId");
			if (areaId!=null) {
				ObjectId id=document.getObjectId("_id");
				Document filter1=new Document("_id", id);
				
				Document areaEntity= dao1.find(new Document("_id",areaId)).first();
				Document doc=new Document();
				doc.append("province",areaEntity.getString("province"));
				doc.append("city", areaEntity.getString("city"));
				doc.append("area", areaEntity.getString("area"));
				
				Document update=new Document("$set",doc);
				dao.update(filter1, update);
			}
		}
	}
	
}
