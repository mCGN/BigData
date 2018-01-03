package action;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import com.neusoft.bigdata.crawler.core.ConnectionManager;

public class ProxyTest {
	public static void main(String[] args) throws ClientProtocolException, IOException {
		proxy("14.125.62.158",3217);
	}
	
	/**
	 * 测试代理ip是否可用
	 * @param ip
	 * @param port
	 */
	public static void proxy(String ip,int port) throws ClientProtocolException, IOException{
		HttpHost proxy= new HttpHost(ip,port);
		CloseableHttpClient client= ConnectionManager.getHttpClient(proxy);
		HttpGet request=new HttpGet("https://www.anjuke.com/sy-city.html");
		request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
		HttpResponse response= client.execute(request);
		int code= response.getStatusLine().getStatusCode();
		System.out.println("responceCode:"+code);
	}
}
