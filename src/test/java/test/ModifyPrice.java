package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.neusoft.bigdata.dao.impl.Dao;

public class ModifyPrice {


	static Pattern numPattern=Pattern.compile("[0-9]+");

	static Pattern measurePattern=Pattern.compile("[0-9]+[.]?[0-9]+");
	
	public static void main(String[] args) {
		Dao dao=new Dao("data", "new_house");
		Document doc=new Document();
		
		FindIterable<Document>iterable= dao.find(doc);
		for (Document document : iterable) {
			String price= document.getString("unitPrice");
			double temp=0;
			if (price.isEmpty()) {
				temp=-1;
			}else {
				Matcher matcher= numPattern.matcher(price);
				if (price.contains("万")) {
					if (matcher.find()) {
						double i=Double.valueOf(matcher.group());
						i=i*10000;
						String measure= document.getString("measure");
						if (measure.isEmpty()) {
							continue;
						}
						Matcher matcher2=measurePattern.matcher(measure);
						if (matcher2.find()) {
							double j=Double.valueOf(matcher2.group());
							temp=i/j;
						}
					}
				}else{
					if (matcher.find()) {
						double i=Double.valueOf(matcher.group());
						temp=i;
					}
				}
			}
			System.out.println(temp);
			Document filter=new Document("_id",document.getObjectId("_id"));
			Document update=new Document("$set",new Document("unitPrice",temp));
			dao.update(filter, update);
		}
		System.out.println("ok");
	}

}
