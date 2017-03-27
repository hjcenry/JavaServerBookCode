package com.hjc.herolpvp.net.socket;

import io.netty.channel.Channel;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: ChannelMgr
 * @Description: Channel管理类
 * @author 何金成
 * @date 2015年12月25日 下午3:34:06
 * 
 */
public class ChannelMgr {
	public Logger logger = LoggerFactory.getLogger(ChannelMgr.class);
	public ConcurrentHashMap<String, ChannelUser> channelMap;
	private static ChannelMgr inst;

	private ChannelMgr() {
		channelMap = new ConcurrentHashMap<String, ChannelUser>();
	}

	public static ChannelMgr getInstance() {
		if (inst == null) {
			inst = new ChannelMgr();
		}
		return inst;
	}

	/**
	 * @Title: addChannelUser
	 * @Description: channel管理
	 * @param ctx
	 * @param userId
	 * @return ChannelUser
	 * @throws
	 */
	public ChannelUser addChannelUser(Channel channel, Long userid) {
		String channelId = channel.id().asShortText();
		ChannelUser ret = new ChannelUser();
		ret.channelId = channelId;
		ret.channel = channel;
		ret.userid = userid;
		synchronized (channelMap) {
			channelMap.put(channelId, ret);
		}
		return ret;
	}

	/**
	 * @Title: closeAllChannel
	 * @Description: 关闭所有Channel void
	 * @throws
	 */
	public void closeAllChannel() {
		synchronized (channelMap) {
			Iterator<ChannelUser> it = channelMap.values().iterator();
			while (it.hasNext()) {
				ChannelUser u = it.next();
				it.remove();
				u.channel.close();
			}
		}
		logger.info("关闭所有channel");
	}

	/**
	 * @Title: removeChannel
	 * @Description: 移除channel
	 * @param ctx
	 *            void
	 * @throws
	 */
	public void removeChannel(Channel channel) {
		synchronized (channelMap) {
			Iterator<ChannelUser> it = channelMap.values().iterator();
			while (it.hasNext()) {
				ChannelUser u = it.next();
				if (u.channel.equals(channel)) {
					it.remove();
					channel.close();
				}
			}
		}
	}

	/**
	 * @Title: getUseridByChannel
	 * @Description: 根据Channel找到Userid
	 * @param ctx
	 * @return Long
	 * @throws
	 */
	public ChannelUser findByChannel(Channel channel) {
		synchronized (channelMap) {
			Iterator<ChannelUser> it = channelMap.values().iterator();
			while (it.hasNext()) {
				ChannelUser u = it.next();
				if (u.channel.equals(channel)) {
					return u;
				}
			}
			return null;
		}
	}

	/**
	 * @Title: findByUserid
	 * @Description: 根据userid找到ChannelUser
	 * @param junZhuId
	 * @return ChannelUser
	 * @throws
	 */
	public ChannelUser findByUserid(Long userid) {
		synchronized (channelMap) {
			Iterator<ChannelUser> it = channelMap.values().iterator();
			while (it.hasNext()) {
				ChannelUser u = it.next();
				Long v = u.userid;
				if (v != null && v.longValue() == userid.longValue()) {
					return u;
				}
			}
		}
		return null;
	}

	/**
	 * @Title: getChannel
	 * @Description: 根据userid获取Channel
	 * @param junZhuId
	 * @return ChannelHandlerContext
	 * @throws
	 */
	public Channel getChannel(Long userid) {
		ChannelUser cu = findByUserid(userid);
		if (cu == null) {
			return null;
		}
		return cu.channel;
	}

	/**
	 * @Title: getAllChannels
	 * @Description: 获取所有Channel
	 * @return List<ChannelUser>
	 * @throws
	 */
	public List<ChannelUser> getAllChannels() {
		List<ChannelUser> list = new LinkedList<ChannelUser>();
		synchronized (channelMap) {
			for (ChannelUser user : channelMap.values()) {
				list.add(user);
			}
		}
		return list;
	}
}
