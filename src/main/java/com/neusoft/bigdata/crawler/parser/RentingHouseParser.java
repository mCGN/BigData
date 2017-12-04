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
import com.neusoft.bigdata.domain.RentingHouse;
import com.neusoft.bigdata.service.utils;

public class RentingHouseParser implements IParser<RentingHouse> {

	Pattern numPattern = Pattern.compile("[0-9]+");
	Pattern decimalPattern = Pattern.compile("[0-9]+[.]?[0-9]+");

	// Pattern chinesPattern = Pattern.compile("[\u4e00-\u9fa5]+");// 匹配中文字符串
	// Pattern numberPattern = Pattern.compile("[1-9]+");// 匹配正整数

	// Pattern houseTypePattern= Pattern.compile("户型：\\s(.+)\\s建筑面积：(.+)");
	// Pattern pattern1=Pattern.compile("<span>(.+?)</span>");
	Gson gson = new Gson();

	public ArrayList<RentingHouse> parse(String url, Document doc) {
		/**
		 * 根据网页的格式进行 针对性的数据爬取
		 */
		// TODO 爬取数据
		ArrayList<RentingHouse> data = catRentingHouseData(doc);

		// onCompleted(data);
		return data;

	}

	public void parse(String url, String content) {

	}

	private ArrayList<RentingHouse> catRentingHouseData(Element doc) {
		ArrayList<RentingHouse> data = new ArrayList<RentingHouse>();
		Elements zu = doc.getElementsByClass("zu-itemmod");
		if (!zu.isEmpty()) {
			for (Element item : zu) {
				String url = zu.attr("link");
				String name = null;
				String unitPrice = null;
				String type = null;
				String address = null;
				String floor = null;
				String tag = null;
				Elements infos = item.getElementsByClass("zu-info");
				if (!infos.isEmpty()) {
					Element info = infos.get(0);
					name = info.child(0).text().trim();
					String[] arr = info.child(1).text().split("|");
					if (arr.length >= 4) {
						type = arr[0];
						tag = arr[1] + " " + arr[2];
						floor = arr[3];
					}
					address = info.child(2).text().trim();
				}
				unitPrice = item.getElementsByClass("zu-side").text().trim();
				Double price = -1.0;
				if (!unitPrice.isEmpty()) {
					Matcher matcher = decimalPattern.matcher(unitPrice);
					if (matcher.find()) {
						price = Double.valueOf(matcher.group());
					}
				}
				if (name != null) {
//					BSONObject msg = utils.getAreaMsg(address);
//					ObjectId areaId = msg == null ? null : (ObjectId) msg.get("_id");
					RentingHouse house = new RentingHouse(name, tag, price, type, address, floor);
					// long timeStamp=System.currentTimeMillis();
					house.setUrl(url);
					data.add(house);
				}
			}
		}
		return data;
	}

	public void onCompleted(ArrayList<RentingHouse> object) {
		// TODO Auto-generated method stub

	}

}
