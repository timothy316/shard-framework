package org.timothy.shard.core;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 虚拟Connection
 * @author zhengxun
 * @date 2018-05-18
 */
public interface VirtualConnection<T> extends Connection {

    /**
     * 创建真实statement
     * @param shard
     * @return
     */
    Statement createActualStatement(T shard) throws SQLException;

    /**
     * 创建真实preparedStatement
     * @param shard
     * @param sql
     * @return
     */
    PreparedStatement createActualPreparedStatement(T shard, String sql) throws SQLException;

    /**
     * 获取数据源
     * @return
     */
    DataSource getDataSource();
}
