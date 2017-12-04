package test.parser;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.neusoft.bigdata.crawler.core.IParser;
import com.neusoft.bigdata.utils.MatcherUtils;

import test.input;
import test.domain.KeyValue;

public class MyParser implements IParser<KeyValue>{

	public ArrayList<KeyValue> parse(String url, org.jsoup.nodes.Document content) {
		ArrayList<KeyValue>list=new ArrayList<KeyValue>();
		Elements citys= content.getElementsByClass("city_list");
		for (Element city : citys) {
			Elements elements= city.getElementsByTag("a");
			for (Element element : elements) {
				String v= element.ownText();
				String k=MatcherUtils.matchCity(element.attr("href"));
				KeyValue kv=new KeyValue(k, v);
				list.add(kv);
			}
		}
		return list;
	}

	public void onCompleted(ArrayList<KeyValue> object) {
		for (KeyValue keyValue : object) {
			System.out.println(keyValue.toString());
		}
	}
	
}