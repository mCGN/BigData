package com.neusoft.bigdata.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
