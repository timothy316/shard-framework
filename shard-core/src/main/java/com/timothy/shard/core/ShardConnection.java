package com.timothy.shard.core;

import com.timothy.shard.core.sharding.ShardConfig;

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


    /**
     * 拆分基础配置信息
     */
    private ShardConfig shardConfig;

    public ShardConnection(VirtualDataSource virtualDataSource, ShardConfig shardConfig) {
        super(virtualDataSource);
        this.shardConfig = shardConfig;
    }

    public ShardConnection(VirtualDataSource virtualDataSource, ShardConfig shardConfig, String username, String password) {
        super(virtualDataSource, username, password);
        this.shardConfig = shardConfig;
    }

    @Override
    public Statement createStatement() throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return null;
    }
}
