package com.neusoft.bigdata.web.socket;

import java.io.IOException;
import java.util.HashSet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * websocket 用于通知客户端，
 * 在客户端打开页面时，会建立一个websocket与服务器保存通信
 * @author AE
 *
 */
@ServerEndpoint(value="/noticeSocket")
public class NoticSocket {
	
	private static HashSet<NoticSocket>set=new HashSet<NoticSocket>();
	
	private synchronized void add(NoticSocket socket){
		set.add(socket);
	}
	
	private synchronized void remove(NoticSocket socket) {
		set.remove(socket);
	}
	
	private static int count=0;
	
	private Session session;
	
	@OnOpen
	public void onOpen(Session session){
		add(this);
		this.session=session;
		count++;
		System.out.println("connection +1\r\ncount:"+count);
	}
	
	@OnClose
	public void onClose(){
		remove(this);
		count--;
	}
	
	@OnError 
	public void onError(Session session,Throwable throwable){
		System.out.println("error msg:"+throwable.getMessage());
	}
	
	@OnMessage
	public void onMessage(String message,Session session){
		
	}
	
	public void sendMessage(String message) throws IOException{
		session.getBasicRemote().sendText(message);
	}
	
	public static void massMessage(String msg){
		for (NoticSocket noticSocket : set) {
			try {
				noticSocket.sendMessage(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
