package com.neusoft.bigdata.crawler.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.neusoft.bigdata.crawler.core.IParser;
import com.neusoft.bigdata.domain.OldHouse;

public class OldHouseParser implements IParser<OldHouse> {

//	Pattern chinesPattern = Pattern.compile("[\u4e00-\u9fa5]+");// 匹配中文字符串
//	Pattern numberPattern = Pattern.compile("[1-9]+");// 匹配正整数
//
//	Pattern houseTypePattern= Pattern.compile("户型：\\s(.+)\\s建筑面积：(.+)");
	Pattern pattern=Pattern.compile("<span>(.+?)</span>");
	Gson gson= new Gson();

	
	public  ArrayList<OldHouse> parse(String url, Document doc) {
		/**
		 * 根据网页的格式进行 针对性的数据爬取
		 */
		// TODO 爬取数据
		ArrayList<OldHouse>data= catOldHouseData(doc);
//		onCompleted(data);
		return data;

	}

	public void parse(String url, String content) {

	}

	/**
	 * 抓取二手房的数据
	 * 
	 */
	private ArrayList<OldHouse> catOldHouseData(Element doc) {
		ArrayList<OldHouse>data=new ArrayList<OldHouse>();
		Elements houses = doc.getElementsByClass("list-item");
		if (!houses.isEmpty()) {
			for (Element house : houses) {
//				System.out.println("====================================");
				String name= house.getElementsByClass("house-title").text();
				String url= house.getElementsByClass("houseListTitle").attr("href");
				if (url!=null&url.contains("?")) {
					url=url.substring(0, url.indexOf("?"));
				}
//				System.out.println(name);
				String tag= house.child(1).getElementsByClass("tags-bottom").text().trim();
				String totalPrice =house.getElementsByClass("price-det").text().trim();
				String unitPrice=house.getElementsByClass("unit-price").text().trim();
				String address=house.getElementsByClass("comm-address").text().trim();
				Elements details= house.child(1).getElementsByClass("details-item");
				String type = null;
				String area=null;
				String floor=null;
				String yrb=null;
				if (!details.isEmpty()) {
					String str_details=details.get(0).html();
					Matcher matcher= pattern.matcher(str_details);
					if (matcher.find()) {
						type=matcher.group(1).trim();
					}
					if (matcher.find()) {
						area=matcher.group(1).trim();
					}
					if (matcher.find()) {
						floor=matcher.group(1).trim();
					}
					if (matcher.find()) {
						yrb=matcher.group(1).substring(0,matcher.group(1).indexOf("年")).trim();
					}
				}
				if (name!=null) {
					OldHouse oldHouse=new OldHouse(name, tag, unitPrice, totalPrice, type, address, area, floor, yrb);
					long timeStamp=System.currentTimeMillis();
					oldHouse.set(url, timeStamp);
					data.add(oldHouse);
				}
				
			}
		}
		return data;
	}


	public  void onCompleted(ArrayList<OldHouse> object) {
		
	}

}
