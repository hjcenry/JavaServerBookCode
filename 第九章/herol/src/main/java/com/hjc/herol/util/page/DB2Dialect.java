package com.hjc.herol.util.page;

/**
 * <strong>Title : DB2Dialect </strong>. <br>
 * <strong>Description : DB2数据方言，目前实现了物理分页.</strong> <br>
 */
public class DB2Dialect implements Dialect {

	public String getPageSql(String sql, long offset, long limit) {
		sql = sql.trim();
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		pagingSelect.append(" SELECT * FROM (SELECT PAGE_B.*, ROWNUMBER() OVER() AS RN FROM ( ");
		pagingSelect.append(sql);
		pagingSelect.append(" ) AS PAGE_B )AS PAGE_A WHERE PAGE_A.RN BETWEEN ").append(offset).append(" AND ")
				.append(offset + limit - 1);
		return pagingSelect.toString();
	}

}