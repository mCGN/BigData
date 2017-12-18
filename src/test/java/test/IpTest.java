package test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.neusoft.bigdata.crawler.core.ConnectionManager;
import com.neusoft.bigdata.dao.impl.Dao;
import com.neusoft.bigdata.domain.ProxyEntity;

public class IpTest {

	public static void main(String[] args) {
		Dao dao=new Dao("data", "ip");
		Bson filter=new Document();
		FindIterable<Document>iterable= dao.find(filter);
		
		for (Document entity : iterable) {
//			System.out.println(0);
			CloseableHttpClient client= ConnectionManager.getHttpClient(entity.getString("host"), entity.getInteger("port"));
			HttpGet get=new HttpGet("https://gz.fang.anjuke.com/loupan/all/p2/");
			get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3100.0 Safari/537.36");
//			get.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//			get.addHeader("Accept-Encoding","gzip, deflate, br");
			try {
//				System.out.println(entity.getString("host")+":"+ entity.getInteger("port"));
				CloseableHttpResponse response= client.execute(get);
//				System.out.println(1);
				if (response.getStatusLine().getStatusCode()==200) {
					System.out.println("code=200");
					System.out.println(entity.getString("host")+":"+ entity.getInteger("port"));
				}else{
					System.out.println("code !=200");
//					dao.delete(new Document("_id", entity.getObjectId("_id")));
				}
				
			} catch (Exception e) {
				dao.delete(new Document("_id", entity.getObjectId("_id")));
				e.printStackTrace();
			}
			finally {
				if (client!=null) {
					try {
						client.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
