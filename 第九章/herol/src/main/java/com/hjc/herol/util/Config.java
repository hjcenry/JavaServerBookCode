package com.hjc.herol.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {
	public static Logger logger = LoggerFactory.getLogger(Config.class);
	public String SERVER_CONFIG_PATH = "/server.properties"; // 服务器的配置路径
	public String CRONTAB_CONFIG_PATH = "/crontab.properties";
	public String OVERRIDE_PATH = "TX_CONF_PATH";

	public Properties data = new Properties();
	public List<String> crontabConfs = new ArrayList<String>();

	public String get(String key) {
		if (data.size() == 0) {
			loadConfig();
		}

		String ret = data.getProperty(key);
		if (ret != null) {
			ret = ret.trim();
		}
		return ret;
	}

	public String get(String key, String safeRet) {
		String v = get(key);
		if (v == null) {
			return safeRet;
		} else {
			return v.trim();
		}
	}

	public int get(String key, int safeRet) {
		String v = get(key);
		if (v == null) {
			return safeRet;
		} else {
			return Integer.parseInt(v.trim());
		}
	}

	public void loadConfig() {
		InputStream stream = Config.class
				.getResourceAsStream(SERVER_CONFIG_PATH);
		try {
			if (stream != null) {
				data.load(stream);
			} else {
				logger.error("没有找到配置文件:" + SERVER_CONFIG_PATH);
			}
			// loadServerId();
			printConf();

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("load config error!");
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// private void loadServerId(){
	// String host = get("loginServer");
	// int port = get("loginPort", 8090);
	// String msg = "{" + "\"msg\" : \"SERVERID\" }";
	// String page = "/qxrouter/gameSerManager.jsp";
	//
	// System.out.println("respMesg : " + respMesg);
	// setServerId(respMesg);
	// }
	public String[] getCacheServerList() {
		String svrList = get("cacheServers");
		// if (svrList == null) {2015年5月6日15:29:53修改成qxrouter中的获取方式
		// return new String[]{"cacheServer:11211"};
		// }
		// String[] ret = svrList.split(",");
		// return ret;
		return new String[] { svrList };
	}

	public String[] getRouterCacheServerList() {
		String svrList = get("routerCacheServers");
		return new String[] { svrList };
	}

	public String[] getLockServerList() {
		String svrList = get("cacheLockServers");
		if (svrList == null) {
			return null;
		}
		String[] ret = svrList.split(",");

		return ret;
	}

	/**
	 * @Description: 
	 *               2015年5月7日合并getLockServerList，getCacheServerList，getRouterCacheServerList
	 *               直接通过serverName获取server地址
	 * @param serverName
	 * @return
	 */
	public String getServerByName(String serverName) {
		return get(serverName);
	}

	// public void setServerId(String responseMess){
	// if(responseMess != null && !responseMess.equals("")){
	// JsonUtil jo = org.json.JSONObject.fromObject(responseMess);
	// if(jo != null){
	// String serId = (String)(jo.get("serverId"));
	// if(serId != null && !serId.equals("")){
	// data.put("serverId", serId);
	// System.out.println("设置serverId的值为:" + serId);
	// }
	// }
	// }
	// }
	private void printConf() {
		logger.warn("===================Server Conf==========================");
		Set<Entry<Object, Object>> entrySet = data.entrySet();
		for (Entry<Object, Object> entry : entrySet) {
			logger.warn("server conf:" + entry.getKey() + "->"
					+ entry.getValue());
		}
		for (String crontab : crontabConfs) {
			logger.warn("crontab conf:" + crontab);
		}
	}
}
