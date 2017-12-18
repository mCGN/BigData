package com.neusoft.bigdata.crawler.core;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
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
//		SSLContext context = null;
		SSLConnectionSocketFactory factory=null;
		try {
			factory= new SSLConnectionSocketFactory(SSLContext.getDefault());
//			context = createIgnoreVerifySSL();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("https",factory)
				.register("http", new PlainConnectionSocketFactory())
				.build();
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

	// private static CloseableHttpClient client;

	public static CloseableHttpClient getHttpClient() {
		CloseableHttpClient client = HttpClients.custom().setConnectionManager(manager).build();
		return client;
	}
	
	/**
	 * 设置代理ip
	 * @param host
	 * @param port
	 * @return
	 */
	public static CloseableHttpClient getHttpClient(RequestConfig config) {
		CloseableHttpClient client = HttpClients.custom()
				.setConnectionManager(manager)
				.setDefaultRequestConfig(config)
//				.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(3000).build())
				.build();
		return client;
	}
	
	public static CloseableHttpClient getHttpClient(String host,int port) {
		HttpHost httpHost=new HttpHost(host, port);
		RequestConfig config=RequestConfig.custom()
				.setSocketTimeout(3000)
				.setConnectTimeout(3000)
				.setConnectionRequestTimeout(3000)
				.setProxy(httpHost)
				.build();
		return getHttpClient(config);
	}

}
