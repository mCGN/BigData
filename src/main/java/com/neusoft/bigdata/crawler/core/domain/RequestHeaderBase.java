package com.neusoft.bigdata.crawler.core.domain;

/**
 * http request headers
 * @author AE
 *
 */
public class RequestHeaderBase {

	public String Accept="text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8";
	public String AcceptLanguage="zh-CN,zh;q=0.8";
	public String CacheControl="max-age=0";
	public String Connection="keep-alive";
	public String UpgradeInsecureRequests="1";
	public String UserAgent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3100.0 Safari/537.36";
	public String Cookie;
	public String Host;
	public String Referer;
	public String AcceptEncoding;
	
	public String getAccept() {
		return Accept;
	}
	public void setAccept(String accept) {
		Accept = accept;
	}
	public String getAcceptEncoding() {
		return AcceptEncoding;
	}
	public void setAcceptEncoding(String acceptEncoding) {
		AcceptEncoding = acceptEncoding;
	}
	public String getAcceptLanguage() {
		return AcceptLanguage;
	}
	public void setAcceptLanguage(String acceptLanguage) {
		AcceptLanguage = acceptLanguage;
	}
	public String getCacheControl() {
		return CacheControl;
	}
	public void setCacheControl(String cacheControl) {
		CacheControl = cacheControl;
	}
	public String getConnection() {
		return Connection;
	}
	public void setConnection(String connection) {
		Connection = connection;
	}
	public String getCookie() {
		return Cookie;
	}
	public void setCookie(String cookie) {
		Cookie = cookie;
	}
	public String getHost() {
		return Host;
	}
	public void setHost(String host) {
		Host = host;
	}
	public String getReferer() {
		return Referer;
	}
	public void setReferer(String referer) {
		Referer = referer;
	}
	public String getUpgradeInsecureRequests() {
		return UpgradeInsecureRequests;
	}
	public void setUpgradeInsecureRequests(String upgradeInsecureRequests) {
		UpgradeInsecureRequests = upgradeInsecureRequests;
	}
	public String getUserAgent() {
		return UserAgent;
	}
	public void setUserAgent(String userAgent) {
		UserAgent = userAgent;
	}
	
	public void setRequestHeader(String acceptEncoding, String cookie,String host, String referer) {
		AcceptEncoding = acceptEncoding;
		Cookie = cookie;
		Host=host;
		Referer = referer;
	}
	
	
	
}
