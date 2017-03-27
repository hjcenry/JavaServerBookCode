package com.hjc.herolrouter.server;

import org.springframework.stereotype.Service;

import com.hjc.herolrouter.util.hibernate.HibernateUtil;

@Service
public class NotifyService {
	/**
	 * @Title: getBaseUrl
	 * @Description: 获取返回的baseUrl
	 * @param serverId
	 * @return
	 * @return String
	 * @throws
	 */
	public String getBaseUrl(long serverId) {
		ServerConfig server = HibernateUtil.find(ServerConfig.class, serverId);
		StringBuilder sb = new StringBuilder();
		sb.append("http://").append(server.getIp()).append(":")
				.append(server.getPort()).append("/herol" + "/notify/");
		return null;
	}

	// /**
	// * @Title: kick
	// * @Description: 把重复登录的玩家踢下线
	// * @param serverId 要通知的服务器id
	// * @param playerId 要踢下线的玩家id
	// * @return void
	// * @throws
	// */
	// public void kick(int serverId, long playerId) {
	// Map<String, String> param = new HashMap<String, String>();
	// OperatePlayerReq req = new OperatePlayerReq();
	// req.setPlayerId(playerId);
	// req.setServerId(serverId);
	// param.put("data", JsonUtils.objectToJson(req));
	// HttpClient.get(getBaseUrl(serverId) + "kick", param);
	// }
}
