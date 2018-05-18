package com.timothy.shard.core.sharding;

import javax.sql.DataSource;

/**
 * 真实数据源
 * @author zhengxun
 * @date 2018-05-18
 */
public class DataSourceItem {

    private String dataSourceName;

    private DataSource actualDataSource;

    public DataSourceItem() {
    }

    public DataSourceItem(String dataSourceName, DataSource actualDataSource) {
        this.dataSourceName = dataSourceName;
        this.actualDataSource = actualDataSource;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public DataSource getActualDataSource() {
        return actualDataSource;
    }

    public void setActualDataSource(DataSource actualDataSource) {
        this.actualDataSource = actualDataSource;
    }

    @Override
    public String toString() {
        return "DataSourceItem{" +
                "dataSourceName='" + dataSourceName + '\'' +
                ", actualDataSource=" + actualDataSource +
                '}';
    }
}
