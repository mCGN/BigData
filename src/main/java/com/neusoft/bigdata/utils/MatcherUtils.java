package com.neusoft.bigdata.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
//？
public class MatcherUtils {
	
	static Pattern pattern=Pattern.compile("https://(.+)\\.anjuke\\.com");
	public static String matchCity(String str){
		
		Matcher matcher= pattern.matcher(str);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}
	
}
