package com.hjc.herol.util.page;

/**
 * <strong>Title : Dialect </strong>. <br>
 * <strong>Description : 数据库方言接口.</strong> <br>
 * <br>
 */
public interface Dialect {     
    
    public static enum Type{     
        MYSQL,
        ORACLE,
        DB2
    }
    
    /**
     * 获取分页SQL.
     * @param sql		原始查询SQL
     * @param offset	开始记录索引（从0开始计算）
     * @param pageSize	每页记录大小
     * @return          返回数据库相关的分页SQL语句
     */
    public abstract String getPageSql(String sql, long offset, long pageSize); 
}
