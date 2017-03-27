package com.hjc.demo.net;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ProtoMessage implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -3460913241121151489L;
	private Short protoid;// 协议号
	private Long userid;// userid
	public JSONObject data;// 游戏传递数据（JSON格式）

	public ProtoMessage() {

	}

	public <T> ProtoMessage(Short protoid, T data) {
		this.protoid = protoid;
		this.setData(data);
	}

	public <T> ProtoMessage(T data) {
		this.setData(data);
	}

	public static ProtoMessage getResp(String msg, int code) {
		JSONObject ret = new JSONObject();
		ret.put("code", code);
		if (msg != null) {
			ret.put("err", msg);
		}
		return new ProtoMessage(ret);
	}

	public static ProtoMessage getSuccessResp() {
		return getResp(null, ResultCode.SUCCESS);
	}

	public static ProtoMessage getEmptyResp() {
		JSONArray ret = new JSONArray();
		return new ProtoMessage(ret);
	}

	public static ProtoMessage getErrorResp(String msg) {
		return getResp(msg, ResultCode.COMMON_ERR);
	}

	public static ProtoMessage getErrorResp(short id) {
		return getResp(null, ResultCode.COMMON_ERR);
	}

	public static ProtoMessage getErrorResp(int code) {
		return getResp(null, code);
	}

	public static ProtoMessage getErrorResp(int code, String msg) {
		return getResp(msg, code);
	}

	public JSONObject getData() {
		return this.data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	public <T> T getData(Class<T> t) {// 转换为对象传递
		return JSON.parseObject(JSON.toJSONString(data), t);
	}

	public <T> void setData(T t) {
		this.data = JSON.parseObject(JSON.toJSONString(t), JSONObject.class);
	}

	public Short getProtoid() {
		return protoid;
	}

	public void setProtoid(Short protoid) {
		this.protoid = protoid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}
}
