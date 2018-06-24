package org.timothy.shard.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.timothy.shard.core.sharding.RoutingValue;
import org.timothy.shard.core.sharding.Shard;
import org.timothy.shard.core.sharding.ShardAndSql;
import org.timothy.shard.core.sharding.ShardRouterStrategy;
import org.timothy.shard.core.sql.*;

import java.sql.SQLException;
import java.util.List;

/**
 * @author zhengxun
 * @date 2018-05-19
 */
public class ShardStatement extends AbstractPreparedStatement {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShardStatement.class);
    private ShardRouterStrategy shardRouterStrategy;


    public ShardStatement(ShardRouterStrategy shardRouterStrategy, VirtualConnection virtualConnection, String sql) {
        super(virtualConnection, sql);
        this.shardRouterStrategy = shardRouterStrategy;
    }

    @Override
    public ShardAndSql createShardAndSql(Sql sql) throws SQLException {
        ShardConnection shardConnection = (ShardConnection) super.getConnection();
        ShardDataSource shardDataSource = (ShardDataSource) shardConnection.getDataSource();
        SqlParseAndBuilderFactory sqlParseAndBuilderFactory = shardDataSource.getSqlParseAndBuilderFactory();
        SqlParseAndBuilder sqlParseAndBuilder = sqlParseAndBuilderFactory.getSqlParseAndBuilder(sql.getText());
        List<TableValue> tableValueList = sqlParseAndBuilder.getTableValueList();
        for (TableValue tableValue : tableValueList) {
            for (int i = 0; i < tableValue.getColumnValueList().size(); i++) {
                ColumnValue columnValue = tableValue.getColumnValueList().get(i);
                columnValue.setColumnValue(String.valueOf(sql.getParameters().get(i + 1)));
            }
        }
        Sql sourceSql = new Sql(sql.getText(), sql.isPrepared(), sql.clone().getParameters());
        RoutingValue routingValue = new RoutingValue(sourceSql, sqlParseAndBuilder.getTableNames(), sqlParseAndBuilder.getTableValueList());
        Shard shard = shardRouterStrategy.route(routingValue);
        String shardSqlForParse = sqlParseAndBuilder.createSqlPlaceWithShardId(shard.getShardId());
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("==> Shard Preparing:" + shardSqlForParse);
            LOGGER.debug("==> Shard Parameters:" + sql.getParameters());
        }
        Sql shardSql = new Sql(shardSqlForParse, sql.isPrepared(), sql.getParameters());
        ShardAndSql shardAndSql = new ShardAndSql(shard, shardSql);
        return shardAndSql;
    }
}
