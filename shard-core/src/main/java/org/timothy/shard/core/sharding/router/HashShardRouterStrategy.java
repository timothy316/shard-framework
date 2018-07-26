package org.timothy.shard.core.sharding.router;

import com.google.common.collect.HashMultimap;
import org.timothy.shard.core.sharding.Shard;
import org.timothy.shard.core.sql.ColumnValue;
import org.timothy.shard.core.sql.ShardTable;
import org.timothy.shard.core.sql.TableValue;

import java.util.*;

/**
 * 哈希取模 拆分策略
 * @author zhengxun
 * @date 2018-05-25
 */
public class HashShardRouterStrategy extends AbstractShardRouterStrategy {

    public HashShardRouterStrategy(ShardConfigRule shardConfigRule) {
        super(shardConfigRule);
    }

    @Override
    protected Shard doRoute(ShardConfigRule shardConfigRule, List<TableValue> tableValueList) {
        ShardTable matchedShardTable = null;
        TableValue matchedTableValue = null;

        shardConfigRule.getShardTableList();
        for (TableValue tableValue : tableValueList) {
            for (ShardTable shardTable : shardConfigRule.getShardTableList()) {
                HashMultimap<String, String> shardColumnMap = HashMultimap.create();
                Map<String, ColumnValue> columnValueMap = new HashMap<>(shardTable.getShardColumnNameList().size());
                if (shardTable.getTableName().equalsIgnoreCase(tableValue.getTableName())) {
                    for (ColumnValue columnValue : tableValue.getColumnValueList()) {
                        //SqlParse解析出来的参数扔到columnValueMap
                        columnValueMap.put(columnValue.getColumnName().toUpperCase(), columnValue);
                        for (String shardColumnName : shardTable.getShardColumnNameList()) {
                            if (columnValue.getColumnName().equalsIgnoreCase(shardColumnName)) {
                                shardColumnMap.put(shardColumnName.toUpperCase(), columnValue.getColumnValue());
                            }
                        }
                    }
                }
                for (String shardColumnName : shardTable.getShardColumnNameList()) {
                    Set<String> shardColumnValueSet = shardColumnMap.get(shardColumnName.toUpperCase());
                    if (shardColumnValueSet.size() == 0) {
                        throw new RuntimeException("拆分键的值为空,表:" + shardTable.getTableName() + ",属性值:" + tableValueList);
                    } else if (shardColumnValueSet.size() != 1) {
                        throw new RuntimeException("批量操作不支持拆分键多值情况，表:" + shardTable.getTableName() + ",拆分键值:" + shardColumnValueSet);
                    }
                }

                List<String> unmatchedColumns = new ArrayList<>(shardTable.getShardColumnNameList());
                for (String shardColumnName : shardTable.getShardColumnNameList()) {
                    ColumnValue columnValue = columnValueMap.get(shardColumnName.toUpperCase());
                    if (columnValue != null) {
                        unmatchedColumns.remove(shardColumnName);
                    }
                }
                if (unmatchedColumns.isEmpty()) {
                    matchedShardTable = shardTable;
                    matchedTableValue = tableValue;
                    break;
                }
            }
            if (matchedTableValue != null) {
                break;
            }
        }

        Map<String, ColumnValue> columnValueMap = new HashMap<>();
        for (ColumnValue columnValue : matchedTableValue.getColumnValueList()) {
            columnValueMap.put(columnValue.getColumnName().toUpperCase(), columnValue);
        }
        StringBuilder shardValue = new StringBuilder();
        for (String columnName : matchedShardTable.getShardColumnNameList()) {
            ColumnValue columnValue = columnValueMap.get(columnName.toUpperCase());
            if (columnValue == null) {
                throw new RuntimeException("没有找到" + columnName + "对应的值");
            }
            shardValue.append(columnValue.getColumnValue());
        }
        long h;
        int shardId = (int) (((h = hash.hash(shardValue.toString())) ^ (h >>> 32)) & (shardConfigRule.getShardSize() - 1));
        String dataSourceName = shardConfigRule.getDataSourceNameByShardId(shardId);
        if (dataSourceName == null) {
            throw new RuntimeException("未找到对应的数据源名称，请确认是否配置");
        }
        return new Shard(shardId, dataSourceName);
    }


}
