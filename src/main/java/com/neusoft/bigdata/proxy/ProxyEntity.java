package com.neusoft.bigdata.proxy;

import java.io.Serializable;

public class ProxyEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String ip;
	public int port;
	
	public ProxyEntity(){
		super();
	}
	
	public ProxyEntity(String ip, Integer port) {
		super();
		this.ip = ip;
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "ProxyEntity [ip=" + ip + ", port=" + port + "]";
	}
	
	
	
}
