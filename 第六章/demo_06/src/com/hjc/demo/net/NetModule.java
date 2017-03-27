package com.hjc.demo.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Properties;

import com.hjc.test.Demo;

/**
 * @ClassName: NetModule
 * @Description: 网络接入模块
 * @author 何金成
 * @date 2016年4月27日 下午3:22:53
 * 
 */
public class NetModule {
	private static NetModule netModule = null;
	public static Properties p;
	public static String ip;
	public static int port;
	public static NetFramework net;

	public static NetModule getInstance() {
		if (netModule == null) {
			netModule = new NetModule();
			netModule.init();
		}
		return netModule;
	}

	private NetModule() {

	}

	public void init() {
		try {
			p = readProperties();
			ip = p.getProperty("ip");
			port = Integer.parseInt(p.getProperty("port"));
			net = NetFramework.buildNetFramework(ip, port);
			NetHandler handler = new NetHandlerImpl();
			net.setHandler(handler);
		} catch (IOException e) {
			Demo.print("网络配置文件读取错误");
			e.printStackTrace();
		}
	}

	public boolean startNet() {
		// TODO 网络模块启动及绑定端口
		try {
			net.start();
			Demo.print("服务器ip " + ip + " 开启网络成功");
			Demo.print("已绑定端口 " + port);
		} catch (Exception e) {
			e.printStackTrace();
			Demo.print("服务器ip " + ip + " 开启网络失败");
		}
		return true;
	}

	public boolean shutNet() {
		// TODO 网络模块关闭及解绑端口
		try {
			net.shut();
			Demo.print("服务器ip " + ip + " 关闭网络成功");
			Demo.print("已解绑端口 " + port);
		} catch (Exception e) {
			e.printStackTrace();
			Demo.print("服务器ip " + ip + " 关闭网络失败");
		}
		return true;
	}

	protected Properties readProperties() throws IOException {
		Properties p = new Properties();
		InputStream in = NetModule.class.getResourceAsStream("net.properties");
		Reader r = new InputStreamReader(in, Charset.forName("UTF-8"));
		p.load(r);
		in.close();
		return p;
	}
}
