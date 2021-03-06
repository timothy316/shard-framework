package org.timothy.shard.core.sql;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author zhengxun
 * @date 2018-05-19
 */
public class Sql implements Cloneable{

    /**
     * 上层传递的Sql,包含占位符
     */
    private String text;

    /**
     * 是否预编译
     */
    private boolean prepared;
    /**
     * SQL参数
     */
    private SortedMap<Integer, Object> parameters;

    public Sql(String text, boolean prepared, SortedMap<Integer, Object> parameters) {
        this.text = text;
        this.prepared = prepared;
        this.parameters = parameters;
    }

    public String getText() {
        return text;
    }

    public boolean isPrepared() {
        return prepared;
    }

    public SortedMap<Integer, Object> getParameters() {
        return parameters;
    }

    @Override
    public Sql clone() {
        return new Sql(text, prepared, parameters == null ? null : new TreeMap<Integer, Object>(parameters));
    }

    @Override
    public String toString() {
        return "Sql{" +
                "text='" + text + '\'' +
                ", prepared=" + prepared +
                ", parameters=" + parameters +
                '}';
    }
}
