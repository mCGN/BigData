package com.neusoft.bigdata.crawler.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.neusoft.bigdata.crawler.core.ConnectionManager;
import com.neusoft.bigdata.crawler.core.IParser;
import com.neusoft.bigdata.dao.Instance;
import com.neusoft.bigdata.dao.impl.MongoDao;
import com.neusoft.bigdata.domain.NewHouse;
import com.neusoft.bigdata.service.utils;
import com.neusoft.bigdata.web.socket.NoticSocket;

public class NewHouseParser implements IParser<NewHouse> {

	Pattern numPattern=Pattern.compile("[0-9]+");
	Pattern measurePattern=Pattern.compile("[0-9]+[.]?[0-9]+");
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
				String type=null;
				String measure=null;
				//正则匹配 获取户型和面积
				String huxing=item.getElementsByClass("huxing").text().trim();
				Matcher matcher=houseTypePattern.matcher(huxing);
				if (matcher.find()) {
				 	type= matcher.group(1).trim();
				 	measure=matcher.group(2).trim();
				}
				
				String name = item.getElementsByClass("items-name").html().trim();
				String address = item.getElementsByClass("address").text().trim();
				String unitPrice = item.getElementsByClass("price").text().trim();
				Double price=getPrice(unitPrice, measure);
				
				String tag = item.getElementsByClass("tag").text().trim();
				String url=item.attr("data-link").trim();
				
				if (!name.isEmpty()) {
					org.bson.Document msg= utils.getAreaMsg(address);
					String province=null;
					String city=null;
					String area=null;
					if (msg!=null) {
						province=msg.getString("provice");
						city=msg.getString("city");
						area=msg.getString("area");
					}
					NewHouse house=new NewHouse(name, tag, price, type, address,measure,province,city,area);
					house.setUrl(url);
					data.add(house);
				}
			}
		}
		return data;
	}
	
	private Double getPrice(String unitPrice,String measure){
		Double price=-1.0;
		if(unitPrice!=null){
			Matcher matcher=measurePattern.matcher(unitPrice);//匹配价格的数字
			if (unitPrice.contains("万")) {//以万为单位
				if (matcher.find()) {
					Double i=Double.valueOf(matcher.group());
					i=i*10000;
					if (measure==null) {
						price=-1.0;
					}else {
						Matcher matcher2=measurePattern.matcher(measure);
						if (matcher2.find()) {
							double j=Double.valueOf(matcher2.group());
							price=i/j;
						}
					}
				}
			}else{//以元为单位
				if (matcher.find()) {
					price=Double.valueOf(matcher.group());
				}
			}
		}
		return price;
	}
	
	MongoDao dao=Instance.getNewHouseDao();
	
	public  void onCompleted(ArrayList<NewHouse> object) {
		Iterator<NewHouse>iterator=object.iterator();
		while (iterator.hasNext()) {
			FindIterable<org.bson.Document>a=dao.find(new org.bson.Document("url", iterator.next().url));
			if(a.first()!=null){
				iterator.remove();
			}
		}
		dao.insertAll(object);
		if (!object.isEmpty()) {
			recountMsg();
		}
	}
	
	public void recountMsg(){
		HttpPost httpPost=new HttpPost("http://localhost:8080/bigdata/notice");
		List<NameValuePair> parameters=new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("action", "recount"));
		try {
			UrlEncodedFormEntity entity=new UrlEncodedFormEntity(parameters);
			httpPost.setEntity(entity);
			CloseableHttpClient client= ConnectionManager.getHttpClient();
			client.execute(httpPost);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
