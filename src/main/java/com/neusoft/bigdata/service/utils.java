package com.neusoft.bigdata.service;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.neusoft.bigdata.dao.impl.MongoDao;

public class utils {

	private static MongoDao dao2=new MongoDao("data", "city");
	//将地址映射到省份城市区域，例如：[ 广州 天河 ] ——》广东省 广州市 天河区
	static public Document getAreaMsg(String address) {
		ArrayList<String>arr=new ArrayList<String>();
		Matcher matcher= Pattern.compile("[\u4e00-\u9fa5]+").matcher(address.substring(address.indexOf("[")+1, address.indexOf("]")));
		while (matcher.find()) {
			arr.add(matcher.group(0));
		}
		for (int i = 0; i < arr.size(); i++) {
			Document doc=new Document();
			Pattern pattern=Pattern.compile("^"+arr.get(i)+".+");
			doc.put("area",pattern);
			FindIterable<Document>findIterable= dao2.find(doc);
			if (findIterable.first()!=null) {
				Document result= new Document();
				for (Entry<String, Object> entry : findIterable.first().entrySet()) {
					result.put(entry.getKey(), entry.getValue());
				}
				return result;
			}
		}
		return null;
	}
	
}
