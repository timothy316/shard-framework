package org.timothy.shard.core.sharding;

/**
 * @author zhengxun
 * @date 2018-05-24
 */
public interface ShardRouterStrategy {

    Shard route(RoutingValue routingValue);
}
