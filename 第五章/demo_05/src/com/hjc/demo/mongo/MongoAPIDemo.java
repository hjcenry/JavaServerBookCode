package com.hjc.demo.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;

/**
 * 保存对象API
 */
public class MongoAPIDemo {

	public static void main(String[] args) throws UnknownHostException {
		// 第一：实例化mongo对象，连接mongodb服务器 包含所有的数据库
		// 默认构造方法，默认是连接本机，端口号，默认是27017
		// 获取MongoCredential
		MongoCredential credentials = MongoCredential.createMongoCRCredential(
				"kidbear", "war", "123321".toCharArray());
		List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
		credentialsList.add(credentials);
		// 获取DBOptions
		MongoClientOptions.Builder build = new MongoClientOptions.Builder();
		build.connectionsPerHost(50); // 与目标数据库能够建立的最大connection数量为50
		build.threadsAllowedToBlockForConnectionMultiplier(50); // 如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
		build.maxWaitTime(120000);
		build.connectTimeout(60000);
		MongoClientOptions myOptions = build.build();
		// 获取ServerAddress
		ServerAddress addr = new ServerAddress();
		String hosts = "123.57.211.130:27017";
		for (String host : hosts.split("&")) {
			String ip = host.split(":")[0];
			String port = host.split(":")[1];
			addr = new ServerAddress(ip, Integer.parseInt(port));
		}
		// new Mongo实例
		Mongo mongo = new MongoClient(addr, credentialsList, myOptions);
		// 第二：连接具体的数据库
		// 其中参数是具体数据库的名称，若服务器中不存在，会自动创建
		DB db = mongo.getDB("war");
		// 第三：操作具体的表
		// 在mongodb中没有表的概念，而是指集合
		// 其中参数是数据库中表，若不存在，会自动创建
		DBCollection collection = db.getCollection(MongoCollections.USER_DATA);
		// 添加操作
		// 在mongodb中没有行的概念，而是指文档
		BasicDBObject document = new BasicDBObject();
		document.put("id", 1);
		document.put("name", "小明");
		// 然后保存到集合中
		collection.insert(document);
		// 当然也可以保存这样的json串
		/*
		 * { "id":1, "name","小明", "address": { "city":"beijing", "code":"065000"
		 * } }
		 */
		// 实现上述json串思路如下：
		// 第一种：类似xml时，不断添加
		BasicDBObject addressDocument = new BasicDBObject();
		addressDocument.put("city", "beijing");
		addressDocument.put("code", "065000");
		document = new BasicDBObject();
		document.put("address", addressDocument);
		// 然后保存数据库中
		collection.insert(document);
		// 第二种：直接把json存到数据库中
		String jsonTest = "{'id':1,'name':'小明',"
				+ "'address':{'city':'beijing','code':'065000'}" + "}";
		DBObject dbobjct = (DBObject) JSON.parse(jsonTest);
		collection.insert(dbobjct);
	}

}
