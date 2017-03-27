package com.hjc.herol.util.mongo;

import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.hjc.herol.manager.player.Player;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

public class MorphiaUtil {
	public static Logger logger = LoggerFactory.getLogger(MorphiaUtil.class);
	public static Datastore ds;
	private static final String CONF_PATH = "/spring-mongodb/mongodb.properties";
	public static String dbName = "db";

	public static Datastore getDatastore() {
		if (ds == null) {
			ds = buildDatastore();
		}
		return ds;
	}

	public static Datastore buildDatastore() {
		MongoClient mongo = null;
		try {
			String hosts = getProperty(CONF_PATH, dbName + ".host");
			mongo = new MongoClient(new ServerAddress(hosts.split(":")[0],
					Integer.parseInt(hosts.split(":")[1])),
					getDBOptions(dbName));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		Morphia morphia = new Morphia();
		morphia.mapPackage("com.hjc.herol");
		ds = morphia.createDatastore(mongo, "herol");
		ds.ensureIndexes();
		if (ds == null) {
			logger.error("mongo connect failed");
		}
		return ds;
	}

	/**
	 * @Title: getDBOptions
	 * @Description: 获取数据参数设置
	 * @return
	 * @return MongoClientOptions
	 * @throws
	 */
	private static MongoClientOptions getDBOptions(String dbName) {
		MongoClientOptions.Builder build = new MongoClientOptions.Builder();
		build.connectionsPerHost(Integer.parseInt(getProperty(CONF_PATH, dbName
				+ ".connectionsPerHost"))); // 与目标数据库能够建立的最大connection数量为50
		build.threadsAllowedToBlockForConnectionMultiplier(Integer
				.parseInt(getProperty(CONF_PATH, dbName
						+ ".threadsAllowedToBlockForConnectionMultiplier"))); // 如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
		build.maxWaitTime(Integer.parseInt(getProperty(CONF_PATH, dbName
				+ ".maxWaitTime")));
		build.connectTimeout(Integer.parseInt(getProperty(CONF_PATH, dbName
				+ ".connectTimeout")));
		MongoClientOptions myOptions = build.build();
		return myOptions;
	}

	/**
	 * @Title: main
	 * @Description: Test Code
	 * @param args
	 *            void
	 * @throws
	 */
	public static void main(String[] args) {
		Datastore ds = MorphiaUtil.getDatastore();
		for (long i = 1l; i <= 3l; i++) {
			TestBean bean = new TestBean();
			bean.setId(i);
			bean.setMsg("test bean1");
			bean.setScore(100.2d);
			SubBean sub1 = new SubBean();
			sub1.setId(11l);
			sub1.setStr("sub bean 1");
			SubBean sub2 = new SubBean();
			sub2.setId(12l);
			sub2.setStr("sub bean 2");
			Map<Long, SubBean> subs = new HashMap<Long, SubBean>();
			subs.put(sub1.getId(), sub1);
			subs.put(sub2.getId(), sub2);
			bean.setSubBean(sub1);
			bean.setSubBeans(subs);
			Key<TestBean> key = ds.save(bean);
			logger.info(key.getCollection());
			logger.info(key.getClass() + "");
			logger.info(key.getId() + "");
			logger.info(key.getType() + "");
		}
		// query
		List<TestBean> list = ds.createQuery(TestBean.class).asList();
		for (TestBean testBean : list) {
			logger.info(JSON.toJSONString(testBean));
			// MorphiaUtil.deleteData(MorphiaUtil.ds.createQuery(TestBean.class)
			// .field("_id").equal(testBean.getId()));
		}
		// update
		ds.update(
				ds.createQuery(TestBean.class).field("_id").equal(2),
				ds.createUpdateOperations(TestBean.class).set(
						"subBeans.11.str", "update map val"));
		// query
		List<TestBean> list2 = ds.createQuery(TestBean.class).asList();
		for (TestBean testBean : list2) {
			logger.info(JSON.toJSONString(testBean));
		}
	}

	/**
	 * 根据properties文件的key获取value
	 * 
	 * @param filePath
	 *            properties文件路径
	 * @param key
	 *            属性key
	 * @return 属性value
	 */
	private static String getProperty(String filePath, String key) {
		Properties props = new Properties();
		try {
			InputStream in = MongoUtil.class.getResourceAsStream(filePath);
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			logger.info("load mongo properties exception {}", e);
			System.exit(0);
			return null;
		}
	}

	/**
	 * @param t
	 * @return
	 */
	public <T> Long getTableIDMax(Class<T> t) {
		long count = ds.find(Player.class).countAll();
		if (count <= 0) {
			count = 1;
		}
		return count;
	}
}
