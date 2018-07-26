package org.timothy.shard.core.sharding;

/**
 * 拆分键id以及对应的实际数据源配置对象
 *
 * @author zhengxun
 * @date 2018-05-18
 */
public class ShardMapping {

    /**
     * 拆分键id配置
     * 支持按照"-"/","两种配置方式
     * "-" 表示区间，eg: "0-2",表示0、1、2三个值
     * "," 表示特别指定，eg: "0,1,2",表示0、1、2三个值
     */
    private String shardIdRange;

    /**
     * 实际数据源名称
     */
    private String dataSourceName;

    public String getShardIdRange() {
        return shardIdRange;
    }

    public void setShardIdRange(String shardIdRange) {
        this.shardIdRange = shardIdRange;
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
                "shardIdRange='" + shardIdRange + '\'' +
                ", dataSourceName='" + dataSourceName + '\'' +
                '}';
    }
}
