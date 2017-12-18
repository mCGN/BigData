package com.neusoft.bigdata.domain;

import com.neusoft.bigdata.crawler.core.domain.Base;

public class ProxyEntity extends Base{
	
	public String host;
	public Integer port;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	
}
