package com.timothy.shard.core;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 虚拟数据源
 * @author zhengxun
 * @date 2018-05-18
 */
public interface VirtualDataSource<T> extends DataSource {

    Connection createActualConnection(T shard) throws SQLException;

    Connection createActualConnection(T shard, String username, String password) throws SQLException;
}
