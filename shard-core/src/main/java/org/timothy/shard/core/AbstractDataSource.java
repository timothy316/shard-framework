package org.timothy.shard.core;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * @author zhengxun
 * @date 2018-05-18
 */
public abstract class AbstractDataSource<T> implements VirtualDataSource<T> {
    private PrintWriter logWriter;
    private int logTimeout = 0;

    @Override
    public abstract Connection getConnection() throws SQLException;


    @Override
    public abstract Connection getConnection(String username, String password) throws SQLException;


    /**
     * 获取真正数据源链接
     * @param shard
     * @return
     * @throws SQLException
     */
    @Override
    public Connection createActualConnection(T shard) throws SQLException {
        return this.createActualConnection(shard, null, null);
    }

    /**
     * 获取真正数据源链接
     * @param shard
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    @Override
    public Connection createActualConnection(T shard, String username, String password) throws SQLException {
        DataSource actualDataSource = createActualDataSource(shard);
        if(actualDataSource == null){
            throw new RuntimeException("无效的shard，没有对应的数据源，shard:" + shard);
        }
        if(logTimeout > 0){
            actualDataSource.setLoginTimeout(logTimeout);
        }
        if(logWriter != null){
            actualDataSource.setLogWriter(logWriter);
        }
        if(username != null){
            return actualDataSource.getConnection(username, password);
        } else {
            return actualDataSource.getConnection();
        }
    }

    /**
     * 根据shard获取真实数据源
     * @param shard
     * @return
     */
    protected abstract DataSource createActualDataSource(T shard);

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return logWriter;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        this.logWriter = out;
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        this.logTimeout = seconds;
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return logTimeout;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
