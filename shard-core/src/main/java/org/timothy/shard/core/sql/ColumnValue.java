package org.timothy.shard.core.sql;

/**
 * 列名以及列值
 * @author zhengxun
 * @date 2018-05-24
 */
public class ColumnValue {

    private String columnName;

    private String columnValue;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    @Override
    public String toString() {
        return "ColumnValue{" +
                "columnName='" + columnName + '\'' +
                ", columnValue='" + columnValue + '\'' +
                '}';
    }
}
