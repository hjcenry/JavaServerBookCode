package com.hjc.herol.util.page;

/**
 * <strong>Title : Mysql5Dialect </strong>. <br>
 * <strong>Description : Mysql数据库方言，目前实现了物理分页.</strong> <br>
 * <br>
 */
public class Mysql5Dialect implements Dialect {

	public String getPageSql(String sql, long offset, long pageSize) {
		return sql + " limit " + offset + "," + pageSize;
	}

}
