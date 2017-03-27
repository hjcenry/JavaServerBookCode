package com.hjc.herolrouter.client;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hjc.herolrouter.client.model.Account;
import com.hjc.herolrouter.client.service.AccountService;
import com.hjc.herolrouter.server.NotifyService;
import com.hjc.herolrouter.server.ServerConfig;
import com.hjc.herolrouter.server.ServerService;
import com.hjc.herolrouter.util.Constants;
import com.hjc.herolrouter.util.hibernate.HibernateUtil;
import com.hjc.herolrouter.util.redis.Redis;

@Controller
@RequestMapping(value = "/route")
public class RouteController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private ServerService serverService;
	@Autowired
	private NotifyService notifyService;
	public Logger logger = LoggerFactory.getLogger(RouteController.class);
	public static final String COUNTRY_CACHE = "country_cache";
	public static final String JUNZHU_CACHE = "junzhu_cache";

	@RequestMapping(value = "/loginOrRegist", method = RequestMethod.POST)
	public void loginOrRegist(HttpServletRequest request,
			HttpServletResponse response) {
		String username = request.getParameter("name");
		String password = request.getParameter("pwd");
		JSONObject ret = new JSONObject();
		if (username == null) {
			ret.put("code", 1);
			ret.put("msg", "用户名不能为空");
			writeJSON(ret, response, request.getRemoteAddr());
			return;
		}
		if (password == null) {
			ret.put("code", 1);
			ret.put("msg", "密码不能为空");
			writeJSON(ret, response, request.getRemoteAddr());
			return;
		}
		username = username.trim();
		password = password.trim();
		int state = accountService.loginOrRegist(username, password, 0);
		ret.put("code", state);
		switch (state) {
		case 100:
			ret.put("msg", "登录成功");
			break;
		case 101:
			ret.put("msg", "登录密码错误");
			break;
		case 200:
			ret.put("msg", "注册成功");
			break;
		case 201:
			ret.put("msg", "密码至少6位数");
			break;
		}
		if (state == 100 || state == 200) {
			Account account = accountService.getAccount(username);
			ret.put("userid", account.getId());
			ret.put("lastserver", account.getLastServer());
			List<ServerConfig> serverConfigs = HibernateUtil.list(
					ServerConfig.class, " order by id DESC");
			JSONArray servers = new JSONArray();
			for (ServerConfig serverConfig : serverConfigs) {
				JSONArray serverArr = new JSONArray();
				serverArr.add(serverConfig.getId());
				serverArr.add(serverConfig.getIp());
				serverArr.add(serverConfig.getPort());
				serverArr.add(serverConfig.getState());
				serverArr.add(serverConfig.getName());
				servers.add(serverArr);
			}
			ret.put("server", servers);
		}
		writeJSON(ret, response, request.getRemoteAddr());
	}

	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public void regist(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("name");
		String password = request.getParameter("pwd");
		JSONArray ret = new JSONArray();
		if (username == null) {
			ret.add(1);
			ret.add("用户名不能为空");
			writeJSON(ret, response, request.getRemoteAddr());
			return;
		}
		if (password == null) {
			ret.add(1);
			ret.add("密码不能为空");
			writeJSON(ret, response, request.getRemoteAddr());
			return;
		}
		username = username.trim();
		password = password.trim();
		int state = accountService.regist(username, password, 0);
		switch (state) {
		case 0:
			ret.add(0);
			ret.add("注册成功");
			break;
		case 1:
			ret.add(1);
			ret.add("账号已存在");
			break;
		case 2:
			ret.add(2);
			ret.add("密码至少8位数");
			break;
		}
		writeJSON(ret, response, request.getRemoteAddr());
		return;
	}

	@RequestMapping(value = "/chooseServer", method = RequestMethod.POST)
	public void chooseServer(HttpServletRequest request,
			HttpServletResponse response) {
		long userid = Long.parseLong(request.getParameter("userid"));
		long server = Long.parseLong(request.getParameter("server"));
		Account account = HibernateUtil.find(Account.class,
				(userid - server) / 1000);
		if (account != null) {
			account.setLastServer(server);
			HibernateUtil.save(account);
		}
		JSONObject result = new JSONObject();
		// 国家人数
		JSONArray arr = new JSONArray();

		// 国家人数
		String nums = Redis.getInstance().hget(Redis.GLOBAL_DB, COUNTRY_CACHE,
				String.valueOf(server));
		if (nums == null) {
			nums = "0#0#0";
		}
		String num1 = nums.split("#")[0];
		String num2 = nums.split("#")[1];
		String num3 = nums.split("#")[2];
		arr.add(num1);
		arr.add(num2);
		arr.add(num3);
		// 玩家是否存在
		String servers = Redis.getInstance().hget(Redis.GLOBAL_DB,
				JUNZHU_CACHE, String.valueOf(account.getId()));
		servers = (servers == null) ? "-1#-1" : servers;
		if (Arrays.asList(servers.split("#")).contains(String.valueOf(server))) {
			result.put("new", false);
		} else {
			result.put("new", true);
			result.put("country", arr);
		}
		int sum = Integer.parseInt(num1) + Integer.parseInt(num2)
				+ Integer.parseInt(num3);
		result.put("sum", sum);
		logger.info("账号{}登录了服务器{},服务器总人数{},魏国{}人，蜀国{}人，吴国{}人", userid, server,
				sum, Integer.parseInt(num1), Integer.parseInt(num2),
				Integer.parseInt(num3));
		writeJSON(result, response, request.getRemoteAddr());
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("name");
		String password = request.getParameter("pwd");
		JSONArray ret = new JSONArray();
		if (username == null) {
			ret.add(1);
			ret.add("用户名不能为空");
			writeJSON(ret, response, request.getRemoteAddr());
			return;
		}
		if (password == null) {
			ret.add(1);
			ret.add("密码不能为空");
			writeJSON(ret, response, request.getRemoteAddr());
			return;
		}
		username = username.trim();
		password = password.trim();
		int state = accountService.login(username, password, 0);
		switch (state) {
		case 0:
			ret.add(0);
			ret.add("登录成功");
			Account account = accountService.getAccount(username);
			ret.add(account.getId());
			break;
		case 1:
			ret.add(1);
			ret.add("账号不存在");
			break;
		case 2:
			ret.add(2);
			ret.add("密码错误");
			break;
		}
		writeJSON(ret, response, request.getRemoteAddr());
	}

	@RequestMapping(value = "/getServers")
	public void getServers(HttpServletRequest request,
			HttpServletResponse response) {
		List<ServerConfig> serverConfigs = HibernateUtil.list(
				ServerConfig.class, "");
		JSONArray ret = new JSONArray();
		for (ServerConfig serverConfig : serverConfigs) {
			JSONArray server = new JSONArray();
			server.add(serverConfig.getIp());
			server.add(serverConfig.getPort());
			server.add(serverConfig.getState());
			server.add(serverConfig.getName());
			ret.add(server);
		}
		writeJSON(ret, response, request.getRemoteAddr());
	}

	protected void writeJSON(Object msg, HttpServletResponse response,
			String remoteAddr) {
		String result = JSON.toJSONString(msg);
		if (Constants.CLIENT_DEBUG) {
			logger.info("ip:{},write:{}", remoteAddr, result);
		}
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
