package com.neusoft.bigdata.crawler.service.filter;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;

import com.neusoft.bigdata.crawler.core.IUrlFilter;

public class AnjukeUrlFilter implements IUrlFilter {

	Pattern pattern=Pattern.compile("https://.+\\.anjuke\\.com/?(loupan|sale|fangyuan/)?(p.+/)?(.+)?");
	
	public Collection<String> filter(Collection<String> urls) {
		Iterator<String>iterator=urls.iterator();
		while (iterator.hasNext()) {
			if (!pattern.matcher(iterator.next()).matches()) {
				iterator.remove();
			}
		}
		return urls;
	}

}
