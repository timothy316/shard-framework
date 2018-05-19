package org.timothy.shard.core;

import org.timothy.shard.core.sharding.ShardAndSql;
import org.timothy.shard.core.sharding.Sql;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * 虚拟statement
 * @author zhengxun
 * @date 2018-05-18
 */
public interface VirtualStatement<T> extends Statement {

    ShardAndSql<T> createShardAndSql(Sql sql) throws SQLException;
}
