package com.neusoft.bigdata.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class FileUtils {
	
	/**
	 * 读取文件
	 * @param file
	 * @return
	 */
	public static String read(File file){
		String result=null;
		FileInputStream is = null;
		byte[] b=null;
		try {
			is = new FileInputStream(file);
			b=new byte[is.available()];
			is.read(b);
			result=new String(b, Charset.forName("UTF-8"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 获取某个文件夹中所有文件的路径
	 * @param file
	 * @return
	 */
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
