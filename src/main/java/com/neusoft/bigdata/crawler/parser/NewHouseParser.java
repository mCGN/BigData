package com.neusoft.bigdata.crawler.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.neusoft.bigdata.crawler.core.IParser;
import com.neusoft.bigdata.dao.impl.Dao;
import com.neusoft.bigdata.domain.NewHouse;
import com.neusoft.bigdata.utils.BeanMapUtils;

public class NewHouseParser implements IParser<NewHouse> {

//	Pattern chinesPattern = Pattern.compile("[\u4e00-\u9fa5]+");// 匹配中文字符串
//	Pattern numberPattern = Pattern.compile("[1-9]+");// 匹配正整数

	Pattern houseTypePattern= Pattern.compile("户型：\\s(.+)\\s建筑面积：(.+)");
//	Pattern pattern1=Pattern.compile("<span>(.+?)</span>");
	Gson gson= new Gson();

	public  ArrayList<NewHouse> parse(String url, Document doc) {
		/**
		 * 根据网页的格式进行 针对性的数据爬取
		 */
		// TODO 爬取数据
		ArrayList<NewHouse> data = catNewHouseData(doc);
		return data;
	}

	/**
	 * 抓取新楼盘的数据
	 */
	private ArrayList<NewHouse> catNewHouseData(Element doc) {
		ArrayList<NewHouse>data=new ArrayList<NewHouse>();
		// 新楼盘
		Elements item_mod = doc.getElementsByClass("item-mod");
		if (!item_mod.isEmpty()) {
			for (Element item : item_mod) {
				String name = item.getElementsByClass("items-name").html().trim();
				String address = item.getElementsByClass("address").text().trim();
				String price = item.getElementsByClass("price").text().trim();
				String tag = item.getElementsByClass("tag").text().trim();
				String url=item.attr("data-link").trim();
				String type=null;
				String area=null;
				//正则匹配 获取户型和面积
				String huxing=item.getElementsByClass("huxing").text().trim();
				Matcher matcher=houseTypePattern.matcher(huxing);
				if (matcher.find()) {
				 	type= matcher.group(1).trim();
				 	area=matcher.group(2).trim();
				}
				if (!name.isEmpty()) {
					NewHouse house=new NewHouse(name, tag, price, type, address,area);
					long timeStamp=System.currentTimeMillis();
					house.set(url, timeStamp);
					data.add(house);
				}
			}
		}
		return data;
	}
	
	Dao dao=new Dao("data", "new_house");
	
	public  void onCompleted(ArrayList<NewHouse> object) {
		for (NewHouse house : object) {
//			System.out.println(house.toString());
			System.out.println("url------:"+house.url);
			FindIterable<org.bson.Document>a=dao.find(new org.bson.Document("url", house.url));
			if(a.first()==null){
				HashMap< String, Object>map= (HashMap<String, Object>) BeanMapUtils.beanToMap(house);
//				System.out.println(map.size());
//				dao.add(map);
//				System.out.println("add:"+house.url);
			}
		}
	}

}
