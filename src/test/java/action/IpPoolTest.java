package action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.neusoft.bigdata.proxy.IpPool;
import com.neusoft.bigdata.proxy.ProxyEntity;
import com.neusoft.bigdata.utils.FileUtils;

import junit.framework.TestCase;

public class IpPoolTest extends TestCase {

	public static void main(String[] args) {
		put();
	}
	
	/**
	 * 从文件中读取ip，插入数据库中
	 */
	public static void put(){
		Gson gson=new Gson();
		List<ProxyEntity>ips=new ArrayList<ProxyEntity>();
		JsonParser parser=new JsonParser();
		ArrayList<String>list= FileUtils.getAllFilePath(new File("E:/ip/"));
		for (String path : list) {
			System.out.println("path:"+path);
			String json= FileUtils.read(new File(path));
			JsonArray array= parser.parse(json).getAsJsonArray();
			for (JsonElement jsonElement : array) {
				ProxyEntity entity= gson.fromJson(jsonElement, ProxyEntity.class);
				ips.add(entity);
			}
		}
		IpPool.put(ips);
	}
	
}
