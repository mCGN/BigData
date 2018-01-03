package com.neusoft.bigdata.utils;

import java.io.UnsupportedEncodingException;

public class EncodingTool {
	
	
    public static String transformCoding(String str) {  
        try {  
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
            return null;  
        }  
    }
    
    /**
     * 
     * @param str 要转换的字符串
     * @param form 当前字符串的编码
     * @param to 目标字符串编码
     * @return
     */
    public static String transformCoding(String str,String form,String to) {  
        try {  
            return new String(str.getBytes(form), to);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
            return null;  
        }  
    }
    
}  
