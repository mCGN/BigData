package com.neusoft.bigdata.web.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.neusoft.bigdata.domain.KeyValue;
import com.neusoft.bigdata.service.impl.NewHouseService;

@Controller
public class DataController { 
	
	
	@RequestMapping(value={"/data/avg"},produces="text/html; charset=UTF-8")
	public @ResponseBody String getAvg(HttpServletResponse response){
	NewHouseService service=new NewHouseService();
		List<KeyValue<Integer>>a= service.getAvg();
		Gson gson=new Gson();
		String result=gson.toJson(a);
		return result;
	}
	
	@RequestMapping(value={"/data/detail"},produces="text/html; charset=UTF-8")
	public @ResponseBody String getDetail(String city,String tag){
		NewHouseService service=new NewHouseService();
		HashMap<String, Long> num= service.getNumByTag(city, tag);
		Gson gson=new Gson();
		String result=gson.toJson(num);
		return result;
	}
	
	@RequestMapping(value={"/data/cityavg"},produces="text/html; charset=UTF-8")
	public @ResponseBody String getAvg(String city){
	NewHouseService service=new NewHouseService();
		Integer avg= service.getCityAvg(city);
		return "{\"avg\":"+avg+"}";
	}
	
	@RequestMapping(value={"/data/maxandmin"},produces="text/html; charset=UTF-8")
	public @ResponseBody String getMaxAndMin(String city){
		NewHouseService service=new NewHouseService();
		HashMap<String, Integer>a= service.getMaxAndMinPrice(city);
		Gson gson=new Gson();
		String result=gson.toJson(a);
		return result;
	}
	
	@RequestMapping(value={"/data/pricegrouping"},produces="text/html; charset=UTF-8")
	public @ResponseBody String getPriceGrouping(String city){
		NewHouseService service=new NewHouseService();
		List<KeyValue<Integer>>a= service.priceGrouping(city);
		Gson gson=new Gson();
		String result=gson.toJson(a);
		return result;
	}
	
}
