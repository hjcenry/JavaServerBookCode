package com.hjc.herol.util.hibernate;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.hjc.herol.task.ExecutorPool;
import com.hjc.herol.util.cache.MC;
import com.hjc.herol.util.cache.MCSupport;

public class HibernateUtil {
	public static boolean showMCHitLog = false;
	public static Logger log = LoggerFactory.getLogger(HibernateUtil.class);
	public static Map<Class<?>, String> beanKeyMap = new HashMap<Class<?>, String>();
	public static long synDelayT = 0;// 延迟同步周期
	private static SessionFactory sessionFactory;

	public static void init() {
		sessionFactory = buildSessionFactory();
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Throwable insert(Object o, long id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {
			session.save(o);
			session.getTransaction().commit();
			if (MC.cachedList.contains(o.getClass().getSimpleName())) {
				synchronizeDB2MemAsy(o.getClass(), String.valueOf(id));// 同步DB到Mem
			}
		} catch (Throwable e) {
			log.error("0要insert的数据{}", o == null ? "null" : JSONObject
					.fromObject(o).toString());
			log.error("0保存出错", e);
			session.getTransaction().rollback();
			return e;
		}
		return null;
	}

	/**
	 * FIXME 不要这样返回异常，没人会关系返回的异常。
	 * 
	 * @param o
	 * @return
	 */
	public static Throwable save(Object o, long id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction t = session.beginTransaction();
		boolean mcOk = false;
		try {
			if (o instanceof MCSupport) {
				MCSupport s = (MCSupport) o;// 需要对控制了的对象在第一次存库时调用MC.add
				MC.update(o, String.valueOf(s.getIdentifier()));// MC中控制了哪些类存缓存。
				mcOk = true;
				// session.update(o);
				session.saveOrUpdate(o);
			} else {
				session.saveOrUpdate(o);
			}
			t.commit();
			if (o instanceof MCSupport) {
				if (MC.cachedList.contains(o.getClass().getSimpleName())) {
					synchronizeDB2MemAsy(o.getClass(), String.valueOf(id));// 同步DB到Mem
				}
			}
		} catch (Throwable e) {
			log.error("1要save的数据{},{}", o, o == null ? "null" : JSONObject
					.fromObject(o).toString());
			if (mcOk) {
				log.error("MC保存成功后报错，可能是数据库条目丢失。");
			}
			log.error("1保存出错", e);
			t.rollback();
			return e;
		}
		return null;
	}

	public static Throwable update(Object o, String id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction t = session.beginTransaction();
		try {
			if (o instanceof MCSupport) {
				MCSupport s = (MCSupport) o;// 需要对控制了的对象在第一次存库时调用MC.add
				MC.update(o, String.valueOf(s.getIdentifier()));// MC中控制了哪些类存缓存。
				session.update(o);
			} else {
				session.update(o);
			}
			t.commit();
			if (o instanceof MCSupport) {
				if (MC.cachedList.contains(o.getClass().getSimpleName())) {
					synchronizeDB2MemAsy(o.getClass(), String.valueOf(id));// 同步DB到Mem
				}
			}
		} catch (Throwable e) {
			log.error("1要update的数据{},{}", o, o == null ? "null" : JSONObject
					.fromObject(o).toString());
			log.error("1保存出错", e);
			t.rollback();
			return e;
		}
		return null;
	}

	public static String getKeyField(Class<?> c) {
		synchronized (beanKeyMap) {
			String key = beanKeyMap.get(c);
			if (key != null) {
				return key;
			}
			Field[] fs = c.getDeclaredFields();
			for (Field f : fs) {
				if (f.isAnnotationPresent(javax.persistence.Id.class)) {
					key = f.getName();
					beanKeyMap.put(c, key);
					break;
				}
			}
			return key;
		}
	}

	public static <T> T find(Class<T> t, long id) {
		String keyField = getKeyField(t);
		if (keyField == null) {
			throw new RuntimeException("类型" + t + "没有标注主键");
		}
		if (!MC.cachedClass.contains(t)) {
			return find(t, "where " + keyField + "=" + id, false);
		}
		T ret = MC.get(t, String.valueOf(id));
		if (ret == null) {
			if (showMCHitLog)
				log.info("MC未命中{}#{}", t.getSimpleName(), id);
			ret = find(t, "where " + keyField + "=" + id, false);
			if (ret != null) {
				if (showMCHitLog)
					log.info("DB命中{}#{}", t.getSimpleName(), id);
				MC.add(ret, String.valueOf(id));
			} else {
				if (showMCHitLog)
					log.info("DB未命中{}#{}", t.getSimpleName(), id);
			}
		} else {
			if (showMCHitLog)
				log.info("MC命中{}#{}", t.getSimpleName(), id);
		}
		return ret;
	}

	public static <T> T find(Class<T> t, String where) {
		return find(t, where, true);
	}

	public static <T> T find(Class<T> t, String where, boolean checkMCControl) {
		// if (checkMCControl && MC.cachedClass.contains(t)) {
		// // 请使用static <T> T find(Class<T> t,long id)
		// throw new BaseException("由MC控制的类不能直接查询DB:" + t);
		// }
		Session session = sessionFactory.getCurrentSession();
		Transaction tr = session.beginTransaction();
		T ret = null;
		try {
			// FIXME 使用 session的get方法代替。
			String hql = "from " + t.getSimpleName() + " " + where;
			Query query = session.createQuery(hql);

			ret = (T) query.uniqueResult();
			tr.commit();
		} catch (Exception e) {
			tr.rollback();
			log.error("list fail for {} {}", t, where);
			log.error("list fail", e);
		}
		return ret;
	}

	/**
	 * 通过指定key值来查询对应的对象
	 * 
	 * @param t
	 * @param name
	 * @param where
	 * @return
	 */
	public static <T> T findByName(Class<? extends MCSupport> t, String name,
			String where) {
		Class<? extends MCSupport> targetClz = t;// .getClass();
		String key = targetClz.getSimpleName() + ":" + name;
		Object id = MC.getValue(key);
		T ret = null;
		if (id != null) {
			log.info("id find in cache");
			ret = (T) find(targetClz, Long.parseLong((String) id));
			return ret;
		} else {
			ret = (T) find(targetClz, where, false);
		}
		if (ret == null) {
			log.info("no record {}, {}", key, where);
		} else {
			MCSupport mc = (MCSupport) ret;
			long mcId = mc.getIdentifier();
			log.info("found id from DB {}#{}", targetClz.getSimpleName(), mcId);
			MC.add(key, String.valueOf(mcId));
			ret = (T) find(targetClz, mcId);
		}
		return ret;

	}

	public static <T> List<T> list(Class<T> t, long id, String where) {
		String keyField = getKeyField(t);
		if (keyField == null) {
			throw new RuntimeException("类型" + t + "没有标注主键");
		}
		if (!MC.cachedList.contains(t.getSimpleName())) {
			return list(t, where, false);
		}
		List<T> ret = MC.getList(t, String.valueOf(id), where);
		if (ret == null) {
			if (showMCHitLog)
				log.info("MC未命中{}#{}", t.getSimpleName(), where);
			ret = list(t, where, false);
			if (ret != null) {
				if (showMCHitLog)
					log.info("DB命中{}#{}", t.getSimpleName(), where);
				MC.addList(ret, t.getSimpleName(), String.valueOf(id), where);
			} else {
				if (showMCHitLog)
					log.info("DB未命中{}#{}", t.getSimpleName(), where);
			}
		} else {
			if (showMCHitLog)
				log.info("MC命中{}#{}", t.getSimpleName(), where);
		}
		return ret;
	}

	/**
	 * @param t
	 * @param where
	 *            例子： where uid>100
	 * @return
	 */
	public static <T> List<T> list(Class<T> t, String where,
			boolean checkMCControl) {
		// if (checkMCControl && MC.cachedList.contains(t)) {
		// // 请使用static <T> T find(Class<T> t,long id)
		// throw new BaseException("由MC控制的类不能直接查询DB:" + t);
		// }
		Session session = sessionFactory.getCurrentSession();
		Transaction tr = session.beginTransaction();
		List<T> list = Collections.EMPTY_LIST;
		try {
			String hql = "from " + t.getSimpleName() + " " + where;
			Query query = session.createQuery(hql);
			list = query.list();
			tr.commit();
		} catch (Exception e) {
			tr.rollback();
			log.error("list fail for {} {}", t, where);
			log.error("list fail", e);
		}
		return list;
	}

	public static <T> Integer listCount(Class<T> t, String where) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tr = session.beginTransaction();
		int count = 0;
		try {
			String hql = "select count(*) from " + t.getSimpleName() + " "
					+ where;
			Query query = session.createQuery(hql);
			count = ((Long) query.uniqueResult()).intValue();
			tr.commit();
		} catch (Exception e) {
			tr.rollback();
			log.error("list fail for {} {}", t, where);
			log.error("list fail", e);
		}
		return count;
	}

