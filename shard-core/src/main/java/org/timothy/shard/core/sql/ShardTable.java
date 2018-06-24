package org.timothy.shard.core.sql;

import java.util.List;

/**
 * 拆分的表相关配置
 * 包含表名、拆分键
 * @author zhengxun
 * @date 2018-05-18
 */
public class ShardTable {
    /***
     * 拆分表名
     */
    private String tableName;

    /**
     * 拆分键
     */
    private List<String> shardColumnNameList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getShardColumnNameList() {
        return shardColumnNameList;
    }

    public void setShardColumnNameList(List<String> shardColumnNameList) {
        this.shardColumnNameList = shardColumnNameList;
    }

    @Override
    public String toString() {
        return "ShardTable{" +
                "tableName='" + tableName + '\'' +
                ", shardColumnNameList=" + shardColumnNameList +
                '}';
    }
}
