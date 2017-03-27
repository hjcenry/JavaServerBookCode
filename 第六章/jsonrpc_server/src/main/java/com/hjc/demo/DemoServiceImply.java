package com.hjc.demo;

public class DemoServiceImply {

	public DemoBean getDemo2(DemoBean bean, String msg) {
		DemoBean bean1 = new DemoBean();
		System.out.println(bean.getCode());
		System.out.println(bean.getMsg());
		System.out.println(msg);
		bean1 = bean;
		return bean1;
	}

	public DemoBean getDemo(String code, String msg) {
		DemoBean bean1 = new DemoBean();
		bean1.setCode(Integer.parseInt(code));
		bean1.setMsg(msg);
		return bean1;
	}

	public Integer getInt(Integer code) {
		return code;
	}

	public String getString(String msg) {
		return msg;
	}

	public void doSomething() {
		System.out.println("do something");
	}

}
