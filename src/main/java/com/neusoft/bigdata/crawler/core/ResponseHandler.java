package com.neusoft.bigdata.crawler.core;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ResponseHandler implements org.apache.http.client.ResponseHandler<Document> {

	/**
	 * 处理http请求的responce,读取返回的html生成document对象返回
	 */
	public Document handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		Document document = null;
		if (response.getStatusLine().getStatusCode() == 200) {
			document = Jsoup.parse(EntityUtils.toString(response.getEntity()));
		}
		return document;
	}

}
