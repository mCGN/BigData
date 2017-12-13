package com.neusoft.bigdata.crawler.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
import com.neusoft.bigdata.domain.ProxyEntity;

public class ProxyCrawlerService implements ICrawlerService,IParser<ProxyEntity> {

	public void CatData(String url) {
		if (url==null) {
			url="http://www.kuaidaili.com/free/intr/";
		}
		WebCralwer cralwer=new WebCralwer();
		cralwer.setPaeser(this);
		cralwer.setRoot(url);
		cralwer.setSleepTime(5000);
		cralwer.setUrlFilter(new IUrlFilter() {
			
			Pattern pattern=Pattern.compile("/free/intr/([0-9]+/)?");
			String h="http://www.kuaidaili.com";
			Collection<String>a=new ArrayList<String>();
			public Collection<String> filter(Collection<String> urls) {
				Iterator<String>iterator= urls.iterator();
				while (iterator.hasNext()) {
					String url=iterator.next();
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
		});
		cralwer.start(2);
	}

	public ArrayList<ProxyEntity> parse(String url, Document content) {
		ArrayList<ProxyEntity>list=new ArrayList<ProxyEntity>();
		Elements trs= content.getElementsByTag("tr");
		for (Element tr : trs) {
			Element host= tr.getElementsByAttributeValue("data-title", "IP").first();
			Element port=tr.getElementsByAttributeValue("data-title", "PORT").first();
			if (host!=null&&port!=null) {
				ProxyEntity entity=new ProxyEntity();
				entity.setHost(host.ownText());
				entity.setPort(Integer.valueOf( port.ownText()));
				list.add(entity);
			}
		}
		return list;
	}

	public void onCompleted(ArrayList<ProxyEntity> object) {
		for (ProxyEntity proxyEntity : object) {
			System.out.println(proxyEntity.getHost()+":"+proxyEntity.getPort());
			boolean isPing= Ping.ping(proxyEntity.getHost(), 6000);
			System.out.println(isPing);
		}
		
	}
	
	

}
