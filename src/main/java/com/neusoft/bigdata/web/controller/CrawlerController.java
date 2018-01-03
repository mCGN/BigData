package com.neusoft.bigdata.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.neusoft.bigdata.crawler.service.impl.AnjukeCrawlerService;

@Controller
public class CrawlerController {


	static AnjukeCrawlerService  service=new AnjukeCrawlerService();
	
	@RequestMapping(value={"/crawler"})
	public ModelAndView crawler(HttpServletRequest request){
		ModelAndView modelAndView=new ModelAndView("crawler");
		
		return modelAndView;
	}
	
	@RequestMapping(value={"/crawler/start"},produces="text/html; charset=utf-8")
	public @ResponseBody String start(){
		
		service.CatData("https://www.anjuke.com/sy-city.html","https://guangzhou.anjuke.com");
		return "ok";
	}

	@RequestMapping(value={"/crawler/stop"},produces="text/html; charset=utf-8")
	public @ResponseBody String stop(){
		service.stop();
		return "ok";
	}
	
}
