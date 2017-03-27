package com.hjc.herol.util.csv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 添加一个数据表需要做以下几步 
 * 1.在包com.hjc.herol.template下创建对应的模板类，类名与数据文件一致 
 * 2.在src/main/resources/csv/中添加模板数据文件
 * 3.在src/main/resources/dataConfig.xml加入刚才的模板数据文件
 * 
 * @author 何金成
 * 
 */
public class TempletService {
	public static Logger log = LoggerFactory.getLogger(TempletService.class);
	public static TempletService templetService = new TempletService();
	/**
	 * key:实体名 value:该实体下的所有模板数据
	 */
	public static Map<String, List<?>> templetMap = new HashMap<String, List<?>>();

	public TempletService() {

	}

	public static TempletService getInstance() {
		return templetService;
	}

	/**
	 * 获取该实体类下所有模板数据
	 * 
	 * @param beanName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List listAll(String beanName) {
		return templetMap.get(beanName);
	}

	/**
	 * @Title: registerObject 
	 * @Description: 注册对象到对应类的List中
	 * @param o
	 * @param dataMap
	 * @return void
	 * @throws
	 */
	public void registerObject(Object o, Map<String, List<?>> dataMap) {
		add(o.getClass().getSimpleName(), o, dataMap);
	}



	@SuppressWarnings("unchecked")
	private void add(String key, Object data, Map<String, List<?>> dataMap) {
		List list = dataMap.get(key);
		if (list == null) {
			list = new ArrayList();
			dataMap.put(key, list);
		}
		list.add(data);
	}
	
	public void afterLoad() {
		// 加载后处理
		// List tests = TempletService.listAll(Test.class.getSimpleName());
		// for (Object object : tests) {
		// Test test = (Test)object;
		// System.out.print(test.getEquipLv());
		// System.out.print(","+test.getLv1());
		// System.out.print(","+test.getLv2());
		// System.out.print(","+test.getLv3());
		// System.out.print(","+test.getLv4());
		// System.out.print(","+test.getLv5());
		// System.out.print(","+test.getLv6());
		// System.out.print(","+test.getLv7());
		// System.out.print(","+test.getLv8());
		// System.out.print(","+test.getLv9());
		// System.out.println(","+test.getLv10());
		// }
	}

	public void loadCanShu() {
		// 加载全局参数xml配置
	}
}
