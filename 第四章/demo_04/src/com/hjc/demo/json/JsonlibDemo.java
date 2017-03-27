package com.hjc.demo.json;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class JsonlibDemo {

	public static void main(String[] args) {
		// Java序列化为Json
		JsonObject obj = new JsonObject();
		obj.setId(1l);
		obj.setOrderId("2016_3_27");
		JsonSubObject subObject = new JsonSubObject();
		subObject.setCode(1001);
		subObject.setMsg("message");
		obj.setSubObject(subObject);
		JSONObject jsonObject = JSONObject.fromObject(obj);
		System.out.println("json string:");
		System.out.println(jsonObject.toString());
		// Json反序列化为Java
		// 如果对象中含有复杂子对象，如：List、Map或自定义Javabean的时候需要如下的ClassMap，否则早转换过程中会报错
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("subObject", JsonSubObject.class);
		obj = (JsonObject) JSONObject.toBean(jsonObject, JsonObject.class,
				classMap);
		System.out.println("Java Object:");
		System.out.println(obj);
		System.out.println("id:" + obj.getId());
		System.out.println("orderId:" + obj.getOrderId());
		System.out.println("sub code:" + obj.getSubObject().getCode());
		System.out.println("sub msg:" + obj.getSubObject().getMsg());
	}

}
