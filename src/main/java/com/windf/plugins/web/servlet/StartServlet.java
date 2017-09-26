package com.windf.plugins.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.windf.module.sso.filter.LoginFilter;
import com.windf.plugins.web.filter.FilterControler;
import com.windf.plugins.web.session.SessionFilter;

/**
 * 跟随项目启动的类的起点
 *
 */
public class StartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public StartServlet() {
        super();
    }

   
	@Override
	public void init() throws ServletException {
		super.init();
		
		FilterControler.getInstance().addFilter(new SessionFilter());
		
		// 其他接口初始化
		new com.windf.module.sso.Initialization().init();
		
	}
	
	 

}
