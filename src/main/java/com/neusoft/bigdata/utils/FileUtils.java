package com.neusoft.bigdata.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.neusoft.bigdata.crawler.core.IParser;
import com.neusoft.bigdata.crawler.service.impl.ProxyCrawlerService;
import com.neusoft.bigdata.domain.ProxyEntity;

public class FileUtils {
	
	public static String read(File file) throws IOException{
		String result=null;
		FileInputStream is=new FileInputStream(file);
		byte[]b=new byte[is.available()];
		is.read(b);
		result=new String(b, Charset.forName("UTF-8"));
		return result;
	}
	
	public static ArrayList<String> getAllFilePath(File file){
		ArrayList<String> result=new ArrayList<String>();
		if (file.isDirectory()) {
			File[] child= file.listFiles();
			for (int i = 0; i < child.length; i++) {
				result.addAll(getAllFilePath(child[i]));
			}
		}else{
			result.add(file.getPath());
		}
		return result;
	}
}
