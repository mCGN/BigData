package com.neusoft.bigdata.crawler.core;

import java.util.Collection;

public interface IUrlFilter {
	Collection<String> filter(Collection<String>urls);
}
