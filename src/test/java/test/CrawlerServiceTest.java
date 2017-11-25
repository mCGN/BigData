package test;

import com.neusoft.bigdata.crawler.parser.NewHouseParser;
import com.neusoft.bigdata.crawler.service.impl.AnjukeCrawlerService;

public class CrawlerServiceTest {

	public static void main(String[] args) {
		AnjukeCrawlerService service=new AnjukeCrawlerService();
		service.CatData("https://gz.fang.anjuke.com", NewHouseParser.class);

	}

}
