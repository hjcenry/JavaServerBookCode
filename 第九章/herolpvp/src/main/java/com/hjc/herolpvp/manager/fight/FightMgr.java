/**   
 * @Title: FightMgr.java 
 * @Package com.hjc.herolpvp.manager.fight 
 * @Description: 实时战斗
 * @author 何金成   
 * @date 2016年5月12日 下午12:14:36 
 */
package com.hjc.herolpvp.manager.fight;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.hjc.herolpvp.core.GameInit;
import com.hjc.herolpvp.net.ProtoMessage;
import com.hjc.herolpvp.net.ResultCode;
import com.hjc.herolpvp.net.socket.ChannelMgr;
import com.hjc.herolpvp.net.socket.ChannelUser;
import com.hjc.herolpvp.net.socket.SocketHandler;

/**
 * @ClassName: FightMgr
 * @Description: TODO
 * @author 何金成
 * @date 2016年5月12日 下午12:14:36
 * 
 */
public class FightMgr {
	private static FightMgr fightMgr;

	public Logger logger = LoggerFactory.getLogger(FightMgr.class);
	public ConcurrentHashMap<Long, ChannelGroup> pkSceneMap = new ConcurrentHashMap<Long, ChannelGroup>();
	public List<Long> waitingUsers = new Vector<Long>();

	private FightMgr() {
	}

	public static FightMgr getInstance() {
		if (fightMgr == null) {
			fightMgr = new FightMgr();
		}
		return fightMgr;
	}

	/**
	 * @Title: enterScene
	 * @Description: 进入游戏场景
	 * @param ctx
	 * @param data
	 * @return void
	 * @throws
	 */
	public void enterScene(ChannelHandlerContext ctx, ProtoMessage data) {
		long userid = data.getUserid();
		// 添加到channel管理
		ChannelMgr.getInstance().addChannelUser(ctx.channel(), userid);
		// 匹配等待队列
		if (waitingUsers.size() == 0) {
			if (pkSceneMap.containsKey(Long.valueOf(userid))) {
				SocketHandler.writeJSON(ctx,
						ProtoMessage.getErrorResp("玩家" + userid + "已经在对战场景中"));
				return;
			}
			synchronized (waitingUsers) {
				// 没有人，直接加入等待队列
				waitingUsers.add(userid);
			}
			SocketHandler.writeJSON(ctx, ProtoMessage.getSuccessResp());
			return;
		}
		if (waitingUsers.contains(Long.valueOf(userid))) {
			SocketHandler.writeJSON(ctx,
					ProtoMessage.getErrorResp("玩家" + userid + "已经在等待队列中"));
			return;
		}
		if (pkSceneMap.containsKey(Long.valueOf(userid))) {
			SocketHandler.writeJSON(ctx, "玩家" + userid + "已经在对战场景中");
			return;
		}
		long fightUser = 0;
		// 取出第一个人
		synchronized (pkSceneMap) {
			fightUser = waitingUsers.remove(0);
			ChannelGroup pkScene = new DefaultChannelGroup(
					GlobalEventExecutor.INSTANCE);
			waitingUsers.remove(fightUser);
			pkScene.add(ctx.channel());
			pkScene.add(ChannelMgr.getInstance().getChannel(fightUser));
			pkSceneMap.put(userid, pkScene);
			pkSceneMap.put(fightUser, pkScene);
		}
		SocketHandler.writeJSON(pkSceneMap.get(Long.valueOf(userid)),
				ProtoMessage.getResp("玩家" + userid + "进入了对战场景",
						ResultCode.ENTER_PVP));
		SocketHandler.writeJSON(pkSceneMap.get(Long.valueOf(fightUser)),
				ProtoMessage.getResp("玩家" + fightUser + "进入了对战场景",
						ResultCode.ENTER_PVP));
	}

	/**
	 * @Title: skill
	 * @Description: 英雄释放技能
	 * @param ctx
	 * @param data
	 * @return void
	 * @throws
	 */
	public void skill(ChannelHandlerContext ctx, ProtoMessage data) {
		long userid = data.getUserid();
		int hero = data.getData().getIntValue("hero");
		SocketHandler.writeJSON(pkSceneMap.get(Long.valueOf(userid)), "玩家"
				+ userid + "的英雄" + hero + "释放了技能");
	}

	/**
	 * @Title: attack
	 * @Description: 放置英雄
	 * @param ctx
	 * @param data
	 * @return void
	 * @throws
	 */
	public void attack(ChannelHandlerContext ctx, ProtoMessage data) {
		long userid = data.getUserid();
		int hero = data.getData().getIntValue("hero");
		double x = data.getData().getDouble("x");
		double y = data.getData().getDouble("y");
		// TODO 对英雄所放置的位置做逻辑判断
		//
		SocketHandler.writeJSON(pkSceneMap.get(Long.valueOf(userid)), "玩家"
				+ userid + "把英雄" + hero + "放在了(" + x + "," + y + ")位置");
	}

	/**
	 * @Title: pickHero
	 * @Description: 从卡组中抽取一张卡
	 * @param ctx
	 * @param data
	 * @return void
	 * @throws
	 */
	public void pickHero(ChannelHandlerContext ctx, ProtoMessage data) {
		// 抽取卡牌的接口需要使用Json-Rpc去逻辑接口调用逻辑服务器来获取卡牌。
		long userid = data.getUserid();
		int num = data.getData().getIntValue("num");// 从卡牌抽卡数量
		JSONArray ret = new JSONArray();
		JsonRpcHttpClient client = null;
		try {
			client = new JsonRpcHttpClient(new URL(getLogicUrl(userid)));
			for (int i = 0; i < num; i++) {
				JSONArray heroJson = client.invoke("pickFightHero",
						userid + "", JSONArray.class);
				ret.add(heroJson);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		SocketHandler.writeJSON(ctx, ret);
	}

	public void exitScene(long userid) {
		exitWaitingUsers(userid);
		exitPkSceneMap(userid);
		ChannelUser channelUser = ChannelMgr.getInstance().findByUserid(userid);
		if (channelUser != null) {
			ChannelMgr.getInstance().removeChannel(
					ChannelMgr.getInstance().findByUserid(userid).channel);
		}
	}

	public void exitWaitingUsers(long userid) {
		if (waitingUsers.contains(Long.valueOf(userid))) {
			waitingUsers.remove(Long.valueOf(userid));
			logger.info("玩家" + userid + "已退出等待队列");
		}
	}

	public void exitPkSceneMap(long userid) {
		if (pkSceneMap.containsKey(Long.valueOf(userid))) {
			ChannelGroup group = pkSceneMap.get(Long.valueOf(userid));
			pkSceneMap.remove(Long.valueOf(userid));
			SocketHandler.writeJSON(group, "玩家" + userid + "已退出对战场景");
			logger.info("玩家" + userid + "已退出对战场景");
		}
	}

	/**
	 * @Title: getLogicUrl
	 * @Description: 获取逻辑地址
	 * @param userid
	 * @return
	 * @return String
	 * @throws
	 */
	public String getLogicUrl(long userid) {
		int serverId = (int) (userid % 1000);
		String ip = GameInit.cfg.get("loginServer", "127.0.0.1");
		int port = GameInit.cfg.get("loginPort", 81);
		String url = null;
		JsonRpcHttpClient client = null;
		try {
			client = new JsonRpcHttpClient(new URL("http://" + ip + ":" + port));
			url = client.invoke("getServerUrl", serverId + "", String.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
		return url;
	}
}
