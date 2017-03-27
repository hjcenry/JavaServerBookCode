package com.hjc.herolrouter.message;

import java.util.List;

import com.hjc.herolrouter.server.ServerConfig;
import com.hjc.herolrouter.util.JsonUtils;

public class GetServersResp {
	private String serverList;

	public String getServerList() {
		return serverList;
	}

	public void setServerList(List<ServerConfig> serverList) {
		this.serverList = JsonUtils.objectToJson(serverList);
	}
}
