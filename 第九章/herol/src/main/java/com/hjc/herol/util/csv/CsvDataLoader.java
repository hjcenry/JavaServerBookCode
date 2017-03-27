package com.hjc.herol.util.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import com.hjc.herol.core.GameInit;

public class CsvDataLoader {
	public static Logger logger = LoggerFactory.getLogger(CsvDataLoader.class);
	private String packageName;
	private String config; // 每一行就是一个配置文件名字
	public static int ActivityMaxValue;
	private static CsvDataLoader inst;

	public CsvDataLoader(String packageName, String config) {
		this.packageName = packageName;
		this.config = config;
	}

	public static CsvDataLoader getInstance(String packageName, String config) {
		if (inst == null) {
			inst = new CsvDataLoader(packageName, config);
		}
		return inst;
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

	private List<?> loadFile(String file, boolean exitWhenFail) {// 读文件
		try {
			String clzName = file.replaceAll(".csv", "");
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
			return loadFromStream(resourceAsStream, clzName);
		} catch (Exception e) {
			logger.error("载入文件出错：" + file);
			e.printStackTrace();
			System.exit(0);
		} finally {
		}
		return Collections.EMPTY_LIST;
	}

	public List<?> loadFromStream(InputStream resourceAsStream, String clzName)
			throws DocumentException, InstantiationException,
			IllegalAccessException, IOException {// 读csv文件
		CsvParser csvParser = new CsvParser(resourceAsStream);
		List<String> nodes = csvParser.getListWithNoHeader();
		// get clazz
		String className = this.packageName + clzName;
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
			for (String node : nodes) {
				if (node != null) {
					instance = classObject.newInstance();
					boolean ok = false;
					// Element row = (Element) node;
					String[] values = node.split(",");// csv文件以英文逗号分割值
					for (int i = 0; i < fieldList.size(); i++) {
						Field field = fieldList.get(i);
						fieldName = field.getName();
						fieldValue = values[i];
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
					if (ok) {
						instances.add(instance);
					}
				}
			}
			return instances;
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			logger.error("未找到类" + className);
			return null;
		} finally {
			if (resourceAsStream != null) {
				resourceAsStream.close();
			}
		}
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
		CsvDataLoader dl = new CsvDataLoader("com.hjc.herol.template.",
				"/dataConfig.xml");
		dl.load();
	}
}
