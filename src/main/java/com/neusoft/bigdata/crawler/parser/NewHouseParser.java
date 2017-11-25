package com.neusoft.bigdata.crawler.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.BSONObject;
import org.bson.types.ObjectId;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.neusoft.bigdata.crawler.core.IParser;
import com.neusoft.bigdata.dao.BeanMapUtils;
import com.neusoft.bigdata.dao.Instance;
import com.neusoft.bigdata.dao.impl.Dao;
import com.neusoft.bigdata.dao.impl.SparkDao;
import com.neusoft.bigdata.domain.NewHouse;
import com.neusoft.bigdata.service.utils;

public class NewHouseParser implements IParser<NewHouse> {

	Pattern houseTypePattern= Pattern.compile("户型：\\s(.+)\\s建筑面积：(.+)");
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
					BSONObject msg= utils.getAreaMsg(address);
					ObjectId areaId=msg==null?null:(ObjectId)msg.get("_id");
					NewHouse house=new NewHouse(name, tag, price, type, address,area,areaId);
					house.setUrl(url);
					data.add(house);
				}
			}
		}
		return data;
	}
	
	Dao dao=Instance.getNewHouseDao();
	
	public  void onCompleted(ArrayList<NewHouse> object) {
		Iterator<NewHouse>iterator=object.iterator();
		while (iterator.hasNext()) {
			FindIterable<org.bson.Document>a=dao.find(new org.bson.Document("url", iterator.next().url));
			if(a.first()!=null){
				iterator.remove();
			}
		}
		dao.insertAll(object);
	}

}
