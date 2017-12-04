package test;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.neusoft.bigdata.crawler.core.ConnectionManager;
import com.neusoft.bigdata.crawler.parser.NewHouseParser;
import com.neusoft.bigdata.crawler.service.impl.AnjukeCrawlerService;
import com.neusoft.bigdata.crawler.service.impl.ProxyCrawlerService;

public class CrawlerServiceTest {

	public static void main(String[] args) {
//		cat();
		
//		try {
//			proxy();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			System.out.println("finally");
//		}
		
//		test1();
		
	}
	
	public static void test1(){
		ProxyCrawlerService service=new ProxyCrawlerService();
		service.CatData(null);
	}
	
	public static void cat(){
		AnjukeCrawlerService service=new AnjukeCrawlerService();
		service.CatData("https://gz.fang.anjuke.com/loupan/all/p2/");
	}
	
	public static void proxy() throws ClientProtocolException, IOException{
		CloseableHttpClient client= ConnectionManager.getHttpClient();
//		https://www.cnblogs.com/jinjiyese153/p/6972331.html
//		https://gz.fang.anjuke.com/loupan/all/p2/
//		https://www.baidu.com/
		HttpGet request=new HttpGet("https://www.cnblogs.com/jinjiyese153/p/6972331.html");
		RequestConfig config=RequestConfig.custom().setProxy(new HttpHost("222.66.22.82", 8090)).build();
		request.setConfig(config);
		request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
		HttpResponse response= client.execute(request);
		int code= response.getStatusLine().getStatusCode();
		System.out.println("responceCode:"+code);
//		String result=EntityUtils.toString(response.getEntity());
//		System.out.println(result);
	}

}
