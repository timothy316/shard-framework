package com.timothy.shard.core;

import java.util.List;

/**
 * 分库分表配置对象
 *
 * @author zhengxun
 * @date 2018-05-18
 */
public class ShardConfig {
    /**
     * 拆分的数据源个数
     */
    private int shardSize;

    /**
     * 需要分库分表的表
     */
    private List<ShardTable> shardTableList;

    /**
     * shardId与实际数据源对应关系
     */
    private List<ShardMapping> shardMappingList;


    public int getShardSize() {
        return shardSize;
    }

    public void setShardSize(int shardSize) {
        this.shardSize = shardSize;
    }

    public List<ShardTable> getShardTableList() {
        return shardTableList;
    }

    public void setShardTableList(List<ShardTable> shardTableList) {
        this.shardTableList = shardTableList;
    }

    public List<ShardMapping> getShardMappingList() {
        return shardMappingList;
    }

    public void setShardMappingList(List<ShardMapping> shardMappingList) {
        this.shardMappingList = shardMappingList;
    }

    @Override
    public String toString() {
        return "ShardConfig{" +
                "shardSize=" + shardSize +
                ", shardTableList=" + shardTableList +
                ", shardMappingList=" + shardMappingList +
                '}';
    }
}
