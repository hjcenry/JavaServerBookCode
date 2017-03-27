package com.hjc.demo.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisDemo {

	public static void main(String[] args) {
		RedisDemo demo = new RedisDemo();
		demo.run();
	}

	public void run() {
		Redis redis = Redis.getInstance();
		/** redis save **/
		System.out.println("=============redis save==============");
		// string save
		System.out.println("string save:调用set时，若key不存在则添加key，否则为修改key对应的值");
		redis.set(Redis.DB, "testKey1", "test string val1");
		// set save
		System.out.println("set save:set中的元素不允许出现重复且无序");
		redis.sadd(Redis.DB, "testKey2", "test set val1");
		redis.sadd(Redis.DB, "testKey2", "test set val2");
		redis.sadd(Redis.DB, "testKey2", "test set val3");
		// hash save
		System.out
				.println("hash save:调用hset时，若key不存在则创建key，若hash中存在这个hashkey，则修改其值，不存在则添加一条hash数据");
		redis.hset(Redis.DB, "testKey3", "hashKey1", "hashVal1");
		redis.hset(Redis.DB, "testKey3", "hashKey2", "hashVal2");
		redis.hset(Redis.DB, "testKey3", "hashKey3", "hashVal3");
		redis.hset(Redis.DB, "testKey3", "hashKey4", "hashVal4");
		// list save
		System.out.println("list save:数据在链表中是有序的，并可以重复添加数据");
		redis.lpush_(Redis.DB, "testKey4", "test list val1");
		redis.lpush_(Redis.DB, "testKey4", "test list val2");
		redis.lpush_(Redis.DB, "testKey4", "test list val3");
		// sorted set save
		System.out.println("sorted set save:有序set中的元素是有序的");
		redis.zadd(Redis.DB, "testKey5", 1, "test zset val1");
		redis.zadd(Redis.DB, "testKey5", 2, "test zset val2");
		redis.zadd(Redis.DB, "testKey5", 3, "test zset val3");
		redis.zadd(Redis.DB, "testKey5", 4, "test zset val4");
		/** redis get **/
		System.out.println("=============redis get==============");
		// string get
		String stringRet = redis.get(Redis.DB, "testKey1");
		System.out.println("string get:" + stringRet);
		// set get
		Set<String> setRet = redis.sget(Redis.DB, "testKey2");
		System.out.print("set get:");
		for (String string : setRet) {
			System.out.print(string + ",");
		}
		System.out.println();
		// hash get
		String hashKeyRet = redis.hget(Redis.DB, "testKey3", "hashKey2");
		System.out.println("hash key get:" + hashKeyRet);
		Map<String, String> hashRet = redis.hgetAll(Redis.DB, "testKey3");
		System.out.print("hash get:");
		for (String string : hashRet.keySet()) {
			System.out.print("key[" + string + "]" + "value["
					+ hashRet.get(string) + "],");
		}
		System.out.println();
		// list get
		List<String> listRet = redis.lgetList(Redis.DB, "testKey4");
		System.out.print("list get:");
		for (String string : listRet) {
			System.out.println(string + ",");
		}
		// zset get
		long val2Rank = redis.zrank(Redis.DB, "testKey5", "test zset val2");
		System.out.println("zset get val2 rank:" + val2Rank);
		Set<String> zsetRet = redis.zrange(Redis.DB, "testKey5", 0, 3);
		System.out.print("zset get range:");
		for (String string : zsetRet) {
			System.out.println(string + ",");
		}
		/** redis delete **/
		System.out.println("=============redis delete==============");
		// string delete
		System.out
				.println("string delete:调用Redis的del方法，可直接删除key，对于所有的数据类型来说，都可以通过这种方式直接删除整个key");
		redis.del(Redis.DB, "testKey1");
		// set delete
		System.out.println("set delete:删除set中的val3");
		redis.sremove(Redis.DB, "testKey2", "test set val3");
		// hash delete
		System.out.println("hash delete:删除hash中key为hashKey4的元素");
		redis.hdel(Redis.DB, "testKey3", "hashKey4");
		// list delete
		System.out
				.println("list delete:删除list中值为test list val3的元素，其中count参数，0代表删除全部，正数代表正向删除count个此元素，负数代表负向删除count个此元素");
		redis.lrem(Redis.DB, "testKey4", 0, "test list val3");
		// zset delete
		System.out.println("zset delete:同set删除元素的方式相同");
		redis.zrem(Redis.DB, "testKey5", "test zset val4");
		System.out
				.println("除了以上常用api之外，还有更多api，在Redis类中都有列出，请参考Redis类，或直接参照Jedis的官方文档");
	}
}
