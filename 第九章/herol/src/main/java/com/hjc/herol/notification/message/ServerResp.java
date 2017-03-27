package com.hjc.herol.notification.message;

public class ServerResp {
	private int code;// 100-成功
	private String result;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
