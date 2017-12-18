package com.neusoft.bigdata.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CrawlerController {

	@RequestMapping(value={"/crawler"})
	public ModelAndView crawler(HttpServletRequest request){
		ModelAndView modelAndView=new ModelAndView("crawler");
		
		return modelAndView;
	}
	
}
