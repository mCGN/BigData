package test;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.bson.Document;
import com.neusoft.bigdata.common.Resources;
import com.neusoft.bigdata.crawler.core.IParser;
import com.neusoft.bigdata.crawler.core.IUrlFilter;
import com.neusoft.bigdata.crawler.core.WebCralwer;
import com.neusoft.bigdata.dao.impl.MongoDao;
import test.domain.KeyValue;
import test.parser.MyParser;

public class input {
	
	public static void main(String[] args) {
		try {
			in();
			System.out.println("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		in2();
	}
	
	public  static void in() throws IOException{
		MongoDao dao=new MongoDao("data", "city");
		String input=Resources.getResourceAsString("city.txt");
		String[]temp1= input.split("#");
		for (int i = 0; i < temp1.length; i++) {
			String[] temp2= temp1[i].split("-");
			if (temp2.length<2) {
				continue;
			}
			String[] temp3= temp2[0].split(",");//省和市
			
			String[] temp4=temp2[1].split("\r\n");
			for (int j = 0; j < temp4.length; j++) {
				if (temp4[j].isEmpty()) {
					continue;
				}
				Document doc=new Document();
				doc.put("province",temp3[0]);
				doc.put("city", temp3[1]);
				doc.put("area", temp4[j]);
				dao.insert(doc);
//				System.out.println("province:"+doc.getString("province")+"---city:"+doc.getString("city")+"---area"+doc.getString("area"));
			}
		}
	}
	
	public static void in2(){
		WebCralwer<KeyValue> cralwer=new WebCralwer<KeyValue>();
		cralwer.setUrlFilter(new IUrlFilter() {

			public List<String> filter(List<String> urls) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		IParser<KeyValue> parser=new MyParser();
		cralwer.setPaeser(parser);
		cralwer.addRoot("https://www.anjuke.com/sy-city.html");
		cralwer.start(1);
		
	}
	
	
	
	
	
}
