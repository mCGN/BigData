package test;

import java.io.IOException;
import java.net.SocketException;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.neusoft.bigdata.crawler.core.ConnectionManager;
import com.neusoft.bigdata.crawler.service.impl.ProxyCrawlerService;

public class ServiceTest {

	//
	public static void main(String[] args) {
//		ProxyCrawlerService service=new ProxyCrawlerService();
//		service.CatData("https://proxy.mimvp.com/free.php");
		
//		test();
	}
	
	public static void test(){
		CloseableHttpClient client= ConnectionManager.getHttpClient(new HttpHost("220.179.214.213",808));
		HttpUriRequest request=new HttpGet("https://guangzhou.anjuke.com/");
		try {
			CloseableHttpResponse response= client.execute(request);
			String html= EntityUtils.toString(response.getEntity());
			System.out.println(html);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
	}
	
}
