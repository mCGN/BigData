package com.neusoft.bigdata.crawler.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.BSONObject;
import org.bson.types.ObjectId;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.neusoft.bigdata.crawler.core.IParser;
import com.neusoft.bigdata.domain.OldHouse;
import com.neusoft.bigdata.service.utils;

public class OldHouseParser implements IParser<OldHouse> {


	Pattern decimalPattern=Pattern.compile("[0-9]+[.]?[0-9]+");
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
				Double total=-1.0;
				Double unit=-1.0;
				Matcher matcher1= decimalPattern.matcher(totalPrice);
				if (matcher1.find()) {
					total=Double.valueOf(matcher1.group())*10000;
				}
				Matcher matcher2= decimalPattern.matcher(unitPrice);
				if (matcher2.find()) {
					unit=Double.valueOf(matcher2.group())*10000;
				}
				String address=house.getElementsByClass("comm-address").text().trim();
				Elements details= house.child(1).getElementsByClass("details-item");
				String type = null;
				String measure=null;
				String floor=null;
				String yrb=null;
				if (!details.isEmpty()) {
					String str_details=details.get(0).html();
					Matcher matcher= pattern.matcher(str_details);
					if (matcher.find()) {
						type=matcher.group(1).trim();
					}
					if (matcher.find()) {
						measure=matcher.group(1).trim();
					}
					if (matcher.find()) {
						floor=matcher.group(1).trim();
					}
					if (matcher.find()) {
						yrb=matcher.group(1).substring(0,matcher.group(1).indexOf("年")).trim();
					}
				}
				if (name!=null) {
//					BSONObject msg= utils.getAreaMsg(address);
//					ObjectId areaId=msg==null?null:(ObjectId)msg.get("_id");
					OldHouse oldHouse=new OldHouse(name, tag, unit, total, type, address, measure, floor, yrb);
//					long timeStamp=System.currentTimeMillis();
					oldHouse.setUrl(url);
					data.add(oldHouse);
				}
				
			}
		}
		return data;
	}


	public  void onCompleted(ArrayList<OldHouse> object) {
		
	}

}
