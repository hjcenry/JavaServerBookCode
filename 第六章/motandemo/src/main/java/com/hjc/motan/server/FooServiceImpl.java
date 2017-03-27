package com.hjc.motan.server;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hjc.motan.DemoBean;

public class FooServiceImpl implements FooService {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:motan_server.xml");
		System.out.println("server start...");
	}

	public String hello(String name) {
		System.out.println("invoked rpc service " + name);
		return "hello " + name;
	}

	public int helloInt(int number1) {
		System.out.println("invoked rpc service " + number1);
		return number1;
	}

	public double helloDouble(double number2) {
		System.out.println("invoked rpc service " + number2);
		return number2;
	}

	public List<String> helloList(List<String> list) {
		System.out.print("invoked rpc service ");
		for (String string : list) {
			System.out.print(string + ",");
		}
		System.out.println();
		return list;
	}

	public Map<String, List<String>> helloMap(Map<String, List<String>> map) {
		System.out.print("invoked rpc service ");
		for (String key : map.keySet()) {
			System.out.print(key + ":[");
			for (String list : map.get(key)) {
				System.out.print(list + ",");
			}
			System.out.print("],");
		}
		System.out.println();
		return map;
	}

	public DemoBean helloJavabean(DemoBean bean) {
		System.out.print("invoked rpc service " + bean);
		System.out.print("," + bean.getId());
		System.out.print("," + bean.getName());
		System.out.print("," + bean.getScore());
		System.out.println();
		return bean;
	}

}
