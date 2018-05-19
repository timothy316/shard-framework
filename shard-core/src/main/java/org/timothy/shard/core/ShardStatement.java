package org.timothy.shard.core;

import org.timothy.shard.core.sharding.ShardAndSql;
import org.timothy.shard.core.sharding.Sql;

import java.sql.SQLException;

/**
 * @author zhengxun
 * @date 2018-05-19
 */
public class ShardStatement extends AbstractPreparedStatement {


    public ShardStatement(VirtualConnection virtualConnection, String sql) {
        super(virtualConnection, sql);
    }

    @Override
    public ShardAndSql createShardAndSql(Sql sql) throws SQLException {
        return null;
    }
}
