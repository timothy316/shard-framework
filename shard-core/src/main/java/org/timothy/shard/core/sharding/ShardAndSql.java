package org.timothy.shard.core.sharding;

import org.timothy.shard.core.sql.Sql;

/**
 * @author zhengxun
 * @date 2018-05-19
 */
public class ShardAndSql<T> {

    private T shard;

    private Sql sql;


    public ShardAndSql(T shard, Sql sql) {
        this.shard = shard;
        this.sql = sql;
    }

    public T getShard() {
        return shard;
    }

    public void setShard(T shard) {
        this.shard = shard;
    }

    public Sql getSql() {
        return sql;
    }

    public void setSql(Sql sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return "ShardAndSql{" +
                "shard=" + shard +
                ", sql=" + sql +
                '}';
    }
}
