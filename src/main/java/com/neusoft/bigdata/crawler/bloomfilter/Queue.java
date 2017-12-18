package com.neusoft.bigdata.crawler.bloomfilter;

import java.util.LinkedList;

import org.jsoup.select.Evaluator.IsEmpty;

public class Queue extends CountBloomFilter {

	private LinkedList<String> queue=new LinkedList<String>(); 
	
	public Queue() {
		super();
	}
	
	public Queue(int m,int k){
		super(m, k);
	}
	
	@Override
	public void add(String value) {
		super.add(value);
		queue.add(value);
	}
	
	public String remove(){
		String a= queue.remove();
		remove(a);
		return a;
	}
	
	public void remove(String value) {
		super.remove(value);
		queue.remove(value);
	}
	
	public boolean isEmpty(){
		return queue.isEmpty();
	}
	
	@Override
	public void reset() {
		super.reset();
		queue.clear();
	}
	
	
}
