package com.timothy.shard;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author zhengxun
 * @version 2018-05-14
 */
public class ShardConnection extends AbstractConnection{
    @Override
    public Statement createStatement() throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return null;
    }
}
