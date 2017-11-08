package com.neusoft.bigdata.common;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Resources {
	public static String getResourceAsString(String name) throws IOException {

		String path = Thread.currentThread().getContextClassLoader().getResource(name).getFile();
		InputStream is = new FileInputStream(path);
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		int len;
		byte[] buffer = new byte[1024];
		while ((len=is.read(buffer))!=-1) {
			os.write(buffer, 0, len);
		}
		is.read(buffer);
		String result =os.toString();
		is.close();
		os.close();
		return result;
	}
}
