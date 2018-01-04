package com.neusoft.bigdata.proxy;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.neusoft.bigdata.dao.BeanMapUtils;
import com.neusoft.bigdata.dao.impl.MongoDao;

/**
 * ip池
 * @author AE
 *
 */
public class IpPool {
	
	private static LinkedList<ProxyEntity>pool=new LinkedList<ProxyEntity>();
	private static int point=0;//游标

	
	
	private static MongoDao dao;
	private IpPool(){
		
	}
	static{
		dao=new MongoDao("data","ip");
		init();
	}
	
	public synchronized static ProxyEntity getIp(){
		if (pool.isEmpty()) {
			return null;
		}
		int size= pool.size();
		point= point%size;
		if (point>=size) {
			point=0;
		}
		ProxyEntity entity=pool.get(point);
		point++;
		return entity;
	}
	
	private static void init(){
		pool.clear();
		FindIterable<Document>iterable= dao.find(new Document("available",true));
		for (Document doc : iterable) {
			ProxyEntity entity=new ProxyEntity(doc.getString("ip"),doc.getInteger("port"));
			pool.add(entity);
		}
	}
	
	public static void put(List<ProxyEntity>list){
		for (ProxyEntity proxyEntity : list) {
			Map<String, Object>map=BeanMapUtils.beanToMap(proxyEntity);
			if(dao.find(new Document(map)).first()==null){
				System.out.println("has");
				map.put("available",true);
				dao.insert(map);
			}
		}
	}

	public synchronized static void setIpInvalid(ProxyEntity entity){
		Map<String, Object>map=BeanMapUtils.beanToMap(entity);
		Document filter=new Document(map);
		dao.update(filter, new Document("$set",new Document("available",false)));
		pool.remove(entity);
		point--;
	}
	
	
	
}
