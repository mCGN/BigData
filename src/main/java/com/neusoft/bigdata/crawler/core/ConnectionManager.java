package com.neusoft.bigdata.crawler.core;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * 管理httpclient的多线程并发
 * 
 * @author AE
 */
public class ConnectionManager {

	private static PoolingHttpClientConnectionManager manager;

	private static int maxTotal = 50;

	private static int defaultMaxPerRoute = 25;

	static {
			SSLContext context = null;
		try {
			context=createIgnoreVerifySSL();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("https", new  SSLConnectionSocketFactory(context))
				.register("http", new PlainConnectionSocketFactory()).build();
		manager = new PoolingHttpClientConnectionManager(registry);
		manager.setMaxTotal(maxTotal);
		manager.setDefaultMaxPerRoute(defaultMaxPerRoute);

	}

	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("SSLv3");

		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		sc.init(null, new TrustManager[] { trustManager }, null);
		return sc;
	}

	private static CloseableHttpClient client;

	public static CloseableHttpClient getHttpClient() {
		if (client == null) {
			client = HttpClients.custom().setConnectionManager(manager).build();
		}
		return client;
	}

}
