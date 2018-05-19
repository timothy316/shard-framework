package org.timothy.shard.core.sharding;

/**
 * 根据一定规则匹配到的shardId以及对应的真实数据源名称
 * 后续会根据据此从shardConfig中配置的拆分信息获取真实数据源引用
 * @author zhengxun
 * @date 2018-05-18
 */
public class Shard {
    /**
     * 拆分id
     */
    private int shardId;
    /**
     * 数据库源名称
     */
    private String dataSourceName;

    public Shard(int shardId, String dataSourceName) {
        this.shardId = shardId;
        this.dataSourceName = dataSourceName;
    }

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
        return "Shard{" +
                "shardId=" + shardId +
                ", dataSourceName='" + dataSourceName + '\'' +
                '}';
    }
}
