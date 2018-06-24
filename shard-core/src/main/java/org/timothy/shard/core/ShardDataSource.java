package org.timothy.shard.core;

import org.timothy.shard.core.sharding.DataSourceItem;
import org.timothy.shard.core.sharding.Shard;
import org.timothy.shard.core.sharding.ShardRouterStrategy;
import org.timothy.shard.core.sql.SqlParseAndBuilderFactory;

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
     * 默认LRUCache缓存大小
     */
    private final int DEFAULT_SQL_CACHE_SIZE = 1 << 16;

    private boolean alterTableUseShardId = true;

    private SqlParseAndBuilderFactory sqlParseAndBuilderFactory;
    /**
     * 实际的数据源列表
     */
    private List<DataSourceItem> actualDataSourceItems;


    private ShardRouterStrategy shardRouterStrategy;

    public ShardDataSource(List<DataSourceItem> actualDataSourceItems, ShardRouterStrategy shardRouterStrategy) {
        this(actualDataSourceItems, shardRouterStrategy, -1);
    }

    public ShardDataSource(List<DataSourceItem> actualDataSourceItems, ShardRouterStrategy shardRouterStrategy, int sqlCacheSize) {
        this.actualDataSourceItems = actualDataSourceItems;
        this.shardRouterStrategy = shardRouterStrategy;
        if (sqlCacheSize < 1) {
            sqlCacheSize = DEFAULT_SQL_CACHE_SIZE;
        }
        sqlParseAndBuilderFactory = new SqlParseAndBuilderFactory(sqlCacheSize);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new ShardConnection(this, this.shardRouterStrategy);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return new ShardConnection(this, this.shardRouterStrategy, username, password);
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

    public SqlParseAndBuilderFactory getSqlParseAndBuilderFactory() {
        return sqlParseAndBuilderFactory;
    }

    public boolean isAlterTableUseShardId() {
        return alterTableUseShardId;
    }

    public void setAlterTableUseShardId(boolean alterTableUseShardId) {
        this.alterTableUseShardId = alterTableUseShardId;
    }
}
