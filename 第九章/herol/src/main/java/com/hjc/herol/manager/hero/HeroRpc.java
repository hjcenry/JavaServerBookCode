package com.hjc.herol.manager.hero;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mongodb.morphia.Datastore;

import com.alibaba.fastjson.JSONArray;
import com.googlecode.jsonrpc4j.JsonRpcServer;
import com.hjc.herol.manager.player.Player;
import com.hjc.herol.util.mongo.MorphiaUtil;

/**
 * Servlet implementation class HeroRpc
 */
public class HeroRpc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JsonRpcServer server = null;
	private Datastore ds = MorphiaUtil.ds;

	public HeroRpc() {
		server = new JsonRpcServer(this, HeroRpc.class);
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		server.handle(request, response);
	}

	/**
	 * @Title: pickFightHero
	 * @Description: 从战斗卡组抽取一张英雄卡牌
	 * @param userid
	 * @return
	 * @return JSONArray
	 * @throws
	 */
	public JSONArray pickFightHero(String useridStr) {
		long userid = Long.parseLong(useridStr);
		Player player = ds.find(Player.class).field("_id").equal(userid).get();
		JSONArray heroJson = HeroMgr.getInstance().getHeroByGroup(
				player.getHeros(), player.fightGroup);
		int random = (int) Math.floor(Math.random() * heroJson.size());
		JSONArray heroArray = heroJson.getJSONArray(random);
		return heroArray;
	}
}