	public static SessionFactory buildSessionFactory() {
		log.info("开始构建hibernate");
		String path = "classpath*:spring-conf/applicationContext.xml";
		ApplicationContext ac = new FileSystemXmlApplicationContext(path);
		sessionFactory = (SessionFactory) ac.getBean("sessionFactory");
		log.info("结束构建hibernate");
		return sessionFactory;
	}

	public static Throwable delete(Object o, long id) {
		if (o == null) {
			return null;
		}
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {
			session.delete(o);
			session.getTransaction().commit();
			if (o instanceof MCSupport) {
				MCSupport s = (MCSupport) o;// 需要对控制了的对象在第一次存库时调用MC.add
				MC.delete(o.getClass(), String.valueOf(s.getIdentifier()));// MC中控制了哪些类存缓存。
				if (MC.cachedList.contains(o.getClass().getSimpleName())) {
					synchronizeDB2MemAsy(o.getClass(), String.valueOf(id));// 同步DB到Mem
				}
			}
		} catch (Throwable e) {
			log.error("要删除的数据{}", o);
			log.error("出错", e);
			session.getTransaction().rollback();
			return e;
		}
		return null;
	}

	/**
	 * 注意这个方法会返回大于等于1的值。数据库无记录也会返回1，而不是null
	 * 
	 * @param t
	 * @return
	 */
	public static <T> Long getTableIDMax(Class<T> t) {
		Long id = null;
		Session session = sessionFactory.getCurrentSession();
		Transaction tr = session.beginTransaction();
		String hql = "select max(id) from " + t.getSimpleName();
		try {
			Query query = session.createQuery(hql);
			Object uniqueResult = query.uniqueResult();
			if (uniqueResult == null) {
				id = 1L;
			} else {
				id = Long.parseLong(uniqueResult + "");
				id = Math.max(1L, id);
			}
			tr.commit();
		} catch (Exception e) {
			tr.rollback();
			log.error("query max id fail for {} {}", t, hql);
			log.error("query max id fail", e);
		}
		return id;
	}

