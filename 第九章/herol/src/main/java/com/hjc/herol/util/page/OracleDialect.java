package com.hjc.herol.util.page;

/**
 * <strong>Title : OracleDialect </strong>. <br>
 * <strong>Description : Oracle数据库方言，目前实现了物理分页.</strong> <br>
 * <br>
 */
public class OracleDialect implements Dialect {

	public String getPageSql(String sql, long offset, long pageSize) {
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		pagingSelect.append(" select * from ( select row_.*, rownum rownum_ from ( ")
					.append(sql)		
					.append(" ) row_ ) where rownum_ > ")
					.append(offset)
					.append(" and rownum_ <= ")
					.append(offset + pageSize);
		return pagingSelect.toString();
	}
}