package org.timothy.shard.core.sharding.router;

import org.timothy.shard.core.sharding.RoutingValue;
import org.timothy.shard.core.sharding.Shard;
import org.timothy.shard.core.sharding.ShardRouterStrategy;
import org.timothy.shard.core.sql.TableValue;
import org.timothy.shard.core.utils.hash.Hashing;
import org.timothy.shard.core.utils.hash.MurmurHash;

import java.util.List;

/**
 * 路由基础策略
 *
 * @author zhengxun
 * @date 2018-05-24
 */
public abstract class AbstractShardRouterStrategy implements ShardRouterStrategy {

    protected Hashing hash = new MurmurHash();
    private ShardConfig shardConfig;

    public AbstractShardRouterStrategy(ShardConfig shardConfig) {
        this.shardConfig = shardConfig;
    }

    @Override
    public Shard route(RoutingValue routingValue) {
        return doRoute(shardConfig, routingValue.getTableValueList());
    }


    protected abstract Shard doRoute(ShardConfig shardConfig, List<TableValue> tableValueList);
}
