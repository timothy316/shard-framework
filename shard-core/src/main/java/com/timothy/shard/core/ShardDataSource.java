package com.timothy.shard.core;

import com.timothy.shard.core.sharding.DataSourceItem;
import com.timothy.shard.core.sharding.Shard;
import com.timothy.shard.core.sharding.ShardConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 分库分表数据源
 *
 * @author zhengxun
 * @date 2018-05-18
 */
public class ShardDataSource extends AbstractDataSource<Shard> {
    /**
     * 实际的数据源列表
     */
    private List<DataSourceItem> actualDataSourceItems;
    /**
     * 拆分基本配置
     */
    private ShardConfig shardConfig;

    public ShardDataSource(List<DataSourceItem> actualDataSourceItems, ShardConfig shardConfig) {
        this.actualDataSourceItems = actualDataSourceItems;
        this.shardConfig = shardConfig;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new ShardConnection(this, shardConfig);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return new ShardConnection(this, shardConfig, username, password);
    }

    /**
     * 根据shard获取真实数据源
     *
     * @param shard
     * @return
     */
    @Override
    protected DataSource createActualDataSource(Shard shard) {
        DataSource actualDataSource = null;
        for (DataSourceItem actualDataSourceItem : actualDataSourceItems) {
            if (actualDataSourceItem.getDataSourceName().equals(shard.getDataSourceName())) {
                actualDataSource = actualDataSourceItem.getActualDataSource();
                break;
            }
        }
        return actualDataSource;
    }
}
