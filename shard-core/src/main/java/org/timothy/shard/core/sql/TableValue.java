package org.timothy.shard.core.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhengxun
 * @date 2018-05-24
 */
public class TableValue {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 列名以及列值
     */
    private List<ColumnValue> columnValueList = new ArrayList<>();

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnValue> getColumnValueList() {
        return columnValueList;
    }

    public void setColumnValueList(List<ColumnValue> columnValueList) {
        this.columnValueList = columnValueList;
    }

    @Override
    public String toString() {
        return "TableValue{" +
                "tableName='" + tableName + '\'' +
                ", columnValueList=" + columnValueList +
                '}';
    }
}
