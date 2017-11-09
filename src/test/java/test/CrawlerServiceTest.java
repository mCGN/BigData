package test;

import com.neusoft.bigdata.crawler.parser.NewHouseParser;
import com.neusoft.bigdata.crawler.service.impl.CrawlerService;

public class CrawlerServiceTest {

	public static void main(String[] args) {
		CrawlerService service=new CrawlerService();
		service.CatData("https://gz.fang.anjuke.com", NewHouseParser.class);

	}

}
