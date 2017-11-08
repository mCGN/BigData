package com.neusoft.bigdata.crawler.core;

import java.util.ArrayList;

import org.jsoup.nodes.Document;

public interface IParser<T> {
	
	ArrayList<T> parse(String url, Document content);
	
	void onCompleted(ArrayList<T> object);
	
}

