package org.timothy.shard.core.sharding.router;

import org.timothy.shard.core.sharding.ShardMapping;
import org.timothy.shard.core.sql.ShardTable;

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

    /**
     * 根据shardId获取数据源名称
     *
     * @param shardId
     * @return
     */
    public String getDataSourceNameByShardId(int shardId) {
        for (ShardMapping shardMapping : shardMappingList) {
            if (shardMapping.getShardId() == shardId) {
                return shardMapping.getDataSourceName();
            } else {
                throw new RuntimeException("未找到对应的数据源名称，请确认是否配置");
            }
        }
        return null;
    }





    public int getShardSize() {
        return shardSize;
    }

    public void setShardSize(int shardSize) {
        if((shardSize & (shardSize - 1)) == 0){
            this.shardSize = shardSize;
        } else {
            throw new RuntimeException("请确保shardSize的数值为2的幂次方");
        }
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
