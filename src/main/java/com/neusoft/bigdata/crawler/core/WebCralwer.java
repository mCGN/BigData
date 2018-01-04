package com.neusoft.bigdata.crawler.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.neusoft.bigdata.crawler.bloomfilter.BloomFilter;
import com.neusoft.bigdata.crawler.bloomfilter.Queue;
import com.neusoft.bigdata.crawler.core.domain.RequestHeaderBase;
import com.neusoft.bigdata.proxy.IpPool;
import com.neusoft.bigdata.proxy.ProxyEntity;

import io.netty.channel.ConnectTimeoutException;

public class WebCralwer<T> extends RequestHeaderBase {

	private ExecutorService executor = Executors.newCachedThreadPool();//线程池
	
	private Queue queue=new Queue();//任务队列
	private BloomFilter trash = new BloomFilter();//垃圾箱
	
	private IParser<T> parser;//解析器
	private IUrlFilter filter = null;//url过滤器
	
	private long time=0;//每次任务完成后 线程休息的时间
	private static boolean isRunning = false;//爬虫是否运行中

//	Pattern pattern = Pattern.compile("^http[s]{0,1}://[^\\s]+[^\\.|jpg|gif|jpeg|png]$");

	public void setPaeser(IParser<T> parser) {
		this.parser = parser;
	}

	
	/**
	 * 设置url过滤器，所有从网页中抓来的网址都会经过这层过滤，
	 */
	public void setUrlFilter(IUrlFilter filter) {
		this.filter = filter;
	}	
	
	/**
	 * 添加URL种子
	 * @param url
	 */
	public void addRoot(String url){
		queue.add(url);
	}
	
	public void addRoot(String[]url){
		for (String string : url) {
			queue.add(string);
		}
	}
	
	/**
	 * 设置每次任务完成后，线程的休眠时间
	 * @param time
	 */
	public void setSleepTime(long time){
		this.time=time;
	}

	public void addURL(String url) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(url);
		addAllURL(list);
	}

	/**
	 * 添加URL
	 * @param urls
	 */
	public void addAllURL(List<String> urls) {
		if (filter != null) {
			urls = filter.filter(urls);
		}
		if (urls==null) {
			return;
		}
		if (!urls.isEmpty()) {
			for (String url : urls) {
				if (!contain(url)) {
					queue.add(url);
				}
			}
		}
	}

	/**
	 * 从任务队列中 拿任务
	 * @return
	 */
	private synchronized String getFirstURL() {
		String result = null;
		if (!queue.isEmpty()) {
			result = queue.remove();
		}
		return result;
	}
	
	/**
	 * 已爬取url移动到trash
	 * @param url
	 */
	private void moveToTrash(String url){
		trash.add(url);
	}

	private void reset() {
		trash.reset();
		parser = null;
		filter=null;
		queue.reset();
		isRunning=false;
	}

	/**
	 * 检测url是否在已收录入队列中
	 */
	private boolean contain(String url) {
		return queue.contains(url) || trash.contains(url);
	}


	/**
	 * 启动爬虫
	 * @param thread 开启的线程数 最小为1
	 */
	public void start(int thread) {
		if (parser == null) {
			System.out.println("解析器为空");
			return;
		}
		if (thread<1) {
			thread=1;
		}
		initConfig();
		isRunning = true;
		for (int i = 0; i < thread; i++) {
			executor.execute(task);
		}
	}

	public void stop() {
		isRunning = false;
	}

	public boolean isRunning() {
		return isRunning;
	}

	List<Header> headers=new ArrayList<Header>();//http-header
	public void initConfig(){//初始化http-header
		headers.add(new BasicHeader(HttpHeaders.ACCEPT, Accept));
		headers.add(new BasicHeader(HttpHeaders.USER_AGENT, UserAgent));
		headers.add(new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE, AcceptLanguage));
		headers.add(new BasicHeader(HttpHeaders.CACHE_CONTROL, CacheControl));
		headers.add(new BasicHeader(HttpHeaders.CONNECTION, Connection));
		headers.add(new BasicHeader("Upgrade-Insecure-Requests", UpgradeInsecureRequests));
		
		if(this.Host!=null){
			headers.add(new BasicHeader(HttpHeaders.HOST, Host));
		}
		if(Referer!=null){
			headers.add(new BasicHeader(HttpHeaders.REFERER, Referer));
		}
		if(AcceptEncoding!=null){
			headers.add(new BasicHeader(HttpHeaders.ACCEPT_ENCODING, AcceptEncoding));
		}
		if (this.Cookie!=null) {//设置cookie
			headers.add(new BasicHeader("Cookie", Cookie));
		}
	}
	
	private ResponseHandler handler = new ResponseHandler();
	/**
	 * 发送HttpRequest请求 获取网页内容
	 * @param url
	 */
	public Document request(String url) throws ClientProtocolException, IOException {
		ProxyEntity entity=IpPool.getIp();//获取代理ip
		CloseableHttpClient client;
		if (entity==null) {
			client=ConnectionManager.getHttpClient();
		}else {
			System.out.println(entity.toString());
			client = ConnectionManager.getHttpClient(new HttpHost(entity.ip,entity.port));
		}
		HttpGet request = new HttpGet(url);
		for (Header header : headers) {
			request.addHeader(header);
		}
		Document doc=null;
		try {
			doc=client.execute(request, handler);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("请求过程中报错："+e.getMessage());
			addRoot(url);
			IpPool.setIpInvalid(entity);
			System.out.println("重新加入");
		}
		return doc;
	}
	
	/**
	 * 获取取网页中所有url，
	 */
	private void catUrl(Document doc) {
		Elements a = doc.getElementsByTag("a");
		ArrayList<String> urls = new ArrayList<String>();
		for (Element e : a) {
			String href = e.attr("href");
			urls.add(href);
		}
		addAllURL(urls);
	}
	
	/**
	 * 爬虫任务
	 */
	private Runnable task = new Runnable() {

		public void run() {
			while (isRunning) {
				String url = getFirstURL();// 获取队列中第一个url
				if (url==null) {// 判断url是否为空
					System.out.println("url is null");
					try {
						Thread.currentThread();
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				} 
				try {
					//1.获取网页内容
					Document doc = request(url);
					if(doc==null){
						continue;
					}
					
					//2.解析 并获得数据
					ArrayList<T> data = parser.parse(url, doc);
					
					if (!data.isEmpty()) {
						//3.处理得到的数据
						parser.onCompleted(data);
					}
					
					//4.获取网页中所有的URL
					catUrl(doc);
					
					moveToTrash(url);
					System.out.println("已访问");
					//5.当前线程睡眠一段时间
					Thread.currentThread();
					Thread.sleep(time);
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

}
