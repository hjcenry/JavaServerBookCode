package com.hjc.herolpvp.util.xml;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjc.herolpvp.core.GameInit;
import com.hjc.herolpvp.util.csv.TempletService;

/**
 * 1 所有的template类都必须在一个包中 2 通过读取xml 遍历所有的Element，通过Element.name和 package 得到具体的
 * className; 3 通过Element的 attr 获取方法，并且根据parameterType 强制转换类型
 * 
 * @author Administrator
 * 
 */
public class XmlDataLoader {
	private String packageName;
	private String config; // 每一行就是一个配置文件名字
	private static Logger logger = LoggerFactory.getLogger(XmlDataLoader.class);
	public static int ActivityMaxValue;

	public XmlDataLoader(String packageName, String config) {
		this.packageName = packageName;
		this.config = config;
	}

	/**
	 * 调用load方法加载所有的配置文件
	 */
	public void load() {
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new InputStreamReader(this.getClass()
					.getResourceAsStream(this.config)));
			List<?> nodes = doc.selectNodes("/config/file");
			Map<String, List<?>> dataMap = new HashMap<String, List<?>>();
			List<String> files = new LinkedList<String>();
			for (Object n : nodes) {
				Element t = (Element) n;
				String f = t.attributeValue("name");
				List<?> dataList = this.loadFile(f, true);
				for (Object o : dataList) {
					TempletService.getInstance().registerObject(o, dataMap);
				}
				files.add(f);
			}
			logger.info("读取配置完毕，准备afterLoad");
			TempletService.templetMap = dataMap;
			TempletService.getInstance().afterLoad();
			logger.info("afterLoad 完毕");
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {

		}
	}

	private List<?> loadFile(String file, boolean exitWhenFail) {
		try {
			file = GameInit.confFileBasePath + file;
			logger.info("load file: {}", file);
			InputStream resourceAsStream = this.getClass().getResourceAsStream(
					file);
			if (resourceAsStream == null) {
				logger.error("文件不存在:" + file);
				if (exitWhenFail) {
					System.exit(0);
				}
				return null;
			}
			return loadFromStream(resourceAsStream);
		} catch (Exception e) {
			logger.error("载入文件出错：" + file);
			e.printStackTrace();
			System.exit(0);
		} finally {
		}
		return Collections.EMPTY_LIST;
	}

	public List<?> loadFromStream(InputStream resourceAsStream)
			throws UnsupportedEncodingException, DocumentException,
			InstantiationException, IllegalAccessException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new InputStreamReader(resourceAsStream,
				"utf-8"));
		Element dataSet = (Element) doc.selectNodes("/dataset").get(0);
		List<?> nodes = dataSet.elements();
		// get clazz
		String className = this.packageName
				+ ((Element) nodes.get(0)).getName();
		try {
			Class<?> classObject = Class.forName(className);
			if (classObject == null) {
				logger.error("未找到类" + className);
				return null;
			}
			// Get all the declared fields
			Field[] fields = classObject.getDeclaredFields();
			LinkedList<Field> fieldList = new LinkedList<Field>();
			int length = fields.length;
			for (int i = -1; ++i < length;) {
				boolean isStaticField = Modifier.isStatic(fields[i]
						.getModifiers());
				if (isStaticField)
					continue;
				boolean isTransientField = Modifier.isTransient(fields[i]
						.getModifiers());
				if (isTransientField)
					continue;
				fieldList.add(fields[i]);
			}
			// Get all the declared fields of supper class
			Class<?> tmp = classObject;
			while ((tmp = tmp.getSuperclass()) != Object.class) {
				System.out.print("the extends class is" + tmp.getName());
				fields = tmp.getDeclaredFields();
				length = fields.length;
				if (length == 0)
					continue;
				for (int i = -1; ++i < length;) {
					boolean isStaticField = Modifier.isStatic(fields[i]
							.getModifiers());
					if (isStaticField)
						continue;
					boolean isTransientField = Modifier.isTransient(fields[i]
							.getModifiers());
					if (isTransientField)
						continue;
					fieldList.add(fields[i]);
				}
			}
			// The truly need to return object
			List<Object> instances = new ArrayList<Object>(nodes.size());
			Object instance = null;
			String fieldName = null;
			String fieldValue = null;
			for (Object node : nodes) {
				if (node != null) {
					instance = classObject.newInstance();
					boolean ok = false;
					Element row = (Element) node;
					for (Field field : fieldList) {
						fieldName = field.getName();
						fieldValue = row.attributeValue(fieldName);
						if (fieldValue == null)
							continue;
						try {
							this.setField(instance, field, fieldValue);
							ok = true;
						} catch (Exception e) {
							logger.error("类名称是" + className + "的属性" + fieldName
									+ "没有被成功赋予静态数据");
							continue;
						}
					}
					if (ok)
						instances.add(instance);
				}
			}
			return instances;
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			logger.error("未找到类" + className);
			return null;
		}
	}

	public void reload() {
	}

	/**
	 * 
	 * @Title: setUnknowField
	 * @Description:
	 * @param ob
	 * @param f
	 * @param v
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private void setField(Object obj, Field f, String v)
			throws IllegalArgumentException, IllegalAccessException {
		f.setAccessible(true);
		if (f.getType() == int.class) {
			f.setInt(obj, Integer.parseInt(v));
		} else if (f.getType() == short.class) {
			f.setShort(obj, Short.parseShort(v));
		} else if (f.getType() == byte.class) {
			f.setByte(obj, Byte.parseByte(v));
		} else if (f.getType() == long.class) {
			f.setLong(obj, Long.parseLong(v));
		} else if (f.getType() == double.class) {
			f.setDouble(obj, Double.parseDouble(v));
		} else if (f.getType() == float.class) {
			f.setFloat(obj, Float.parseFloat(v));
		} else if (f.getType() == Timestamp.class) {
			f.set(obj, Timestamp.valueOf(v));
		} else {
			f.set(obj, f.getType().cast(v));
		}
	}

	/**
	 * Test Code
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		XmlDataLoader dl = new XmlDataLoader("com.hjc.herolpvp.template.",
				"/dataConfig.xml");
		dl.load();
	}

}
