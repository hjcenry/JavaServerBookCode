package com.hjc.herol.util.hibernate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.hjc.herol.core.GameInit;
import com.hjc.herol.util.memcached.MemcachedCRUD;

public class TableIDCreator {
	private static Logger logger = LoggerFactory
			.getLogger(TableIDCreator.class);
	public static String poolName = "TableIdDbPool";
	public static SockIOPool sockIoPool = MemcachedCRUD.init(poolName,
			"cacheServer");
	public static MemCachedClient memCachedClient = new MemCachedClient(
			poolName);
	static {
		memCachedClient.setPrimitiveAsString(true);
	}

	public static <T> long getTableID(Class<T> clazz, long startId) {
		StringBuffer key = new StringBuffer();
		key.append(GameInit.serverId).append("#").append(clazz.getName())
				.append("#id");
		// 表的主键ID从1开始
		Long id = null;
		if (memCachedClient.getCounter(key.toString()) == -1) {
			// 从数据库里查询该表当前主键的最大值
			id = HibernateUtil.getTableIDMax(clazz);
			if (id == null) {
				boolean ret = memCachedClient.storeCounter(key.toString(),
						startId);
				logger.info("A开始为table:{}设置主键ID:{} ret {}", key.toString(),
						startId, ret);
			} else {
				boolean ret = memCachedClient.storeCounter(key.toString(),
						Math.max(startId, id));// 即便数据库有记录，也比较该id是否满足参数startId的要求。
				logger.info("B开始为table:{}设置主键ID:{} ret {}", key.toString(), id,
						ret);
			}
		}
		id = memCachedClient.incr(key.toString(), 1);
		if (id == -1) {
			logger.error("table:{}主键增加失败", key.toString());
			return -1;
		} else {
			logger.info("table:{}的ID加1增长为{}", key.toString(), id);
		}
		return id;
	}
}
