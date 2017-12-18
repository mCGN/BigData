package com.neusoft.bigdata.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neusoft.bigdata.crawler.parser.NewHouseParser;
import com.neusoft.bigdata.crawler.service.impl.AnjukeCrawlerService;

/**
 * Servlet implementation class CatDataServlet
 */
public class CatDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CatDataServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getParameter("action");
//		String url=request.getParameter("url");
		AnjukeCrawlerService service=new AnjukeCrawlerService();
		try {
			if (action.equals("new_house")) {
				service.CatData(UrlConstant.getUrl(action));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.getWriter().append("你请求的是CatDataServlet");
//		response.getWriter().append("{\"size\":\""+service.size()+"\"}");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
