package com.hjc.demo.util;

public class DBUtil {
	public static void insert(Object obj) {

	}

	public static void update(Object obj) {

	}

	public static Object find(String where) {
		Object ret = CacheUtil.find(where);
		if (ret != null) {
			return ret;
		} else {

		}
		return ret;
	}

	public static Object list(String where) {
		return null;
	}

	public static void delete(Object obj) {

	}
}
