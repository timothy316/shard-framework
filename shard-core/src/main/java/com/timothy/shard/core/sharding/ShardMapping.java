package com.timothy.shard.core.sharding;

/**
 * 拆分键id以及对应的实际数据源配置对象
 *
 * @author zhengxun
 * @date 2018-05-18
 */
public class ShardMapping {

    /**
     * 拆分键id
     */
    private int shardId;

    /**
     * 实际数据源名称
     */
    private String dataSourceName;

    public int getShardId() {
        return shardId;
    }

    public void setShardId(int shardId) {
        this.shardId = shardId;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    @Override
    public String toString() {
        return "ShardMapping{" +
                "shardId=" + shardId +
                ", dataSourceName='" + dataSourceName + '\'' +
                '}';
    }
}
