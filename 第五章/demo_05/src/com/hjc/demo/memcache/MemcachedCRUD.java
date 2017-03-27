package com.hjc.demo.memcache;

import java.util.Arrays;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * @author 何金成
 */
public class MemcachedCRUD {
	public static String poolName = "gameDBPool";
	protected static MemCachedClient memCachedClient;
	protected static MemcachedCRUD memcachedCRUD = new MemcachedCRUD();
	public static SockIOPool sockIoPool;
	static {
		sockIoPool = init(poolName, "cacheServer");
		memCachedClient = new MemCachedClient(poolName);
		// if true, then store all primitives as their string value.
		memCachedClient.setPrimitiveAsString(true);
	}

	public static SockIOPool init(String poolName, String confKey) {
		// 缓存服务器
		String server[] = { "123.57.211.130:11211" };
		// 创建一个连接池
		SockIOPool pool = SockIOPool.getInstance(poolName);
		System.out.println("连接池"+poolName+"缓存配置"+Arrays.toString(server)+"");
		pool.setServers(server);// 缓存服务器
		pool.setInitConn(50); // 初始化链接数
		pool.setMinConn(50); // 最小链接数
		pool.setMaxConn(500); // 最大连接数
		pool.setMaxIdle(1000 * 60 * 60);// 最大处理时间
		pool.setMaintSleep(3000);// 设置主线程睡眠时,每3秒苏醒一次，维持连接池大小
		pool.setNagle(false);// 关闭套接字缓存
		pool.setSocketTO(3000);// 链接建立后超时时间
		pool.setSocketConnectTO(0);// 链接建立时的超时时间
		pool.initialize();
		return pool;
	}

	public static void destroy() {
		sockIoPool.shutDown();
	}

	MemcachedCRUD() {
	}

	public static MemcachedCRUD getInstance() {
		return memcachedCRUD;
	}

	private static final long INTERVAL = 100;

	public boolean add(String key, Object o) {
		return memCachedClient.add(key, o);
	}

	public boolean update(String key, Object o) {
		return memCachedClient.replace(key, o);
	}

	public boolean saveObject(String key, Object msg) {
		boolean o = memCachedClient.keyExists(key);
		if (o) {// 存在替换掉
			return memCachedClient.replace(key, msg);
		} else {
			return memCachedClient.add(key, msg);
		}
	}

	public boolean keyExist(String key) {
		return memCachedClient.keyExists(key);
	}

	/**
	 * delete
	 * 
	 * @param key
	 */
	public boolean deleteObject(String key) {
		return memCachedClient.delete(key);
	}

	public Object getObject(String key) {
		Object obj = memCachedClient.get(key);
		return obj;
	}

	public static MemCachedClient getMemCachedClient() {
		return memCachedClient;
	}

}