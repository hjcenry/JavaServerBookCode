package com.hjc.herolpvp.core;

import com.hjc.herolpvp.net.socket.SocketServer;

public class GameServer {
	public static volatile boolean shutdown = false;
	private static GameServer server;

	private GameServer() {
	}

	public static GameServer getInstance() {
		if (null == server) {
			server = new GameServer();
		}
		return server;
	}

	/**
	 * @Title: startServer
	 * @Description: 开启服务器
	 * @throws
	 */
	public void startServer() {
		SocketServer.getInstance().start();
		shutdown = false;
	}

	/**
	 * @Title: shutServer
	 * @Description: 关闭服务器
	 * @throws
	 */
	public void shutServer() {
		SocketServer.getInstance().shut();
		shutdown = true;
	}
}
