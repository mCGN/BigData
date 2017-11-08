package com.neusoft.bigdata.common.bloomfilter;

public class CountBloomFilter extends BloomFilter {

	public CountBloomFilter(){
		super();
		count=new byte[DEFAULT_SIZE];
	}
	
	public CountBloomFilter(int m,int k){
		super(m,k);
		count=new byte[m];
	}
	
	private byte[] count;
	
	@Override
	public void add(String value) {
		int i;
		for (SimpleHash f : func) {
			i=f.hash(value);
			bits.set(i, true);//设置位数组第i位为1；
			count[i]++;
		}
	}
	
	public void remove(String value){
		int i;
		if (!this.contains(value)) {
			return;
		}
		for (SimpleHash f : func) {
			i=f.hash(value);
			if (count[i]>0) {
				count[i]--;
				if (count[i]==0) {
					bits.set(i,false);
				}
			}
		}
	}
	
}
