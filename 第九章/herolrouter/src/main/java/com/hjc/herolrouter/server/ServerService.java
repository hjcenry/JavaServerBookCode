package com.hjc.herolrouter.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hjc.herolrouter.util.hibernate.HibernateUtil;
import com.hjc.herolrouter.util.redis.Redis;

@Service
public class ServerService {
	public static String SESSION_CACHE_KEY = "SERVERSESSION";
	public static Logger log = LoggerFactory.getLogger(ServerService.class);

	public boolean save(ServerConfig server) {
		Throwable t = null;
		if (HibernateUtil.find(ServerConfig.class, server.getId()) == null) {
			t = HibernateUtil.insert(server);
		} else {
			t = HibernateUtil.save(server);
		}
		if (t == null) {
			return true;
		}
		return false;
	}

	public boolean delete(ServerConfig server) {
		Throwable t = HibernateUtil.delete(server);
		if (t == null) {
			return true;
		}
		return false;
	}

	public ServerConfig getServerById(int id) {
		ServerConfig serverConfig = HibernateUtil.find(ServerConfig.class, id);
		return serverConfig;
	}

	public List<ServerConfig> getServerConfigs() {
		List<ServerConfig> list = HibernateUtil.list(ServerConfig.class,
				"where 1=1");
		if (null == list || list.size() == 0) {
			return null;
		}
		return list;
	}

	// /**
	// * @Title: getLeastNumServer
	// * @Description: 获取在线人数最少的逻辑服务器,实现负载均衡
	// * @return
	// * @return ServerConfig
	// * @throws
	// */
	// public ServerConfig getLeastNumServer(List<ServerConfig> serverList) {
	// serverList = serverList==null?HibernateUtil.list(ServerConfig.class,
	// "where state != 4"):serverList;
	// // 第一个server赋值为最小值
	// long minNum = Redis.getInstance().scard_(
	// SESSION_CACHE_KEY + serverList.get(0).getId());
	// long minServerId = serverList.get(0).getId();
	// for (ServerConfig server : serverList) {
	// long userNum = Redis.getInstance().scard_(
	// SESSION_CACHE_KEY + server.getId());
	// if (userNum < minNum) {
	// minNum = userNum;
	// minServerId = server.getId();
	// }
	// }
	// ServerConfig server = HibernateUtil.find(ServerConfig.class,
	// minServerId);
	// if (minNum >= server.getMax()) {
	// // 如果搜索出来的服务器超过自身最大负载
	// serverList.remove(minServerId);
	// if (serverList.size() != 0) {
	// getLeastNumServer(serverList);
	// } else {
	// return null;
	// }
	// }
	// return server;
	// }
}
