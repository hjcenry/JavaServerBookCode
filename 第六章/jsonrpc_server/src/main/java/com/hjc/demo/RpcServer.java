package com.hjc.demo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.jsonrpc4j.JsonRpcServer;

public class RpcServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JsonRpcServer rpcServer = null;

	public RpcServer() {
		super();
		rpcServer = new JsonRpcServer(new DemoServiceImply(), DemoService.class);
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		rpcServer.handle(request, response);
	}

}
