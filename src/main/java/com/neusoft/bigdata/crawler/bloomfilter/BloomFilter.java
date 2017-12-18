package com.neusoft.bigdata.crawler.bloomfilter;

import java.util.BitSet;

public class BloomFilter {
	/* BitSet初始分配2^24个bit */
	protected static final int DEFAULT_SIZE = 2 << 24;

	/* 不同哈希函数的种子，一般应取质数 */
	protected static final int[] seeds = new int[] { 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 61};

	protected BitSet bits ;

	/* 哈希函数对象 */
	protected SimpleHash[] func ;

	protected final static int HASH_NUM=7;
	
	public BloomFilter() {
		init(DEFAULT_SIZE,HASH_NUM);
	}

	/**
	 * @param m 位数组长度
	 * @param k hash个数 1-11个
	 */
	public BloomFilter(int m, int k) {
		init(m,k);
	}
	
	private void init(int m, int k){
		k=k>11?11:k;
		bits = new BitSet(m);
		func = new SimpleHash[k];
		for (int i = 0; i < k; i++) {
			func[i] = new SimpleHash(m, seeds[i]);
		}
	}

	// 将字符串标记到bits中
	public void add(String value) {
		for (SimpleHash f : func) {
			bits.set(f.hash(value), true);
		}
	}

	// 判断字符串是否已经被bits标记
	public boolean contains(String value) {
		if (value == null) {
			return false;
		}

		boolean ret = true;
		for (SimpleHash f : func) {
			ret = ret && bits.get(f.hash(value));
		}

		return ret;
	}
	
	public void reset(){
		bits.clear();
	}

	/* 哈希函数类 */
	public static class SimpleHash {
		private int cap;
		private int seed;

		public SimpleHash(int cap, int seed) {
			this.cap = cap;
			this.seed = seed;
		}

		// hash函数，采用简单的加权和hash
		public int hash(String value) {
			int result = 0;
			int len = value.length();
			for (int i = 0; i < len; i++) {
				result = seed * result + value.charAt(i);
			}
			return (cap - 1) & result;
		}
	}

}
