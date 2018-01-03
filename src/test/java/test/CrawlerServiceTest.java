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
import com.neusoft.bigdata.proxy.ProxyEntity;
import com.neusoft.bigdata.utils.FileUtils;

public class CrawlerServiceTest {

	public static void main(String[] args) {
		cat();
		
//		test1();
		
//		try {
//			parse();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public static void test1(){
		ProxyCrawlerService service=new ProxyCrawlerService();
		service.CatData(null);
	}
	
	public static void cat(){
		AnjukeCrawlerService service=new AnjukeCrawlerService();
		service.CatData("https://sz.fang.anjuke.com/loupan/all/p2/");
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
