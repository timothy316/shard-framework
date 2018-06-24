package org.timothy.shard.core.sql;

import org.timothy.shard.core.utils.LRUCache;

import java.util.Map;

/**
 * @author zhengxun
 * @date 2018-05-24
 */
public class SqlParseAndBuilderFactory {

    private Map<String, SqlParseAndBuilder> cache;

    public SqlParseAndBuilderFactory(int cacheSize) {
        cache = new LRUCache<>(cacheSize);
    }

    public SqlParseAndBuilder getSqlParseAndBuilder(String sqlText) {
        SqlParseAndBuilder sqlParseAndBuilder = cache.get(sqlText);
        if (sqlParseAndBuilder == null) {
            synchronized (cache) {
                sqlParseAndBuilder = cache.get(sqlText);
                if (sqlParseAndBuilder == null) {
                    sqlParseAndBuilder = new SqlParseAndBuilder(sqlText);
                    cache.put(sqlText, sqlParseAndBuilder);
                }
            }
            return sqlParseAndBuilder;
        }
        return sqlParseAndBuilder;
    }
}
