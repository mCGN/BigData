package test.domain;

import java.io.Serializable;

public class AvgCount implements Serializable {
	
	public AvgCount(Integer num,Integer count){
		this.num=num;
		this.count=count;
	}
	
	public Integer num;
	
	public Integer count;
	
}
