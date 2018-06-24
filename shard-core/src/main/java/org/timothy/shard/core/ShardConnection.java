package org.timothy.shard.core;

import org.timothy.shard.core.sharding.ShardRouterStrategy;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 虚拟Connection
 *
 * @author zhengxun
 * @date 2018-05-18
 */
public class ShardConnection extends AbstractConnection {

    private ShardRouterStrategy shardRouterStrategy;

    public ShardConnection(VirtualDataSource virtualDataSource, ShardRouterStrategy shardRouterStrategy) {
        super(virtualDataSource);
        this.shardRouterStrategy = shardRouterStrategy;
    }

    public ShardConnection(VirtualDataSource virtualDataSource, ShardRouterStrategy shardRouterStrategy, String username, String password) {
        super(virtualDataSource, username, password);
        this.shardRouterStrategy = shardRouterStrategy;
    }

    @Override
    public Statement createStatement() throws SQLException {
        return new ShardStatement(this.shardRouterStrategy,this, null);
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return new ShardStatement(this.shardRouterStrategy, this, sql);
    }
}
