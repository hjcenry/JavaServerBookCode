package com.hjc.demo.mongo;

import java.lang.reflect.InvocationTargetException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * @author 何金成 DBObject和Javabean的相关转换 中间借助fastjson，使得json字符串作为两者转换的中间形式
 *
 */
public class DBObjectUtil {

	/**
	 * 把实体bean对象转换成DBObject
	 * 
	 * @param bean
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T> DBObject bean2DBObject(T bean) {
		if (bean == null) {
			return null;
		}
		DBObject dbObject = new BasicDBObject();
		// 使用fastjson转换javabean为json字符串
		String json = com.alibaba.fastjson.JSON.toJSONString(bean);
		// 使用Mongo提供的Json工具类转为DBObject对象
		dbObject = (DBObject) JSON.parse(json);
		return dbObject;
	}

	/**
	 * 把DBObject转换成bean对象
	 * 
	 * @param dbObject
	 * @param bean
	 * @return
	 * @throws Exception
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T dbObject2Bean(DBObject dbObj, Class<T> clz)
			throws Exception {
		if (dbObj == null) {
			return null;
		}
		// 使用fastjson把DBObject对象转化为json字符串
		String json = com.alibaba.fastjson.JSON.toJSONString(dbObj);
		// 使用fastjson把Json字符串转换为Javabean
		T obj = com.alibaba.fastjson.JSON.parseObject(json, clz);
		return obj;
	}

}
