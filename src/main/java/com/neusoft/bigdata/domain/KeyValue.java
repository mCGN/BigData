package com.neusoft.bigdata.domain;

import java.io.Serializable;


public class KeyValue<T> implements Serializable {
	
	public KeyValue(String key,T value){
		this.key=key;
		this.value=value;
	}
	
	public String key;
	
	public T value;
	
}
