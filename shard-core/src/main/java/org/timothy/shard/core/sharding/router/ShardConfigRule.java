package org.timothy.shard.core.sharding.router;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.timothy.shard.core.sharding.ShardMapping;
import org.timothy.shard.core.sql.ShardTable;

import java.util.List;

/**
 * 分库分表配置对象
 *
 * @author zhengxun
 * @date 2018-05-18
 */
public class ShardConfigRule {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShardConfigRule.class);
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
        String dataSourceName = null;
        for (ShardMapping shardMapping : shardMappingList) {
            if (shardMapping.getShardIdRange().indexOf("-") > -1) {
                int start = Integer.parseInt(shardMapping.getShardIdRange().substring(0, shardMapping.getShardIdRange().indexOf("-")));
                int end = Integer.parseInt(shardMapping.getShardIdRange().substring(shardMapping.getShardIdRange().indexOf("-") + 1, shardMapping.getShardIdRange().length()));
                if (start <= shardId && shardId <= end) {
                    dataSourceName = shardMapping.getDataSourceName();
                }
            } else if (shardMapping.getShardIdRange().indexOf(",") > -1) {
                String[] configShardIds = shardMapping.getShardIdRange().split(",");
                for (String configShardId : configShardIds) {
                    if (configShardId != null && configShardId.length() > 0 && Integer.parseInt(configShardId) == shardId) {
                        dataSourceName = shardMapping.getDataSourceName();
                    }
                }
            } else if (Integer.parseInt(shardMapping.getShardIdRange()) == shardId) {
                dataSourceName = shardMapping.getDataSourceName();
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("ShardId:" + shardId + "匹配到数据源名称:" + dataSourceName);
        }
        if (StringUtils.isNotBlank(dataSourceName)) {
            return dataSourceName;
        }
        throw new RuntimeException("shardId= " + shardId + "未找到对应的数据源名称，请确认是否配置");
    }


    public int getShardSize() {
        return shardSize;
    }

    public void setShardSize(int shardSize) {
        if ((shardSize & (shardSize - 1)) == 0) {
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
        return "ShardConfigRule{" +
                "shardSize=" + shardSize +
                ", shardTableList=" + shardTableList +
                ", shardMappingList=" + shardMappingList +
                '}';
    }
}
