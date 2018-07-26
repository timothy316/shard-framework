package org.timothy.shard.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * 虚拟Connection抽象类
 *
 * @author zhengxun
 * @date 2018-05-18
 */
public abstract class AbstractConnection<T> implements VirtualConnection<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractConnection.class);

    private Map<String, Connection> actualConnectionMap = new HashMap<>();

    private Map<Connection, T> connectionShard = new HashMap<>();
    /**
     * 虚拟数据源
     */
    private VirtualDataSource virtualDataSource;
    /**
     * 默认为true
     */
    private Boolean autoCommit;
    private Boolean readOnly;
    private Integer transactionIsolation;
    private Integer holdability;

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;


    private boolean closed;

    public AbstractConnection(VirtualDataSource virtualDataSource) {
        this.virtualDataSource = virtualDataSource;
    }

    public AbstractConnection(VirtualDataSource virtualDataSource, String username, String password) {
        this.virtualDataSource = virtualDataSource;
        this.username = username;
        this.password = password;
    }

    /**
     * 获取真实数据库链接
     *
     * @param shard
     * @return
     * @throws SQLException
     */
    protected Connection getActualConnection(T shard) throws SQLException {
        Connection resultConnection = null;
        String cacheKey = createCacheKey(shard);
        resultConnection = actualConnectionMap.get(cacheKey);
        if (resultConnection == null) {
            //todo why?
            synchronized (this) {
                if (username != null) {
                    resultConnection = virtualDataSource.createActualConnection(shard, username, password);
                } else {
                    resultConnection = virtualDataSource.createActualConnection(shard);
                }
                actualConnectionMap.put(cacheKey, resultConnection);
                connectionShard.put(resultConnection, shard);
                if (autoCommit != null) {
                    resultConnection.setAutoCommit(autoCommit);
                }
                if (readOnly != null) {
                    resultConnection.setReadOnly(readOnly);
                }
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("获取到新连接" + resultConnection + "通过shard:" + shard);
            }
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("获取已有连接" + resultConnection + "通过shard:" + shard);
            }
        }

        return resultConnection;
    }

    protected abstract String createCacheKey(T shard);

    protected void checkIsOpen() {
        if (closed) {
            throw new RuntimeException("当前连接已关闭");
        }
    }

    @Override
    public Statement createActualStatement(T shard) throws SQLException {
        return getActualConnection(shard).createStatement();
    }

    @Override
    public PreparedStatement createActualPreparedStatement(T shard, String sql) throws SQLException {
        return getActualConnection(shard).prepareStatement(sql);
    }

    @Override
    public DataSource getDataSource() {
        return virtualDataSource;
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        this.autoCommit = autoCommit;
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return this.autoCommit != null ? this.autoCommit : true;
    }

    @Override
    public void commit() throws SQLException {
        checkIsOpen();
        for (Connection connection : actualConnectionMap.values()) {
            T shard = connectionShard.get(connection);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("commit已有连接，通过shard:" + shard + "connection" + connection);
            }
            connection.commit();
        }

    }

    @Override
    public void rollback() throws SQLException {
        checkIsOpen();
        for (Connection connection : actualConnectionMap.values()) {
            T shard = connectionShard.get(connection);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("rowback已有连接，通过shard:" + shard + "connection" + connection);
            }
            connection.rollback();
        }
    }

    @Override
    public void close() throws SQLException {
        checkIsOpen();
        closed = true;
        for (Connection connection : actualConnectionMap.values()) {
            T shard = connectionShard.get(connection);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("通过shard:" + shard + "获取到的连接已关闭");
            }
            connection.close();
        }
    }

    @Override
    public boolean isClosed() throws SQLException {
        return closed;
    }


    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return null;
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        this.readOnly = readOnly;
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return this.readOnly;
    }


    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        checkIsOpen();
        this.transactionIsolation = level;
        for (Connection connection : actualConnectionMap.values()) {
            connection.setTransactionIsolation(level);
        }
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return this.transactionIsolation;
    }


    @Override
    public void setHoldability(int holdability) throws SQLException {
        checkIsOpen();
        this.holdability = holdability;
    }

    @Override
    public int getHoldability() throws SQLException {
        return this.holdability != null ? holdability : -1;
    }


    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return null;
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public void clearWarnings() throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");
    }


    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");

    }

    @Override
    public String getCatalog() throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public Clob createClob() throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public Blob createBlob() throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public NClob createNClob() throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return false;
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        throw new UnsupportedOperationException("不支持此方法");
    }

    @Override
    public void setSchema(String schema) throws SQLException {

    }

    @Override
    public String getSchema() throws SQLException {
        return null;
    }

    @Override
    public void abort(Executor executor) throws SQLException {

    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return 0;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
