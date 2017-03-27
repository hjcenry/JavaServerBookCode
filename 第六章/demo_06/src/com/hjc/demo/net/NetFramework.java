package com.hjc.demo.net;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjc.demo.core.GameInit;
import com.hjc.test.Demo;

/**
 * @ClassName: NetFramework
 * @Description: 模拟网络模块，一般直接使用Netty或Mina框架
 * @author 何金成
 * @date 2016年4月27日 下午3:58:20
 * 
 */
public class NetFramework {
	private NetHandler handler;
	private String ip;
	private int port;
	public static NetFramework inst;

	/**
	 * @Fields queue : 模拟网络消息处理队列
	 */
	public static BlockingQueue<JSONObject> queue = new LinkedBlockingQueue<JSONObject>();
	JSONObject exit = new JSONObject();

	private NetFramework(String ip, int port) {
		this.setIp(ip);
		this.setPort(port);
		inst = this;
	}

	public static NetFramework buildNetFramework(String ip, int port) {
		return new NetFramework(ip, port);
	}

	public void start() throws Exception {
		Demo.print("net start success");
		reading();
	}

	public void shut() throws Exception {
		Demo.print("net shut success");
		queue.add(exit);
	}

	public void setHandler(NetHandler handler) {
		this.handler = handler;
	}

	/**
	 * @throws Exception
	 * @Title: readMsg
	 * @Description: 网络模块消息输入接口
	 * @throws
	 */
	public void reading() {
		Thread readingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (GameInit.state == 1) {
					JSONObject msg = null;
					try {
						msg = queue.take();
					} catch (InterruptedException e) {
						Demo.print("Exception when take" + msg);
						continue;
					}
					if (msg == exit) {
						break;
					}
					try {
						if (handler == null) {
							throw new Exception("handler is not setted");
						}
						handler.read(msg);
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
				Demo.print("关闭网络模块");
			}
		}, "NetFramework");
		readingThread.start();
	}

	/**
	 * @throws Exception
	 * @Title: write
	 * @Description: 网络模块消息输出接口
	 * @throws
	 */
	public void write(JSON request) {
		if (handler == null) {
			Demo.print("handler is not setted");
			return;
		}
		handler.write(request);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
