package com.neusoft.bigdata.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class BeanMapUtils {

	// javabean 转map
	public static <T> Map<String, Object> beanToMap(T bean)  {
		Map<String, Object> map = new HashMap<String, Object>();
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[]descriptors= beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : descriptors) {
				String key=descriptor.getName();
				if (!key.equals("class")) {
					Method method= descriptor.getReadMethod();
					Object obj= method.invoke(bean);
					map.put(key, obj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	// map 转 javabean
	public static <T> T mapToBean(Map<String, Object> map, T bean) {
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[]descriptors= beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : descriptors) {
				String key=descriptor.getName();
				if (!key.equals("class")) {
					Method method= descriptor.getWriteMethod();
					method.invoke(bean,map.get(key));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

//	public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		if (objList != null && objList.size() > 0) {
//			Map<String, Object> map = null;
//			T bean = null;
//			for (int i = 0, size = objList.size(); i < size; i++) {
//				bean = objList.get(i);
//				map = beanToMap(bean);
//				list.add(map);
//			}
//		}
//		return list;
//	}

//	public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz)
//			throws InstantiationException, IllegalAccessException {
//		List<T> list = new ArrayList<T>();
//		if (maps != null && maps.size() > 0) {
//			Map<String, Object> map = null;
//			T bean = null;
//			for (int i = 0, size = maps.size(); i < size; i++) {
//				map = maps.get(i);
//				bean = clazz.newInstance();
//				mapToBean(map, bean);
//				list.add(bean);
//			}
//		}
//		return list;
//	}

}
