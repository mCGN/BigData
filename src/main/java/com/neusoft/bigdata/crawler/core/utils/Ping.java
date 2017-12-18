package com.neusoft.bigdata.crawler.core.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Ping {
	
	public static  boolean ping(String host,int timeout){
		boolean result=false;
		try {
			result= InetAddress.getByName(host).isReachable(timeout);
		} catch (UnknownHostException e) {
			System.out.println("未知ip");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static  boolean ping(String host){
		return ping(host, 5000);
	}
	
}
