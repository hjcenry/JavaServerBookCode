package com.hjc.demo.json;

public class JsonObject {
	private long id;
	private String orderId;
	private JsonSubObject subObject;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public JsonSubObject getSubObject() {
		return subObject;
	}

	public void setSubObject(JsonSubObject subObject) {
		this.subObject = subObject;
	}
}
