package com.hjc.herolrouter.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hjc.herolrouter.message.ServerReq;
import com.hjc.herolrouter.message.ServerResp;
import com.hjc.herolrouter.util.JsonUtils;
import com.hjc.herolrouter.util.cache.CacheKeys;
import com.hjc.herolrouter.util.hibernate.HibernateUtil;
import com.hjc.herolrouter.util.memcached.MemcachedCRUD;

/**
 * 
 * @ClassName: NotifyController
 * @Description: 逻辑服务器与管理服务器交互控制器
 * @author 何金成
 * @date 2015年8月20日 下午4:29:29
 * 
 */
@Controller
@RequestMapping(value = "/notify")
public class NotifyController {

	/**
	 * 
	 * @Title: startServer
	 * @Description: 开服通知
	 * @param request
	 * @param response
	 * @throws IOException
	 * @return void
	 * @throws
	 */
	@RequestMapping(value = "/startserver")
	@ResponseBody
	public void startServer(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String data = request.getParameter("data");
		ServerReq req = (ServerReq) JsonUtils.jsonToBean(data, ServerReq.class);
		ServerResp resp = new ServerResp();
		if (null != data && !data.equals("")) {
			int serverId = req.getServerId();
			ServerConfig server = HibernateUtil.find(ServerConfig.class,
					serverId);
			if (null == server) {
				resp.setCode(0);
				resp.setResult("start server:server not regist");
				write(response, resp);
				return;
			}
			server.setState(1);// 设为空闲状态
			Throwable t = HibernateUtil.save(server);
			if (null == t) {
				resp.setCode(100);
				resp.setResult("start server:success");
			} else {
				resp.setCode(0);
				resp.setResult("start server:failed " + t.getMessage() + "");
			}
			write(response, resp);
			return;
		}
		resp.setResult("start server:no param");
		write(response, resp);
	}

	/**
	 * 
	 * @Title: shutServer
	 * @Description: 关服维护通知
	 * @param request
	 * @param response
	 * @throws IOException
	 * @return void
	 * @throws
	 */
	@RequestMapping(value = "/shutserver")
	@ResponseBody
	public void shutServer(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String data = request.getParameter("data");
		ServerReq req = (ServerReq) JsonUtils.jsonToBean(data, ServerReq.class);
		ServerResp resp = new ServerResp();
		if (null != data && !data.equals("")) {
			int serverId = req.getServerId();
			ServerConfig server = HibernateUtil.find(ServerConfig.class,
					serverId);
			if (null == server) {
				resp.setCode(0);
				resp.setResult("shut server:server not regist");
				write(response, resp);
				return;
			}
			server.setState(4);// 设为维护状态
			Throwable t = HibernateUtil.save(server);
			if (t == null) {
				resp.setCode(100);
				resp.setResult("shut server:success");
			} else {
				resp.setCode(0);
				resp.setResult("shut server:failed " + t.getMessage() + "");
			}
			write(response, resp);
			return;
		}
		resp.setCode(0);
		resp.setResult("shut server:no param");
		write(response, resp);
	}

	@RequestMapping(value = "/changestate")
	@ResponseBody
	public void changeState(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String data = request.getParameter("data");
		ServerReq req = (ServerReq) JsonUtils.jsonToBean(data, ServerReq.class);
		ServerResp resp = new ServerResp();
		if (null != data && !data.equals("")) {
			int serverId = req.getServerId();
			int state = req.getState();
			ServerConfig server = HibernateUtil.find(ServerConfig.class,
					serverId);
			if (null == server) {
				resp.setCode(0);
				resp.setResult("change state:server not regist");
				write(response, resp);
				return;
			}
			server.setState(state);// 改变服务器状态
			Throwable t = HibernateUtil.save(server);
			if (t == null) {
				resp.setCode(100);
				resp.setResult("change state:success");
			} else {
				resp.setCode(0);
				resp.setResult("change state:failed " + t.getMessage() + "");
			}
			write(response, resp);
			return;
		}
		resp.setCode(0);
		resp.setResult("change state:no param");
		write(response, resp);
	}

	@RequestMapping(value = "/validateLogin")
	public void validateLogin(HttpServletRequest request,
			HttpServletResponse response) {
		String data = request.getParameter("data");
		ServerReq req = (ServerReq) JsonUtils.jsonToBean(data, ServerReq.class);
		ServerResp resp = new ServerResp();
		if (null != data && !data.equals("")) {
			int serverId = req.getServerId();
			int accId = req.getAccId();
			ServerConfig server = HibernateUtil.find(ServerConfig.class,
					serverId);
			if (null == server) {
				resp.setCode(0);
				resp.setResult("change state:server not regist");
				write(response, resp);
				return;
			}
			Boolean flag = (Boolean) MemcachedCRUD.getInstance().getObject(
					CacheKeys.LOGIN_CACHE+ + accId);
			if (flag == null || !flag.booleanValue()) {
				resp.setCode(0);
				resp.setResult("validateLogin:not login");
			} else if (flag.booleanValue()) {
				resp.setCode(100);
			}
			write(response, resp);
			return;
		}
		resp.setCode(0);
		resp.setResult("validateLogin:no param");
		write(response, resp);
	}

	@RequestMapping(value = "/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		String data = request.getParameter("data");
		ServerReq req = (ServerReq) JsonUtils.jsonToBean(data, ServerReq.class);
		MemcachedCRUD.getInstance().deleteObject(
				CacheKeys.LOGIN_CACHE + req.getAccId());
		return;
	}

	protected void write(HttpServletResponse response, Object msg) {
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.write(JsonUtils.objectToJson(msg));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
