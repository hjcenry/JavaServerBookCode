package com.hjc.demo.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class JacksonDemo {

	public static void main(String[] args) {
		String jsonString = null;
		// Java序列化为Json
		try {
			// 创建一个Java对象
			Map jsonMap = new HashMap();
			ObjectMapper mapper = new ObjectMapper();
			jsonMap.put("param1", "value1");
			jsonMap.put("param2", "value2");
			JsonObject obj = new JsonObject();
			obj.setId(1l);
			obj.setOrderId("2016_3_27");
			JsonSubObject subObject = new JsonSubObject();
			subObject.setCode(1001);
			subObject.setMsg("message");
			obj.setSubObject(subObject);
			// 转化为Json字符串
			jsonMap.put("param3", mapper.writeValueAsString(obj));
			jsonString = mapper.writeValueAsString(jsonMap);
			System.out.println("Json String:");
			System.out.println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Json反序列化为Java
		try {
			ObjectMapper mapper2 = new ObjectMapper();
			JsonNode root = mapper2.readTree(jsonString);
			Map jsonMap = mapper2.readValue(jsonString, Map.class);
			System.out.println("Java Object:");
			System.out.println(jsonMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
