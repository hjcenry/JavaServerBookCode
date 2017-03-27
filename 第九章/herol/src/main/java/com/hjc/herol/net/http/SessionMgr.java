package com.hjc.herol.net.http;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjc.herol.util.memcached.MemcachedCRUD;
import com.hjc.herol.util.mongo.MongoCollections;
import com.hjc.herol.util.mongo.MongoUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class SessionMgr {
	public Logger logger = LoggerFactory.getLogger(SessionMgr.class);
	public MemcachedCRUD memcached;
	private static SessionMgr inst;

	private SessionMgr() {
		memcached = MemcachedCRUD.getInstance();
	}

	public static SessionMgr getInstance() {
		if (inst == null) {
			inst = new SessionMgr();
		}
		return inst;
	}

	public void addUser(long userId) {
		memcached.saveObject(SessionKey.CACHE_ONLINE + userId, 1);// 上线
	}

	public void removeUser(long userId) {
		memcached.saveObject(SessionKey.CACHE_ONLINE + userId, 0);// 下线
	}

	public void removeAll() {
//		BasicDBObject fields = new BasicDBObject(MongoUtil.DB_ID, 1);
//		List<DBObject> objects = MongoUtil.getInstance().findAllByFields(
//				MongoCollections.USER_DATA, fields);
//		if (objects.size() == 0) {
//			return;
//		}
//		for (DBObject dbObject : objects) {
//			Long userId = (Long) dbObject.get(MongoUtil.DB_ID);
//			if (memcached.keyExist(SessionKey.CACHE_ONLINE + userId)) {
//				memcached.saveObject(SessionKey.CACHE_ONLINE + userId, 0);// 下线
//			}
//		}
	}
}
