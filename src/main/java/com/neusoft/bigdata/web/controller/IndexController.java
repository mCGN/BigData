package com.neusoft.bigdata.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	
	@RequestMapping(value={"/index"})
	public ModelAndView Index(HttpServletRequest request) {
		ModelAndView model=new ModelAndView("index");
		return model;
	}
	
}