	public static <T> int getColumnValueMax(Class<T> t, String column) {
		Integer id = null;
		Session session = sessionFactory.getCurrentSession();
		Transaction tr = session.beginTransaction();
		String hql = "select max(" + column + ") from " + t.getSimpleName();
		try {
			Query query = session.createQuery(hql);
			Object uniqueResult = query.uniqueResult();
			if (uniqueResult == null) {
				id = 1;
			} else {
				id = Integer.parseInt(uniqueResult + "");
				id = Math.max(1, id);
			}
			tr.commit();
		} catch (Exception e) {
			tr.rollback();
			log.error("query column value max fail for {} {}", t, hql);
			log.error("query column value max fail", e);
		}
		return id;
	}

	public static List<Map<String, Object>> querySql(String hql) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tr = session.beginTransaction();
		List list = Collections.emptyList();
		try {
			SQLQuery query = session.createSQLQuery(hql);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
			tr.commit();
		} catch (Exception e) {
			tr.rollback();
			log.error("query  failed {}", hql);
			log.error("query count(type) fail", e);
		}
		return list;
	}

	public static int getCount(String hql) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tr = session.beginTransaction();
		int count = -1;
		try {
			Query query = session.createQuery(hql);
			Object uniqueResult = query.uniqueResult();
			if (uniqueResult != null) {
				count = Integer.parseInt(uniqueResult + "");
			}
			tr.commit();
		} catch (Exception e) {
			tr.rollback();
			log.error("query  failed", hql);
			log.error("query count(type) fail", e);
		}
		return count;
	}

	public static <T> void synchronizeDB2MemAsy(final Class<T> t,
			final String id) {
		ExecutorPool.execute(new Runnable() {
			@Override
			public void run() {
				String keys = MC.getListKeys(t, id);
				if (keys != null && keys.length() > 0) {// 遍历所有的where缓存
					String[] wheres = keys.split(",");
					for (String where : wheres) {
						where = where.split("#")[3];
						List<T> list = list(t, where, false);
						MC.updateList(list, t.getSimpleName(), id, where);
						if (showMCHitLog) {
							log.info("DB 同步 MC 成功 t:{},where:{}",
									t.getSimpleName(), where);
						}
					}
				}
			}
		});
	}

	public static <T> void synchronizeDB2MemSyn(final Class<T> t,
			final String id) {
		String keys = MC.getListKeys(t, id);
		if (keys != null && keys.length() > 0) {// 遍历所有的where缓存
			String[] wheres = keys.split(",");
			for (String where : wheres) {
				where = where.split("#")[3];
				List<T> list = list(t, where, false);
				MC.updateList(list, t.getSimpleName(), id, where);
				if (showMCHitLog) {
					log.info("DB 同步 MC 成功 t:{},where:{}", t.getSimpleName(),
							where);
				}
			}
		}
	}

}
