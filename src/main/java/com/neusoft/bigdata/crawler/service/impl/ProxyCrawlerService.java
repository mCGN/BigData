package com.neusoft.bigdata.crawler.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.neusoft.bigdata.crawler.core.IParser;
import com.neusoft.bigdata.crawler.core.IUrlFilter;
import com.neusoft.bigdata.crawler.core.WebCralwer;
import com.neusoft.bigdata.crawler.core.utils.Ping;
import com.neusoft.bigdata.crawler.service.ICrawlerService;
import com.neusoft.bigdata.dao.impl.MongoDao;
import com.neusoft.bigdata.proxy.ProxyEntity;

public class ProxyCrawlerService implements ICrawlerService,IParser<ProxyEntity>,IUrlFilter {

	String cookie="PHPSESSID=msn56o4oj7tcs7q0c3sre8sj82; __asc=f4a117741605ac32422e1e4d4d0; __auc=f4a117741605ac32422e1e4d4d0; Hm_lvt_51e3cc975b346e7705d8c255164036b3=1513351226; Hm_lpvt_51e3cc975b346e7705d8c255164036b3=1513351226; UM_distinctid=1605ac32763372-0be4bc23cf10d2-64547029-100200-1605ac3276414f; CNZZDATA1262220370=1250089011-1513346839-%7C1513346839";
	String host="proxy.mimvp.com";
	String referer="http://www.kuaidaili.com/";
	String acceptEncoding="gzip, deflate, br";
	public void CatData(String... url) {
		if (url==null) {
			url[url.length]="http://www.kuaidaili.com/free/intr/1";
		}
//		System.out.println(url);
		WebCralwer<ProxyEntity> cralwer=new WebCralwer<ProxyEntity>();
		cralwer.setRequestHeader(acceptEncoding, cookie, host, null);
		cralwer.addRoot(url);
		cralwer.setPaeser(this);
		cralwer.setSleepTime(5000);
		cralwer.setUrlFilter(this);
		cralwer.start(1);
	}

	public ArrayList<ProxyEntity> parse(String url, Document content) {
//		System.out.println(content.data());
		ArrayList<ProxyEntity>list=new ArrayList<ProxyEntity>();
		Elements trs= content.getElementsByTag("tr");
		for (Element tr : trs) {
			Element host= tr.getElementsByAttributeValue("data-title", "IP").first();
			Element port=tr.getElementsByAttributeValue("data-title", "PORT").first();
			if (host!=null&&port!=null) {
				ProxyEntity entity=new ProxyEntity();
				entity.setIp(host.ownText());
				entity.setPort(Integer.valueOf( port.ownText()));
				list.add(entity);
			}
		}
		return list;
	}

	MongoDao dao=new MongoDao("data", "ip");
	
	public void onCompleted(ArrayList<ProxyEntity> object) {
		for (ProxyEntity proxyEntity : object) {
			System.out.println(proxyEntity.getIp()+":"+proxyEntity.getPort());
			boolean isPing= Ping.ping(proxyEntity.getIp(), 6000);
			if (isPing) {
				dao.insert(proxyEntity);
			}
			System.out.println(isPing);
		}

//		return object.isEmpty()?false:true;
		
	}

	//url filter 例 /free/intr/1/
	Pattern pattern=Pattern.compile("/free/intr/([0-9]+/)?");
	String h="http://www.kuaidaili.com";
	public List<String> filter(List<String> urls) {
		Iterator<String>iterator= urls.iterator();
		Collection<String>a=new ArrayList<String>();
		while (iterator.hasNext()) {
			String url=iterator.next();
			System.out.println(url);
			Matcher matcher= pattern.matcher(url);
			if (!matcher.find()) {
				iterator.remove();
				continue;
			}else {
				a.add(h+url);
			}
		}
		urls.addAll(a);
		return urls;
	}
	
	

}
