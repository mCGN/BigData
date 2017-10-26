package com.neusoft.bigdata.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	
	@RequestMapping(value={"/index"},params={"a","b"})
	public ModelAndView Index(HttpServletRequest request) {
		int a=Integer.parseInt( request.getParameter("a"));
		int b=Integer.parseInt( request.getParameter("b"));
		ModelAndView model=new ModelAndView("index");
		model.addObject("sum",a+b);
		return model;
	}
	
}
