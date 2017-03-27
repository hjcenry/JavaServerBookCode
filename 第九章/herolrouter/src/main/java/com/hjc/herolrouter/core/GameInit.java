package com.hjc.herolrouter.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjc.herolrouter.util.Config;
import com.hjc.herolrouter.util.hibernate.HibernateUtil;
import com.hjc.herolrouter.util.memcached.MemcachedCRUD;
import com.hjc.herolrouter.util.redis.Redis;
import com.hjc.herolrouter.util.sensitive.SensitiveFilter;

/**
 * @ClassName: GameInit
 * @Description: 服务器初始化
 * @author 何金成
 * @date 2015年5月23日 下午4:21:19
 * 
 */
public class GameInit {
	public static String confFileBasePath = "/";// 配置文件根目录
	public static Config cfg;// 读取server.properties配置
	private static final Logger logger = LoggerFactory
			.getLogger(GameInit.class);

	public static void init() {
		try {
			logger.info("================开启管理服务器================");
			// 加载服务器配置文件
			logger.info("加载服务器配置文件");
			confInit();
			// 加载敏感词语
			logger.info("加载敏感词语");
			SensitiveFilter.getInstance();
			// 加载Redis
			final Redis r = Redis.getInstance();
			new Thread(new Runnable() {// 测试Redis
						@Override
						public void run() {
							try {
								r.test();
							} catch (Exception e) {
								logger.info("Redis未启动，初始化异常");
							}
						}
					}).start();
			//memcached
			MemcachedCRUD.getInstance();
			// 加载hibernate
			logger.info("加载hibernate");
			HibernateUtil.init();
			logger.info("================完成开启管理服务器================");
		} catch (Throwable e) {
			CoreServlet.logger.error("初始化异常 {}", e);
			System.exit(0);
		}
	}

	public static void confInit() {
		cfg = new Config();
		cfg.loadConfig();
	}
}
