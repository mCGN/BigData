package com.neusoft.bigdata.crawler.service.filter;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import com.neusoft.bigdata.crawler.core.IUrlFilter;


public class AnjukeUrlFilter implements IUrlFilter {

	Pattern pattern=Pattern.compile("https://[^\\.]+(\\.fang)?\\.anjuke\\.com(/loupan/all/p.+/)?(/\\?.+)?");
//	Pattern pattern=Pattern.compile("https://.+\\.anjuke\\.com(/loupan/all/p.+/)?"");
	
	public List<String> filter(List<String> urls) {
		Iterator<String>iterator=urls.iterator();
		while (iterator.hasNext()) {
			String next= iterator.next();
			if (!pattern.matcher(next).matches()||next.contains(".html")) {
				iterator.remove();
			}else {
				if (next.endsWith("/")) {
					
				}
			}
		}
		
		return urls;
	}

	public Comparator<String> getComparator() {
		Comparator<String>comparator=new Comparator<String>() {
			
			public int compare(String o1, String o2) {
				
				return 0;
			}
		};
		return null;
	}

}
