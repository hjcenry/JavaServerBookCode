package com.hjc.demo.json;

import com.google.gson.Gson;

public class GsonDemo {

	public static void main(String[] args) {
		String jsonString = null;
		// Java序列化为Json
		JsonObject obj = new JsonObject();
		obj.setId(1l);
		obj.setOrderId("2016_3_27");
		JsonSubObject subObject = new JsonSubObject();
		subObject.setCode(1001);
		subObject.setMsg("message");
		obj.setSubObject(subObject);
		Gson gson = new Gson();
		jsonString = gson.toJson(obj);
		System.out.println("json string:");
		System.out.println(jsonString);
		// Json反序列化为Java
		obj = gson.fromJson(jsonString, obj.getClass());
		System.out.println("Java Object:");
		System.out.println(obj);
		System.out.println("id:" + obj.getId());
		System.out.println("orderId:" + obj.getOrderId());
		System.out.println("sub code:" + obj.getSubObject().getCode());
		System.out.println("sub msg:" + obj.getSubObject().getMsg());
	}

}
