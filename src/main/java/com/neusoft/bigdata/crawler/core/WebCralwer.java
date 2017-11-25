package com.neusoft.bigdata.crawler.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.neusoft.bigdata.common.constant;
import com.neusoft.bigdata.common.bloomfilter.BloomFilter;
import com.neusoft.bigdata.common.bloomfilter.CountBloomFilter;
import com.neusoft.bigdata.domain.Base;

public class WebCralwer {

	private ExecutorService executor = Executors.newCachedThreadPool();

	private LinkedList<String> queue = new LinkedList<String>();
	private LinkedHashSet<String> trash = new LinkedHashSet<String>();
	private IParser<Base> parser;
	private CountBloomFilter queueBloomFilter = new CountBloomFilter();
	private BloomFilter trashBloomFilter = new BloomFilter();

	Pattern pattern = Pattern.compile("^http[s]{0,1}://[^\\s]+[^\\.|jpg|gif|jpeg|png]$");

	public<T extends Base> void setPaeser(IParser<T> parser) {
		this.parser = (IParser<Base>) parser;
	}

	IUrlFilter filter = null;

	/**
	 * 设置url过滤器，所有从网页中抓来的网址都会经过这层过滤，
	 */
	public void setUrlFilter(IUrlFilter filter) {
		this.filter = filter;
	}
	
	private String cookie=null;
	
	StringBuilder builder=new StringBuilder();
	public void setCookie(HashMap<String, String>cookie){
		Iterator<Entry<String, String>>iterator= cookie.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String>item= iterator.next();
			builder.append(item.getKey()).append("=").append(item.getValue()).append(";");
		}
		this.cookie= builder.substring(0, builder.length()-1);
	}
	
	public void setCookie(String cookie){
		this.cookie=cookie;
	}
	
	public void setRoot(String url){
		queue.add(url);
		queueBloomFilter.add(url);
	}

	public void addURL(String url) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(url);
		addAllURL(list);
	}

	public void addAllURL(Collection<String> urls) {
		if (filter != null) {
			urls = filter.filter(urls);
		}
		if (urls==null) {
			return;
		}
		if (!urls.isEmpty()) {
			for (String url : urls) {
				if (!contain(url) && pattern.matcher(url).matches()) {
					queue.add(url);
					queueBloomFilter.add(url);
				}
			}
		}
	}

	private synchronized String getFirstURL() {
		String result = null;
		if (!queue.isEmpty()) {
			result = queue.remove();
			trash.add(result);
		}
		return result;
	}

	public int size() {
		return trash.size();
	}

	public void reset() {
		queueBloomFilter.reset();
		trashBloomFilter.reset();
		parser = null;
		trash.clear();
		queue.clear();
		isRunning=false;
	}

	private boolean contain(String url) {
		return queueBloomFilter.contains(url) || trashBloomFilter.contains(url);
	}

	private static boolean isRunning = false;

	public void start(int thread) {
		if (parser == null) {
			System.out.println("解析器为空");
			return;
		}
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

	private ResponseHandler handler = new ResponseHandler();
	private CloseableHttpClient client = ConnectionManager.getHttpClient();
	/**
	 * 发送HttpRequest请求 获取网页内容
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Document request(String url) throws ClientProtocolException, IOException {
		HttpGet request = new HttpGet(url);
		request.addHeader("User-Agent", constant.User_Agent);
		if (this.cookie!=null) {//设置cookie
			request.addHeader("cookie", this.cookie);
		}
		RequestConfig config = RequestConfig.custom().setConnectTimeout(3000).build();
		request.setConfig(config);
		return client.execute(request, handler);
	}
	
	/**
	 * 获取取网页中所有a标签中的href的值，
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
						Thread.currentThread().sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				} else {
//					System.out.println("is running");
				}
				try {
					Document doc = null;
					//1.获取网页内容
					doc = request(url);
					if(doc==null){
						continue;
					}
					//2.解析 并获得数据
					ArrayList<Base> data = parser.parse(url, doc);
					//3.处理得到的数据
					parser.onCompleted(data);
					//4.获取网页中所有的URL
					catUrl(doc);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	};

}
