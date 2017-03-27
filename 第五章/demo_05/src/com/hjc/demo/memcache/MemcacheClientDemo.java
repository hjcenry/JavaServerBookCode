package com.hjc.demo.memcache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemcacheClientDemo {

	public static void main(String[] args) {
		MemcacheClientDemo demo = new MemcacheClientDemo();
		demo.run();
	}

	public void run() {
		/** memcache add **/
		MemcachedCRUD memcache = MemcachedCRUD.getInstance();
		System.out.println("===========memcache add==============");
		memcache.add("testKey1", 1);
		memcache.add("testKey2", "test value");
		List<String> list = new ArrayList<String>();
		list.add("string1");
		list.add("string2");
		list.add("string3");
		list.add("string4");
		memcache.add("testKey3", list);
		Map<Long, List<String>> map = new HashMap<Long, List<String>>();
		map.put(1l, list);
		map.put(2l, null);
		map.put(3l, list);
		memcache.add("testKey4", map);
		/** memcache get **/
		System.out.println("===========memcache get==============");
		int val1 = Integer.parseInt((String) memcache.getObject("testKey1"));
		System.out.println("val1:" + val1);
		String val2 = (String) memcache.getObject("testKey2");
		System.out.println("val2:" + val2);
		List<String> val3 = (List<String>) memcache.getObject("testKey3");
		System.out.print("val3:");
		for (String string : val3) {
			System.out.print(string + ",");
		}
		System.out.println();
		Map<Long, List<String>> val4 = (Map<Long, List<String>>) memcache
				.getObject("testKey4");
		System.out.print("val4:");
		for (Long key : val4.keySet()) {
			System.out.print("key:" + key + ",value:" + val4.get(key));
		}
		System.out.println();
		/** memcache update **/
		System.out.println("===========memcache update==============");
		memcache.saveObject("testKey2", "val after update");
		String updateVal = (String) memcache.getObject("testKey2");
		System.out.println("val2 after update:" + updateVal);
		/** memcache key Exist **/
		System.out.println("===========memcache key Exist==============");
		boolean flag = memcache.keyExist("testKey2");
		System.out.println("is testKey2 exist:" + flag);
		/** memcache delete **/
		System.out.println("===========memcache delete==============");
		memcache.deleteObject("testKey3");
		boolean delFlag = memcache.keyExist("testKey3");
		System.out.println("is testKey3 exist after delete:" + delFlag);
	}

}
