package com.hjc.demo.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * @ClassName: MongoUtil
 * @Description: mongo
 * @author 何金成
 * @date 2016年1月19日 下午3:35:25
 * 
 */
public class MongoUtil {
	private MongoClient mongo = null;
	private DB db = null;
	private static final Map<String, MongoUtil> instances = new ConcurrentHashMap<String, MongoUtil>();
	public static final String DB_ID = "id";// DB中id字段名
	public static final String DB = "war";// DB中id字段名

	/**
	 * 实例化
	 * 
	 * @return MongoDBManager对象
	 */
	static {
		getInstance("db");// 初始化默认的MongoDB数据库
	}

	public static MongoUtil getInstance() {
		return getInstance("db");// 配置文件默认数据库前缀为db
	}

	public static MongoUtil getInstance(String dbName) {
		MongoUtil mongoMgr = instances.get(dbName);
		if (mongoMgr == null) {
			mongoMgr = buildInstance(dbName);
			if (mongoMgr == null) {
				return null;
			}
			instances.put(dbName, mongoMgr);
		}
		return mongoMgr;
	}

	private static synchronized MongoUtil buildInstance(String dbName) {
		MongoUtil mongoMgr = new MongoUtil();
		try {
			mongoMgr.mongo = new MongoClient(getServerAddress(dbName),
					getMongoCredential(dbName), getDBOptions(dbName));
			mongoMgr.db = mongoMgr.mongo.getDB("war");
			System.out.println("connect to MongoDB success!");
			boolean flag = mongoMgr.db.authenticate("username",
					"123456".toCharArray());
			if (!flag) {
				System.out.println("MongoDB auth failed");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return mongoMgr;
	}

	/**
	 * 获取集合（表）
	 * 
	 * @param collection
	 */
	public DBCollection getCollection(String collection) {
		db.requestStart();
		DBCollection collect = db.getCollection(collection);
		return collect;
	}

	/**
	 * 插入
	 * 
	 * @param collection
	 * @param o
	 */
	public void insert(String collection, DBObject o) {
		getCollection(collection).insert(o);
		db.requestDone();
	}

	/**
	 * 批量插入
	 * 
	 * @param collection
	 * @param list
	 */
	public void insertBatch(String collection, List<DBObject> list) {
		if (list == null || list.isEmpty()) {
			return;
		}
		getCollection(collection).insert(list);
		db.requestDone();
	}

	/**
	 * 删除
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 */
	public List<DBObject> delete(String collection, DBObject q) {
		getCollection(collection).remove(q);
		List<DBObject> list = find(collection, q);
		db.requestDone();
		return list;
	}

	/**
	 * 批量删除
	 * 
	 * @param collection
	 * @param list
	 *            删除条件列表
	 */
	public void deleteBatch(String collection, List<DBObject> list) {
		if (list == null || list.isEmpty()) {
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			// 批量条件删除
			delete(collection, list.get(i));
		}
		db.requestDone();
	}

	/**
	 * 计算集合总条数
	 * 
	 * @param collection
	 */
	public int getCount(String collection) {
		int count = (int) getCollection(collection).find().count();
		db.requestDone();
		return count;
	}

	/**
	 * 计算满足条件条数
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 */

	public long getCount(String collection, DBObject q) {
		long count = getCollection(collection).getCount(q);
		db.requestDone();
		return count;
	}

	/**
	 * 更新
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 * @param setFields
	 *            更新对象
	 * @return List<DBObject> 更新后的对象列表
	 */
	public List<DBObject> update(String collection, DBObject q,
			DBObject setFields) {
		getCollection(collection).findAndModify(q,
				new BasicDBObject("$set", setFields));
		List<DBObject> list = find(collection, q);
		db.requestDone();
		return list;
	}

	/**
	 * @Title: updateOne
	 * @Description: 更新一条数据
	 * @param collection
	 * @param q
	 * @param setFields
	 * @return DBObject
	 * @throws
	 */
	public DBObject updateOne(String collection, DBObject q, DBObject setFields) {
		DBObject ret = getCollection(collection).findAndModify(q, setFields);
		db.requestDone();
		return ret;
	}

	/**
	 * @Title: updateOne
	 * @Description: 更新一条数据
	 * @param collection
	 * @param q
	 * @param setFields
	 * @return DBObject
	 * @throws
	 */
	public DBObject updateOne(String collection, long userid, DBObject setFields) {
		BasicDBObject condition = new BasicDBObject(DB_ID, userid);
		DBObject ret = getCollection(collection).findAndModify(condition,
				setFields);
		db.requestDone();
		return ret;
	}

	/**
	 * 查找集合所有对象
	 * 
	 * @param collection
	 */
	public List<DBObject> findAll(String collection) {
		List<DBObject> list = getCollection(collection).find().toArray();
		db.requestDone();
		return list;
	}

	/**
	 * 查找集合所有对象特定字段
	 * 
	 * @param collection
	 */
	public List<DBObject> findAllByFields(String collection, DBObject fields) {
		List<DBObject> list = getCollection(collection).find(null, fields)
				.toArray();
		db.requestDone();
		return list;
	}

	/**
	 * 按顺序查找集合所有对象
	 * 
	 * @param collection
	 *            数据集
	 * @param orderBy
	 *            排序
	 */
	public List<DBObject> findAll(String collection, DBObject orderBy) {
		List<DBObject> list = getCollection(collection).find().sort(orderBy)
				.toArray();
		db.requestDone();
		return list;
	}

	/**
	 * 查找（返回一个对象）
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 */
	public DBObject findOne(String collection, DBObject q) {
		DBObject ret = findOne(collection, q, null);
		db.requestDone();
		return ret;
	}

	/**
	 * 查找（返回一个对象）
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 * @param fileds
	 *            返回字段
	 */
	public DBObject findOne(String collection, DBObject q, DBObject fields) {
		DBObject ret = fields == null ? getCollection(collection).findOne(q)
				: getCollection(collection).findOne(q, fields);
		db.requestDone();
		return ret;
	}

	/**
	 * 查找返回特定字段（返回一个List<DBObject>）
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 * @param fileds
	 *            返回字段
	 */
	public List<DBObject> findLess(String collection, DBObject q,
			DBObject fileds) {
		DBCursor c = getCollection(collection).find(q, fileds);
		if (c != null) {
			List<DBObject> list = c.toArray();
			db.requestDone();
			return list;
		} else {
			db.requestDone();
			return null;
		}
	}

	/**
	 * 查找返回特定字段（返回一个List<DBObject>）
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 * @param fileds
	 *            返回字段
	 * @param orderBy
	 *            排序
	 */
	public List<DBObject> findLess(String collection, DBObject q,
			DBObject fileds, DBObject orderBy) {
		DBCursor c = getCollection(collection).find(q, fileds).sort(orderBy);
		if (c != null) {
			List<DBObject> list = c.toArray();
			db.requestDone();
			return list;
		} else {
			db.requestDone();
			return null;
		}
	}

	/**
	 * 分页查找集合对象，返回特定字段
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 * @param fileds
	 *            返回字段
	 * @pageNo 第n页
	 * @perPageCount 每页记录数
	 */
	public List<DBObject> findLess(String collection, DBObject q,
			DBObject fileds, int pageNo, int perPageCount) {
		List<DBObject> list = getCollection(collection).find(q, fileds)
				.skip((pageNo - 1) * perPageCount).limit(perPageCount)
				.toArray();
		db.requestDone();
		return list;

	}

	/**
	 * 按顺序分页查找集合对象，返回特定字段
	 * 
	 * @param collection
	 *            集合
	 * @param q
	 *            查询条件
	 * @param fileds
	 *            返回字段
	 * @param orderBy
	 *            排序
	 * @param pageNo
	 *            第n页
	 * @param perPageCount
	 *            每页记录数
	 */
	public List<DBObject> findLess(String collection, DBObject q,
			DBObject fileds, DBObject orderBy, int pageNo, int perPageCount) {
		List<DBObject> list = getCollection(collection).find(q, fileds)
				.sort(orderBy).skip((pageNo - 1) * perPageCount)
				.limit(perPageCount).toArray();
		db.requestDone();
		return list;
	}

	/**
	 * 查找（返回一个List<DBObject>）
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 */
	public List<DBObject> find(String collection, DBObject q) {
		DBCursor c = getCollection(collection).find(q);
		if (c != null) {
			List<DBObject> list = c.toArray();
			db.requestDone();
			return list;
		} else {
			db.requestDone();
			return null;
		}
	}

	/**
	 * 按顺序查找（返回一个List<DBObject>）
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 * @param orderBy
	 *            排序
	 */
	public List<DBObject> find(String collection, DBObject q, DBObject orderBy) {
		DBCursor c = getCollection(collection).find(q).sort(orderBy);
		if (c != null) {
			List<DBObject> list = c.toArray();
			db.requestDone();
			return list;
		} else {
			db.requestDone();
			return null;
		}
	}

	/**
	 * 分页查找集合对象
	 * 
	 * @param collection
	 * @param q
	 *            查询条件
	 * @pageNo 第n页
	 * @perPageCount 每页记录数
	 */
	public List<DBObject> find(String collection, DBObject q, int pageNo,
			int perPageCount) {
		List<DBObject> list = getCollection(collection).find(q)
				.skip((pageNo - 1) * perPageCount).limit(perPageCount)
				.toArray();
		db.requestDone();
		return list;
	}

	/**
	 * 按顺序分页查找集合对象
	 * 
	 * @param collection
	 *            集合
	 * @param q
	 *            查询条件
	 * @param orderBy
	 *            排序
	 * @param pageNo
	 *            第n页
	 * @param perPageCount
	 *            每页记录数
	 */
	public List<DBObject> find(String collection, DBObject q, DBObject orderBy,
			int pageNo, int perPageCount) {
		List<DBObject> list = getCollection(collection).find(q).sort(orderBy)
				.skip((pageNo - 1) * perPageCount).limit(perPageCount)
				.toArray();
		db.requestDone();
		return list;
	}

	/**
	 * distinct操作
	 * 
	 * @param collection
	 *            集合
	 * @param field
	 *            distinct字段名称
	 */
	public Object[] distinct(String collection, String field) {
		Object[] ret = getCollection(collection).distinct(field).toArray();
		db.requestDone();
		return ret;
	}

	/**
	 * distinct操作
	 * 
	 * @param collection
	 *            集合
	 * @param field
	 *            distinct字段名称
	 * @param q
	 *            查询条件
	 */
	public Object[] distinct(String collection, String field, DBObject q) {
		Object[] ret = getCollection(collection).distinct(field, q).toArray();
		db.requestDone();
		return ret;
	}

	/**
	 * group分组查询操作，返回结果少于10,000keys时可以使用
	 * 
	 * @param collection
	 *            集合
	 * @param key
	 *            分组查询字段
	 * @param q
	 *            查询条件
	 * @param reduce
	 *            reduce Javascript函数，如：function(obj,
	 *            out){out.count++;out.csum=obj.c;}
	 * @param finalize
	 *            reduce
	 *            function返回结果处理Javascript函数，如：function(out){out.avg=out.csum
	 *            /out.count;}
	 */
	public BasicDBList group(String collection, DBObject key, DBObject q,
			DBObject initial, String reduce, String finalize) {
		BasicDBList list = ((BasicDBList) getCollection(collection).group(key,
				q, initial, reduce, finalize));
		db.requestDone();
		return list;
	}

	/**
	 * group分组查询操作，返回结果大于10,000keys时可以使用
	 * 
	 * @param collection
	 *            集合
	 * @param map
	 *            映射javascript函数字符串，如：function(){ for(var key in this) {
	 *            emit(key,{count:1}) } }
	 * @param reduce
	 *            reduce Javascript函数字符串，如：function(key,emits){ total=0; for(var
	 *            i in emits){ total+=emits[i].count; } return {count:total}; }
	 * @param q
	 *            分组查询条件
	 * @param orderBy
	 *            分组查询排序
	 */
	public Iterable<DBObject> mapReduce(String collection, String map,
			String reduce, DBObject q, DBObject orderBy) {
		// DBCollection coll = db.getCollection(collection);
		// MapReduceCommand cmd = new MapReduceCommand(coll, map, reduce, null,
		// MapReduceCommand.OutputType.INLINE, q);
		// return coll.mapReduce(cmd).results();
		MapReduceOutput out = getCollection(collection).mapReduce(map, reduce,
				null, q);
		Iterable<DBObject> list = out.getOutputCollection().find()
				.sort(orderBy).toArray();
		db.requestDone();
		return list;
	}

	/**
	 * group分组分页查询操作，返回结果大于10,000keys时可以使用
	 * 
	 * @param collection
	 *            集合
	 * @param map
	 *            映射javascript函数字符串，如：function(){ for(var key in this) {
	 *            emit(key,{count:1}) } }
	 * @param reduce
	 *            reduce Javascript函数字符串，如：function(key,emits){ total=0; for(var
	 *            i in emits){ total+=emits[i].count; } return {count:total}; }
	 * @param q
	 *            分组查询条件
	 * @param orderBy
	 *            分组查询排序
	 * @param pageNo
	 *            第n页
	 * @param perPageCount
	 *            每页记录数
	 */
	public List<DBObject> mapReduce(String collection, String map,
			String reduce, DBObject q, DBObject orderBy, int pageNo,
			int perPageCount) {
		MapReduceOutput out = getCollection(collection).mapReduce(map, reduce,
				null, q);
		List<DBObject> list = out.getOutputCollection().find().sort(orderBy)
				.skip((pageNo - 1) * perPageCount).limit(perPageCount)
				.toArray();
		db.requestDone();
		return list;
	}

	/**
	 * group分组查询操作，返回结果大于10,000keys时可以使用
	 * 
	 * @param collection
	 *            集合
	 * @param map
	 *            映射javascript函数字符串，如：function(){ for(var key in this) {
	 *            emit(key,{count:1}) } }
	 * @param reduce
	 *            reduce Javascript函数字符串，如：function(key,emits){ total=0; for(var
	 *            i in emits){ total+=emits[i].count; } return {count:total}; }
	 * @param outputCollectionName
	 *            输出结果表名称
	 * @param q
	 *            分组查询条件
	 * @param orderBy
	 *            分组查询排序
	 */
	public List<DBObject> mapReduce(String collection, String map,
			String reduce, String outputCollectionName, DBObject q,
			DBObject orderBy) {
		db.requestStart();
		if (!db.collectionExists(outputCollectionName)) {
			getCollection(collection).mapReduce(map, reduce,
					outputCollectionName, q);
		}
		List<DBObject> list = getCollection(outputCollectionName)
				.find(null, new BasicDBObject("_id", false)).sort(orderBy)
				.toArray();
		db.requestDone();
		return list;
	}

	/**
	 * group分组分页查询操作，返回结果大于10,000keys时可以使用
	 * 
	 * @param collection
	 *            集合
	 * @param map
	 *            映射javascript函数字符串，如：function(){ for(var key in this) {
	 *            emit(key,{count:1}) } }
	 * @param reduce
	 *            reduce Javascript函数字符串，如：function(key,emits){ total=0; for(var
	 *            i in emits){ total+=emits[i].count; } return {count:total}; }
	 * @param outputCollectionName
	 *            输出结果表名称
	 * @param q
	 *            分组查询条件
	 * @param orderBy
	 *            分组查询排序
	 * @param pageNo
	 *            第n页
	 * @param perPageCount
	 *            每页记录数
	 */
	public List<DBObject> mapReduce(String collection, String map,
			String reduce, String outputCollectionName, DBObject q,
			DBObject orderBy, int pageNo, int perPageCount) {
		db.requestStart();
		if (!db.collectionExists(outputCollectionName)) {
			getCollection(collection).mapReduce(map, reduce,
					outputCollectionName, q);
		}
		List<DBObject> list = getCollection(outputCollectionName)
				.find(null, new BasicDBObject("_id", false)).sort(orderBy)
				.skip((pageNo - 1) * perPageCount).limit(perPageCount)
				.toArray();
		db.requestDone();
		return list;
	}

	/**
	 * @param t
	 * @return
	 */
	public <T> Long getTableIDMax(Class<T> t) {
		Long id = 1l;
		DBCursor cursor = getCollection(t.getSimpleName()).find()
				.sort(new BasicDBObject(DB_ID, -1)).limit(1);
		List<DBObject> list = cursor.toArray();
		if (list.size() > 0) {
			DBObject ret = list.get(0);
			id = (Long) ret.get(DB_ID);
		}
		return id;
	}

	/**
	 * @Title: getServerAddress
	 * @Description: 获取数据库服务器列表
	 * @param dbName
	 * @return
	 * @throws UnknownHostException
	 * @return List<ServerAddress>
	 * @throws
	 */
	private static List<ServerAddress> getServerAddress(String dbName)
			throws UnknownHostException {
		List<ServerAddress> list = new ArrayList<ServerAddress>();
		String hosts = "127.0.0.1:27017";
		for (String host : hosts.split("&")) {
			String ip = host.split(":")[0];
			String port = host.split(":")[1];
			list.add(new ServerAddress(ip, Integer.parseInt(port)));
		}
		return list;
	}

	/**
	 * @Title: getMongoCredential
	 * @Description: 获取数据库安全验证信息
	 * @param dbName
	 * @return
	 * @return List<MongoCredential>
	 * @throws
	 */
	private static List<MongoCredential> getMongoCredential(String dbName) {
		String username = "username";
		String password = "123456";
		String database = "war";
		MongoCredential credentials = MongoCredential.createMongoCRCredential(
				username, database, password.toCharArray());
		List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
		credentialsList.add(credentials);
		return credentialsList;
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
		build.connectionsPerHost(50); // 与目标数据库能够建立的最大connection数量为50
		build.threadsAllowedToBlockForConnectionMultiplier(50); // 如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
		build.maxWaitTime(120000);
		build.connectTimeout(60000);
		MongoClientOptions myOptions = build.build();
		return myOptions;
	}

	public static void main(String[] args) {
		try {
			System.out
					.println("/////////////////直接使用DBObject调用MongoUtil//////////////////");
			// API使用
			System.out.println("=================insert==============");
			getInstance().insert(
					MongoCollections.USER_DATA,
					new BasicDBObject().append("name", "admin3")
							.append("type", "2").append("score", 70)
							.append("level", 2)
							.append("inputTime", System.currentTimeMillis()));
			System.out.println("=================update==============");
			getInstance().update(MongoCollections.USER_DATA,
					new BasicDBObject().append("status", 1),
					new BasicDBObject().append("status", 2));
			// === group start =============
			System.out.println("=================group==============");
			StringBuilder sb = new StringBuilder(100);
			sb.append("function(obj, out){out.count++;out.").append("scoreSum")
					.append("+=obj.").append("score").append(";out.")
					.append("levelSum").append("+=obj.").append("level")
					.append('}');
			String reduce = sb.toString();
			BasicDBList list = getInstance().group(
					MongoCollections.USER_DATA,
					new BasicDBObject("type", true),
					new BasicDBObject(),
					new BasicDBObject().append("count", 0)
							.append("scoreSum", 0).append("levelSum", 0)
							.append("levelAvg", (Double) 0.0), reduce,
					"function(out){ out.levelAvg = out.levelSum / out.count }");
			for (Object o : list) {
				DBObject obj = (DBObject) o;
				System.out.println(obj);
			}
			// ======= group end=========
			// === mapreduce start =============
			System.out
					.println("=================mapreduce start==============");
			List<DBObject> list2 = getInstance()
					.mapReduce(
							MongoCollections.USER_DATA,
							"function(){emit({type:this.type},{type:this.type,score:this.score,level:this.level});}",
							"function(key,values){var result={type:key.type,score:0,level:0};var count=0;values.forEach(function(value){result.score+=value.score;result.level+=value.level;count++});result.level=result.level/count;return result;}",
							"group_temp_user", new BasicDBObject(),
							new BasicDBObject("score", 1));
			for (DBObject o : list2) {
				System.out.println(o);
			}
			// ======= mapreduce end=========
			System.out.println("=================find all==============");
			System.out.println(getInstance()
					.findAll(MongoCollections.USER_DATA));
			System.out.println("=================find==============");
			System.out.println(getInstance().find(
					MongoCollections.USER_DATA,
					new BasicDBObject("inputTime", new BasicDBObject("$gt",
							1348020002890L)),
					new BasicDBObject().append("_id", "-1"), 1, 2));
			System.out.println("=================delete==============");
			getInstance().delete(MongoCollections.USER_DATA,
					new BasicDBObject());
			System.out.println(getInstance().findAll(MongoCollections.USER_DATA));
			System.out
					.println("/////////////////JavaBean与DBObject的转换封装使用//////////////////");
			// 封装后的MongoUtil的使用
			// 声明一个Bean
			TestBean bean1 = new TestBean();
			bean1.setId(1l);
			bean1.setMsg("test bean1");
			bean1.setScore(200.01d);
			SubBean sub1 = new SubBean();
			sub1.setId(1l);
			sub1.setStr("sub bean 1");
			List<SubBean> subBeans = new ArrayList<SubBean>();
			subBeans.add(sub1);
			bean1.setSubBean(sub1);
			// 利用DBObjectUtil工具类将Javabean转成DBObject
			System.out.println("===============insert===============");
			MongoUtil.getInstance().insert(MongoCollections.USER_DATA,
					DBObjectUtil.<TestBean>bean2DBObject(bean1));
			bean1.setId(2l);// 修改id再插入一条数据
			MongoUtil.getInstance().insert(MongoCollections.USER_DATA,
					DBObjectUtil.bean2DBObject(bean1));
			BasicDBObject query = new BasicDBObject();
			query.put("id", 1l);// 查询id为1的数据
			BasicDBObject setFields = new BasicDBObject();
			setFields.put("str", "test bean 1 update");// 查询id为1的数据
			System.out.println("===============update===============");
			MongoUtil.getInstance().update(MongoCollections.USER_DATA, query,
					setFields);
			System.out.println("===============delete===============");
			BasicDBObject delObj = new BasicDBObject();
			delObj.put(DB_ID, 2l);
			MongoUtil.getInstance().delete(MongoCollections.USER_DATA, delObj);
			System.out.println("===============find all===============");
			List<DBObject> findList = MongoUtil.getInstance().findAll(
					MongoCollections.USER_DATA);
			for (DBObject dbObject : findList) {
				TestBean findBean = new TestBean();
				System.out.println("for each list:");
				System.out.println(dbObject.toString());
				findBean = DBObjectUtil.<TestBean> dbObject2Bean(dbObject,
						TestBean.class);
				System.out.println(findBean.getId());
				System.out.println(findBean.getMsg());
				System.out.println(findBean.getScore());
				System.out.println(findBean.getSubBean().getId());
				System.out.println(findBean.getSubBean().getStr());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
