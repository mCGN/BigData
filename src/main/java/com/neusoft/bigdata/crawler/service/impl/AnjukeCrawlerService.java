package com.neusoft.bigdata.crawler.service.impl;

import com.neusoft.bigdata.crawler.core.IParser;
import com.neusoft.bigdata.crawler.core.WebCralwer;
import com.neusoft.bigdata.crawler.core.domain.Base;
import com.neusoft.bigdata.crawler.parser.NewHouseParser;
import com.neusoft.bigdata.crawler.service.ICrawlerService;
import com.neusoft.bigdata.crawler.service.filter.AnjukeUrlFilter;
import com.neusoft.bigdata.domain.NewHouse;

public class AnjukeCrawlerService implements ICrawlerService {
	
	private static String cookie="sessid=2966103A-C38D-687F-A08C-SX1019093018; _stat_guid=5AB2028D-0A6D-88D0-4D4E-101540FA89B7; _prev_stat_guid=DE5FA7DB-53AA-C2D0-F7B1-SX1019093018; als=0; ajk_member_captcha=bb6e0cebbd6f22d39775a2684dd2377e; lps=http%3A%2F%2Fbeijing.anjuke.com%2Fprop%2Fview%2FA1003994259%3Ffrom%3Dfilter-saleMetro%26spread%3Dfiltersearch%26position%3D1%26kwtype%3Dfilter%26now_time%3D1508551437%7C; propertys=glr2xf-oy98sb_g601uf-oy246z_ggafq9-oy2434_gfsi7b-oy2410_gdpzys-oy1wru_gfssm2-oy1whf_; _ga=GA1.2.763433404.1508376911; _gid=GA1.2.497129673.1508376911; isp=true; lp_lt_ut=b68e61ea333655669a09340cb6c5be4e; ctid=14; __xsptplusUT_8=1; 58tj_uuid=04404f21-848b-4fb3-bbdc-0378112466f2; new_session=0; init_refer=; new_uv=17; Hm_lvt_c5899c8768ebee272710c9c5f365a6d8=1508376623; Hm_lpvt_c5899c8768ebee272710c9c5f365a6d8=1508734890; _stat_rfpn=Aifang_Web_Loupan_View_IndexPage_tracklog; __xsptplus8=8.21.1508732590.1508734890.19%232%7Cwww.baidu.com%7C%7C%7C%25E4%25B8%25AD%25E5%259B%25BD%25E6%2588%25BF%25E4%25BB%25B7%7C%23%232V0dE2NeDkTOTA2ahuO068VWOJaPRFFe%23; aQQ_ajkguid=DE5FA7DB-53AA-C2D0-F7B1-SX1019093018; twe=2";

	public AnjukeCrawlerService(){
		cralwer=new WebCralwer();
	}
	
	private  WebCralwer cralwer;
	
	/***
	 * 
	 * @param mClass 实现了IParser接口的类
	 */
	@SuppressWarnings("unchecked")
	public  void CatData(String url) {
		if (cralwer.isRunning()) {
			return;
		}
		IParser<NewHouse> parser=new NewHouseParser();
		cralwer.setPaeser(parser);
		cralwer.setUrlFilter(new AnjukeUrlFilter());
		cralwer.setRoot(url);
		cralwer.setCookie(cookie);
		cralwer.start(5);
	}
	
	public void setSleepTime(long time){
		cralwer.setSleepTime(time);
	}
	
	public int size(){
		return cralwer.size();
	}
	
	public void reset(){
//		cralwer.reset();
	}
	
	public void stop(){
		cralwer.stop();
	}
	
}
