package test.domain;

import com.neusoft.bigdata.domain.Base;

public class KeyValue extends Base{
	public KeyValue(Object k,Object v){
		this.k=k;
		this.v=v;
	}
	
	Object k;
	Object v;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "key="+k+";value="+v;
	}
	
}