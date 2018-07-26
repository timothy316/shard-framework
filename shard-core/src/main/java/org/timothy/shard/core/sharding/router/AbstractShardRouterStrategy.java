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
    private ShardConfigRule shardConfigRule;

    public AbstractShardRouterStrategy(ShardConfigRule shardConfigRule) {
        this.shardConfigRule = shardConfigRule;
    }

    @Override
    public Shard route(RoutingValue routingValue) {
        return doRoute(shardConfigRule, routingValue.getTableValueList());
    }


    protected abstract Shard doRoute(ShardConfigRule shardConfigRule, List<TableValue> tableValueList);
}
