package com.hjc.herol.util.cache;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hjc.herol.core.GameInit;
import com.hjc.herol.manager.player.Player;
import com.hjc.herol.util.memcached.MemcachedCRUD;

public class MC {
	/**
	 * 控制哪些类进行memcached缓存。 被控制的类在进行创建时，需要注意调用MC的add和hibernate的insert。
	 */
	public static Set<Class<? extends MCSupport>> cachedClass = new HashSet<Class<? extends MCSupport>>();
	public static Set<String> cachedList = new HashSet<String>();
	static {
	}
	static {
	}

	public static <T> T get(Class<T> t, String id) {
		if (!cachedClass.contains(t)) {
			return null;
		}
		StringBuffer key = new StringBuffer();
		key.append(GameInit.serverId).append("#").append(t.getSimpleName())
				.append("#").append(id);
		// String c = GameInit.serverId + "#" + t.getSimpleName();
		// String key = c + "#" + id;
		Object o = MemcachedCRUD.getInstance().getObject(key.toString());
		return (T) o;
	}

	public static <T> List<T> getList(Class<T> t, String id, String where) {
		if (!cachedList.contains(t.getSimpleName())) {
			return null;
		}
		StringBuffer key = new StringBuffer();
		key.append(GameInit.serverId).append("#").append(id).append("#")
				.append(t.getSimpleName()).append("#").append(where);
		// String c = GameInit.serverId + "#" + id + "#" + t.getSimpleName();
		// String key = c + "#" + where;
		Object o = MemcachedCRUD.getInstance().getObject(key.toString());
		return (List<T>) o;
	}

	public static <T> String getListKeys(Class<T> t, String id) {
		if (!cachedList.contains(t.getSimpleName())) {
			return null;
		}
		StringBuffer key = new StringBuffer();
		key.append(GameInit.serverId).append("#").append(id).append("#")
				.append(t.getSimpleName());
		// String c = GameInit.serverId + "#" + id + "#" + t.getSimpleName();
		Object o = MemcachedCRUD.getInstance().getObject(key.toString());
		return (String) o;
	}

	public static Object getValue(String key) {
		Object o = MemcachedCRUD.getInstance().getObject(key);
		return o;
	}

	public static boolean add(Object t, String id) {
		if (!cachedClass.contains(t.getClass())) {
			return false;
		}
		StringBuffer key = new StringBuffer();
		key.append(GameInit.serverId).append("#")
				.append(t.getClass().getSimpleName()).append("#").append(id);
		// String c = GameInit.serverId + "#" + t.getClass().getSimpleName();
		// String key = c + "#" + id;
		return MemcachedCRUD.getInstance().add(key.toString(), t);
	}

	public static boolean addList(List list, String tName, String id,
			String where) {
		if (!cachedList.contains(tName)) {
			return false;
		}
		StringBuffer key = new StringBuffer();
		key.append(GameInit.serverId).append("#").append(id).append("#")
				.append(tName);
		String c = key.toString();
		// String key = c + "#" + where;
		key.append("#").append(where);
		Object tmp = MemcachedCRUD.getInstance().getObject(c);
		String keys = tmp == null ? "" : (String) tmp;
		if (keys.equals("")) {
			MemcachedCRUD.getInstance().add(c, key.toString());
		} else if (!keys.contains(key.toString())) {
			MemcachedCRUD.getInstance().update(c, keys + "," + key.toString());
		}
		return MemcachedCRUD.getInstance().add(key.toString(), list);
	}

	public static boolean addKeyValue(String key, Object value) {
		return MemcachedCRUD.getInstance().add(key, value);
	}

	public static void update(Object t, String id) {
		if (!cachedClass.contains(t.getClass())) {
			return;
		}
		StringBuffer key = new StringBuffer();
		key.append(GameInit.serverId).append("#")
				.append(t.getClass().getSimpleName()).append("#").append(id);
		// String c = GameInit.serverId + "#" + t.getClass().getSimpleName();
		// String key = c + "#" + id;
		MemcachedCRUD.getInstance().update(key.toString(), t);
	}

	public static void updateList(List list, String tName, String id,
			String where) {
		if (!cachedList.contains(tName)) {
			return;
		}
		StringBuffer key = new StringBuffer();
		key.append(GameInit.serverId).append("#").append(id).append("#")
				.append(tName);
		String c = key.toString();
		// String key = c + "#" + where;
		key.append("#").append(where);
		Object tmp = MemcachedCRUD.getInstance().getObject(c);
		String keys = tmp == null ? "" : (String) tmp;
		if (keys.equals("")) {
			MemcachedCRUD.getInstance().add(c, key.toString());
		} else if (!keys.contains(key)) {
			MemcachedCRUD.getInstance().update(c, keys + "," + key.toString());
		}
		MemcachedCRUD.getInstance().update(key.toString(), list);
	}

	/**
	 * 根据主键删除缓存
	 * 
	 * @param obj
	 *            删除对象
	 * @param id
	 *            主键id
	 */
	public static void delete(Class clazz, String id) {
		if (!cachedClass.contains(clazz)) {
			return;
		}
		StringBuffer key = new StringBuffer();
		key.append(GameInit.serverId).append("#").append(clazz.getSimpleName())
				.append("#").append(id);
		// String prefix = GameInit.serverId + "#" + clazz.getSimpleName();
		// String key = prefix + "#" + id;
		MemcachedCRUD.getInstance().deleteObject(key.toString());
	}

}
