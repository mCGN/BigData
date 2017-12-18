package test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.neusoft.bigdata.crawler.core.ConnectionManager;
import com.neusoft.bigdata.crawler.core.IParser;
import com.neusoft.bigdata.crawler.parser.NewHouseParser;
import com.neusoft.bigdata.crawler.service.impl.AnjukeCrawlerService;
import com.neusoft.bigdata.crawler.service.impl.ProxyCrawlerService;
import com.neusoft.bigdata.domain.ProxyEntity;
import com.neusoft.bigdata.utils.FileUtils;

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
		
		try {
			parse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void test1(){
		ProxyCrawlerService service=new ProxyCrawlerService();
		service.CatData(null);
	}
	
	public static void cat(){
		AnjukeCrawlerService service=new AnjukeCrawlerService();
		service.CatData("https://gz.fang.anjuke.com/loupan/all/p2/");
	}
	
	/**
	 * 测试ip是否可用
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void proxy() throws ClientProtocolException, IOException{
		CloseableHttpClient client= ConnectionManager.getHttpClient("220.179.214.213",808);
//		https://www.cnblogs.com/jinjiyese153/p/6972331.html
//		https://gz.fang.anjuke.com/loupan/all/p2/
//		https://www.baidu.com/
		HttpGet request=new HttpGet("https://www.cnblogs.com/jinjiyese153/p/6972331.html");
		RequestConfig config=RequestConfig.custom().setProxy(new HttpHost("121.232.148.189",9000)).build();
//		request.setConfig(config);
		request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
		HttpResponse response= client.execute(request);
		int code= response.getStatusLine().getStatusCode();
		System.out.println("responceCode:"+code);
//		String result=EntityUtils.toString(response.getEntity());
//		System.out.println(result);
	}
	
	public static  void  parse() throws IOException{
		IParser<ProxyEntity> parser=new ProxyCrawlerService();
		File dir=new File("E:/ip/");
		ArrayList<String>paths= FileUtils.getAllFilePath(dir);
		for (String string : paths) {
			System.out.println(string);
			String html= FileUtils.read(new File(string));
			Document content= Jsoup.parse(html);
			ArrayList<ProxyEntity>ips= parser.parse("", content);
			parser.onCompleted(ips);
		}
		
		
	}

}
