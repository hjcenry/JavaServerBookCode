package com.hjc.herol.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjc.herol.manager.event.EventMgr;
import com.hjc.herol.net.ProtoIds;
import com.hjc.herol.notification.ServerNotify;
import com.hjc.herol.task.ExecutorPool;
import com.hjc.herol.util.Config;
import com.hjc.herol.util.ThreadViewer;
import com.hjc.herol.util.csv.CsvDataLoader;
import com.hjc.herol.util.hibernate.HibernateUtil;
import com.hjc.herol.util.memcached.MemcachedCRUD;
import com.hjc.herol.util.mongo.MorphiaUtil;
import com.hjc.herol.util.redis.Redis;
import com.hjc.herol.util.sensitive.SensitiveFilter;

/**
 * @ClassName: GameInit
 * @Description: 服务器初始化
 * @author 何金成
 * @date 2015年5月23日 下午4:21:19
 * 
 */
public class GameInit {
	public static int serverId = 1; // 服务器标示
	public static String confFileBasePath = "/csv/";// 配置文件根目录
	public static Config cfg;// 读取server.properties配置
	public static String templatePacket = "com.hjc.herol.template.";// xml模板类的位置
	public static String dataConfig = "/dataConfig.xml";// xml位置
	private static final Logger logger = LoggerFactory
			.getLogger(GameInit.class);

	public static boolean init() {
		try {
			logger.info("================开启逻辑服务器================");
			// 加载服务器配置文件
			logger.info("加载服务器配置文件");
			confInit();
			// 加载Redis
			logger.info("加载Redis");
			Redis.getInstance().init();
			try {
				Redis.getInstance().test();
			} catch (Exception e) {
				logger.info("Redis未启动，初始化异常", e);
			}
			// memcached
			logger.info("加载Memcached");
			MemcachedCRUD.getInstance().init();
			// 加载hibernate
			// logger.info("加载hibernate");
			// HibernateUtil.init();
			// 加载morphia
			logger.info("加载Morphia");
			MorphiaUtil.buildDatastore();
			// 加载CSV数据
			logger.info("加载数据配置");
			CsvDataLoader.getInstance(templatePacket, dataConfig).load();
			// 加载敏感词语
			logger.info("加载敏感词语");
			SensitiveFilter.getInstance();
			// 开启事件处理器
			logger.info("开启事件处理器");
			EventMgr.getInstance().init();
			// 初始化消息路由和系统模块
			logger.info("初始化模块");
			Router.getInstance().initMgr();
			logger.info("初始化CSV表");
			Router.getInstance().initCsvData();
			logger.info("初始化数值");
			Router.getInstance().initData();
			// 初始化消息协议
			logger.info("初始化消息协议");
			ProtoIds.init();
			// 初始化线程池
			logger.info("初始化线程池");
			ExecutorPool.initThreadsExecutor();
			// 初始化JsonRpc
			// logger.info("初始化JsonRpc");
			// JsonRpcServers.initData();
			// 启动线程监控
			logger.info("启动线程监控");
			ThreadViewer.start();
			// 启动服务器
			GameServer.getInstance().startServer();
			ServerNotify.startServer();
			logger.info("================完成开启逻辑服务器================");
			return true;
		} catch (Throwable e) {
			CoreServlet.logger.error("初始化异常:{}", e);
			return false;
		}
	}

	public static void confInit() {
		cfg = new Config();
		cfg.loadConfig();
		serverId = cfg.get("serverId", serverId);
	}

	public static boolean shutdown() {
		logger.info("================关闭逻辑服务器================");
		// 通知关闭服务器，部署herolrouter开启
		ServerNotify.shutServer();
		logger.info("关闭redis连接");
		Redis.destroy();
		logger.info("关闭memcached连接");
		MemcachedCRUD.getInstance().destroy();
		logger.info("关闭事件处理器");
		EventMgr.shutdown();
		logger.info("关闭线程池");
		ExecutorPool.shutdown();
		logger.info("关闭线程监控");
		ThreadViewer.interrupt();
		// 关闭逻辑服务器
		logger.info("关闭逻辑服务器");
		GameServer.getInstance().shutServer();
		logger.info("================完成关闭服务器================");
		return true;
	}
}
