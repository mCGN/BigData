package com.neusoft.bigdata.web.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.client.DistinctIterable;
import com.neusoft.bigdata.dao.impl.MongoDao;

@Controller
public class IndexController {
	
	@RequestMapping(value={"/index"})
	public ModelAndView Index() {
		ModelAndView model=new ModelAndView("index");
		return model;
	}
	
	@RequestMapping(value={"/list"})
	public ModelAndView list() {
		ModelAndView model=new ModelAndView("list");
		MongoDao dao=new MongoDao("data", "city");
		DistinctIterable<String>cityList=dao.getCollection().distinct("city",String.class);
		ArrayList<String> arrayList=new ArrayList<String>();
		for (String string : cityList) {
			arrayList.add(string);
		}
		model.addObject("cityList", arrayList);
		return model;
	}
	
	@RequestMapping(value={"/contact"})
	public ModelAndView contact() {
		ModelAndView model=new ModelAndView("contact");
		return model;
	}
	
	@RequestMapping(value={"/detail"},method=RequestMethod.GET)
	public ModelAndView query( @RequestParam String city,HttpServletRequest request) {
		ModelAndView model=new ModelAndView("city_detail");
		model.addObject("city", city);
		return model;
	}
	
	@RequestMapping(value={"/statistics/avg"})
	public ModelAndView avg(){
		ModelAndView modelAndView=new ModelAndView("price_avg");
		return modelAndView;
	}
	
	
}
