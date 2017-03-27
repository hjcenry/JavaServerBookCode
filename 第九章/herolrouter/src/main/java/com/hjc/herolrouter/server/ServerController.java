package com.hjc.herolrouter.server;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hjc.herolrouter.util.HttpClient;
import com.hjc.herolrouter.util.hibernate.HibernateUtil;

@Controller
@RequestMapping(value = "/server")
public class ServerController {
	public Logger logger = LoggerFactory.getLogger(ServerController.class);
	public static int port = 8091;// 逻辑服端口
	@Autowired
	private ServerService serverService;

	@RequestMapping(value = "/saveserver", method = RequestMethod.POST)
	@ResponseBody
	public String saveServer(ServerConfig server) {
		boolean t = serverService.save(server);
		if (t) {
			return "SUCCESS";
		}
		return "ERROR";
	}

	@RequestMapping(value = "/deleteserver", method = RequestMethod.POST)
	@ResponseBody
	public String deleteServer(ServerConfig server) {
		boolean t = serverService.delete(server);
		if (t) {
			return "SUCCESS";
		}
		return "ERROR";
	}

	@RequestMapping(value = "/addserver")
	public void addServer(Model model, int id) {
		if (id != -1) {
			ServerConfig serverConfig = HibernateUtil.find(ServerConfig.class,
					id);
			model.addAttribute("server", serverConfig);
		}
	}

	@RequestMapping(value = "/serverlist")
	public void serverList(Model model) {
		List<ServerConfig> list = serverService.getServerConfigs();
		model.addAttribute("list", list);
	}

	@RequestMapping(value = "/operateStart")
	@ResponseBody
	public String operateStart(HttpServletRequest request,
			HttpServletResponse response) {
		String host = request.getParameter("host");
		String url = "http://" + host + ":"+port+"/herol/notify/operateStart";
		String data = HttpClient.post(url, "");
		if (null != data && !data.equals("")) {
			if (data.equals("success")) {
				logger.info("开服成功");
			} else if (data.equals("started")) {
				logger.info("服务器已经开启");
			} else {
				logger.info("开服失败");
			}
			return data;
		} else {
			logger.info("无法连接游戏服务器 {}", url);
			return "failed";
		}
	}

	@RequestMapping(value = "/operateShut")
	@ResponseBody
	public String operateShut(HttpServletRequest request,
			HttpServletResponse response) {
		String host = request.getParameter("host");
		String url = "http://" + host + ":"+port+"/herol/notify/operateShut";
		String data = HttpClient.post(url, "");
		if (null != data && !data.equals("")) {
			if (data.equals("success")) {
				logger.info("关服成功");
			} else if (data.equals("shutted")) {
				logger.info("服务器已经关闭");
			} else {
				logger.info("关服失败");
			}
			return data;
		} else {
			logger.info("无法连接游戏服务器 {}", url);
			return "failed";
		}
	}

}
