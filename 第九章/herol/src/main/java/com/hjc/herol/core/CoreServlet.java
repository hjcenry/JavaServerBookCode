package com.hjc.herol.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreServlet extends HttpServlet {
	private static final long serialVersionUID = -2082543656440562457L;
	public static final Logger logger = LoggerFactory
			.getLogger(CoreServlet.class);

	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		logger.info("servlet service");
	}

	@Override
	public void destroy() {
		logger.info("CoreServelet destroy");
		GameInit.shutdown();// 通知服务器关服
	}

	@Override
	public void init() throws ServletException {
		logger.info("CoreServelet init");
		GameInit.init();// 初始化游戏服务器
	}

}
