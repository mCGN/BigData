package com.neusoft.bigdata.crawler.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
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

	public void setPaeser(IParser<Base> parser) {
		this.parser = parser;
	}

	IUrlFilter filter = null;

	/**
	 * 设置url过滤器，所有从网页中抓来的网址都会经过这层过滤，
	 */
	public void setUrlFilter(IUrlFilter filter) {
		this.filter = filter;
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
	}

	private boolean contain(String url) {
		return queueBloomFilter.contains(url) || trashBloomFilter.contains(url);
	}

	/**
	 * 爬取网页中所有a标签中的href的值，
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

	public Document request(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient client = ConnectionManager.getHttpClient();
		HttpGet request = new HttpGet(url);
		request.addHeader("User-Agent", constant.User_Agent);
		request.addHeader("cookie", constant.cookies);
		RequestConfig config = RequestConfig.custom().setConnectTimeout(3000).build();
		request.setConfig(config);
		return client.execute(request, handler);
	}

	private Runnable task = new Runnable() {
		public void run() {
			while (isRunning) {
				String url = getFirstURL();// 获取第一个url
				if (url == null) {// 判断url是否为空
					System.out.println("url is null");
					try {
						Thread.currentThread().sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					continue;
				} else {
					System.out.println("is running");
				}
				try {
					Document doc = null;
					doc = request(url);
					// doc= Jsoup.connect(url).cookies(constant.getCookieMap())
					// .header("User-Agent",
					// "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36
					// (KHTML, like Gecko) Chrome/55.0.2883.87
					// UBrowser/6.2.3637.220 Safari/537.36")
					// .timeout(5000)
					// .get();
					ArrayList<Base> data = parser.parse(url, doc);
					parser.onCompleted(data);
					catUrl(doc);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	};

}
