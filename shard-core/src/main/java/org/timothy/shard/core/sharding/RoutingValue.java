package org.timothy.shard.core.sharding;

import org.timothy.shard.core.sql.Sql;
import org.timothy.shard.core.sql.TableValue;

import java.util.List;

/**
 * @author zhengxun
 * @date 2018-05-24
 */
public class RoutingValue {
    private Sql sourceSql;

    private List<String> tableNames;

    private List<TableValue> tableValueList;

    public RoutingValue(Sql sourceSql, List<String> tableNames, List<TableValue> tableValueList) {
        this.sourceSql = sourceSql;
        this.tableNames = tableNames;
        this.tableValueList = tableValueList;
    }

    public Sql getSourceSql() {
        return sourceSql;
    }

    public List<String> getTableNames() {
        return tableNames;
    }

    public List<TableValue> getTableValueList() {
        return tableValueList;
    }

    @Override
    public String toString() {
        return "RoutingValue{" +
                "sourceSql=" + sourceSql +
                ", tableNames=" + tableNames +
                ", tableValueList=" + tableValueList +
                '}';
    }
}
