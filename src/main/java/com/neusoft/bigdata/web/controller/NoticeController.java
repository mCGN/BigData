package com.neusoft.bigdata.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neusoft.bigdata.web.socket.NoticSocket;

@Controller
public class NoticeController {
	
	private static boolean readyToRefresh=false;
 	
	/**
	 * 通知客户端有新数据
	 * @param action
	 * @return
	 */
	@RequestMapping(value="/notice",method=RequestMethod.POST)
	public @ResponseBody String notice(String action){
		if (readyToRefresh) {
			return "success";
		}
		if (action.equals("recount")) {
			new Thread(target).start();
			return "success";
		}
		return "fail";
	}
	
	Runnable target= new Runnable() {
		public void run() {
			try {
				readyToRefresh=true;
				Thread.currentThread();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				NoticSocket.massMessage("recount");
				readyToRefresh=false;
			}
		}
	};
	
	
	
}
