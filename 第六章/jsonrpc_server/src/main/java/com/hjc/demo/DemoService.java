package com.hjc.demo;

public interface DemoService {

	public DemoBean getDemo(String code, String msg);

	public Integer getInt(Integer code);

	public String getString(String msg);

	public void doSomething();
}
