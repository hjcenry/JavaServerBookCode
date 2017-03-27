package com.hjc.herol.notification;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.hjc.herol.core.GameInit;
import com.hjc.herol.notification.message.ServerReq;
import com.hjc.herol.notification.message.ServerResp;
import com.hjc.herol.util.HttpClient;
import com.hjc.herol.util.JsonUtils;

public class ServerNotify {
	public static String loginServer = GameInit.cfg.get("loginServer");
	public static String loginPort = GameInit.cfg.get("loginPort");
	public static String baseUrl = "http://" + loginServer + ":" + loginPort
			+ "/herolrouter/notify/";
	private static final Logger logger = LoggerFactory
			.getLogger(ServerNotify.class);

	/**
	 * @Title: startServer
	 * @Description: 发送开服消息给登录服务器
	 * @return void
	 * @throws
	 */
	public static boolean startServer() {
		String url = baseUrl + "startserver";
		Map<String, String> param = new HashMap<String, String>();
		// request message
		ServerReq req = new ServerReq(); 
		req.setServerId(GameInit.serverId);
		param.put("data", JSON.toJSONString(req));
		String data = HttpClient.get(url, param);
		// response message
		ServerResp resp = (ServerResp) JSON.parseObject(data, ServerResp.class);
		if (null != data && !data.equals("")) {
			logger.info("登录服务器收到登录消息 {},{}", url, resp.getResult());
			return true;
		} else {
			logger.info("无法连接登录服务器 {}", url);
			return false;
		}
	}

	/**
	 * @Title: shutServer
	 * @Description: 发送关服消息给登录服务器
	 * @return void
	 * @throws
	 */
	public static boolean shutServer() {
		String url = baseUrl + "shutserver";
		Map<String, String> param = new HashMap<String, String>();
		// request message
		ServerReq req = new ServerReq();
		req.setServerId(GameInit.serverId);
		param.put("data", JSON.toJSONString(req));
		String result = HttpClient.get(url, param);
		// response message
		ServerResp resp = (ServerResp) JSON.parseObject(result,
				ServerResp.class);
		if (null != result && !result.equals("")) {
			logger.info("登录服务器收到关服消息 {},{}", url, resp.getResult());
			return true;
		} else {
			logger.info("无法连接登录服务器 {}", url);
			return false;
		}
	}

	/**
	 * @Title: changeState
	 * @Description: 通知登录服务器改变服务器状态
	 * @return void
	 * @throws
	 */
	public static boolean changeState(int state) {
		String url = baseUrl + "changestate";
		Map<String, String> param = new HashMap<String, String>();
		// request message
		ServerReq req = new ServerReq();
		req.setServerId(GameInit.serverId);
		req.setServerId(state);
		param.put("data", JSONUtils.toJSONString(req));
		String result = HttpClient.get(url, param);
		// response message
		ServerResp resp = (ServerResp) JsonUtils.jsonToBean(result,
				ServerResp.class);
		if (null != result && !result.equals("")) {
			logger.info("登录服务器收到改变状态消息 {},{}", url, resp.getResult());
			return true;
		} else {
			logger.info("无法连接登录服务器 {}", url);
			return false;
		}
	}

	/**
	 * @Title: validateLogin
	 * @Description: 验证账号是否登录
	 * @param accId
	 *            void
	 * @throws
	 */
	public static boolean validateLogin(int accId) {
		String url = baseUrl + "validateLogin";
		Map<String, String> param = new HashMap<String, String>();
		// request message
		ServerReq req = new ServerReq();
		req.setAccId(accId);
		req.setServerId(GameInit.serverId);
		param.put("data", JSONUtils.toJSONString(req));
		String result = HttpClient.get(url, param);
		// response message
		ServerResp resp = (ServerResp) JsonUtils.jsonToBean(result,
				ServerResp.class);
		if (null != result && !result.equals("")) {
			logger.info("登录服务器收到登录验证消息 {},{}", url, resp.getResult());
			if (resp.getCode() == 100) {
				logger.info("账号 {} 登录成功", accId);
				return true;
			} else {
				logger.error(resp.getResult());
			}
		} else {
			logger.info("无法连接登录服务器 {}", url);
		}
		return false;
	}

	/**
	 * @Title: logout
	 * @Description: 退出
	 * @param accId
	 * @throws
	 */
	public static void logout(int accId) {
		String url = baseUrl + "logout";
		Map<String, String> param = new HashMap<String, String>();
		// request message
		ServerReq req = new ServerReq();
		req.setAccId(accId);
		req.setServerId(GameInit.serverId);
		param.put("data", JSONUtils.toJSONString(req));
		HttpClient.get(url, param);
	}
}
