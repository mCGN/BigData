package com.neusoft.bigdata.crawler.core;

import java.util.List;

public interface IUrlFilter {
	List<String> filter(List<String>urls);
}
