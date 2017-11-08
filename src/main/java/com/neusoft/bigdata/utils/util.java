package com.neusoft.bigdata.utils;

import java.util.Random;

public class util {

	
	private static String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static Random random = new Random();
	public static String getRandomString(int length) { // length表示生成字符串的长度

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

}
