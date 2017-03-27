package com.hjc.motan.server;

import java.util.List;
import java.util.Map;

import com.hjc.motan.DemoBean;

public interface FooService {
	public String hello(String name);

	public int helloInt(int number1);

	public double helloDouble(double number2);

	public List<String> helloList(List<String> list);

	public Map<String, List<String>> helloMap(Map<String, List<String>> map);

	public DemoBean helloJavabean(DemoBean bean);
}
