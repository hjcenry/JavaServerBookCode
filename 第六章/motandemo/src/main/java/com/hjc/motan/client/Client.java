package com.hjc.motan.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hjc.motan.DemoBean;
import com.hjc.motan.server.FooService;

public class Client {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"classpath:motan_client.xml");
		// 获取到service
		FooService service = (FooService) ctx.getBean("remoteService");
		// rpc调用
		/** String **/
		String ret1 = service.hello("motan");
		System.out.println(ret1);
		/** int **/
		int ret2 = service.helloInt(110);
		System.out.println(ret2);
		/** double **/
		double ret3 = service.helloDouble(11.2);
		System.out.println(ret3);
		/** list **/
		List<String> list = new ArrayList<String>();
		list.add("hello");
		list.add("motan");
		List<String> ret4 = service.helloList(list);
		for (String string : ret4) {
			System.out.print(string + ",");
		}
		System.out.println();
		/** map **/
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("key1", Arrays.asList(new String[] { "val1","val2" }));
		map.put("key2", Arrays.asList(new String[] { "val1","val2","val3" }));
		map.put("key3", Arrays.asList(new String[] { "val1","val2","val3","val4" }));
		Map<String, List<String>> ret5 = service.helloMap(map);
		for (String key : ret5.keySet()) {
			System.out.print(key + ":[");
			for (String tmp : map.get(key)) {
				System.out.print(tmp + ",");
			}
			System.out.print("],");
		}
		System.out.println();
		/** javabean **/
		DemoBean bean = new DemoBean();
		bean.setId(1001l);
		bean.setName("motan bean");
		bean.setScore(99.998);
		DemoBean ret6 = service.helloJavabean(bean);
		System.out.print(ret6.getId());
		System.out.print("," + ret6.getName());
		System.out.print("," + ret6.getScore());
		System.out.println();
	}

}
