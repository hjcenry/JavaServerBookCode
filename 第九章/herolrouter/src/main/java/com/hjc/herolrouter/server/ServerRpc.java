package com.hjc.herolrouter.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.jsonrpc4j.JsonRpcServer;
import com.hjc.herolrouter.util.hibernate.HibernateUtil;

public class ServerRpc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JsonRpcServer server = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServerRpc() {
		server = new JsonRpcServer(this, ServerRpc.class);
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		server.handle(request, response);
	}

	public String getServerUrl(String serverId) {
		ServerConfig serverConfig = HibernateUtil.find(ServerConfig.class,
				Long.parseLong(serverId));
		StringBuffer url = new StringBuffer("http://");
		url.append(serverConfig.getIp());
		url.append(":");
		url.append(serverConfig.getPort());
		return url.toString();
	}
}
