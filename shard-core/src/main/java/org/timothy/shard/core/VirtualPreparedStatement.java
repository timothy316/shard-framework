package org.timothy.shard.core;

import java.sql.PreparedStatement;

/**
 * 虚拟preparedStatement
 * @author zhengxun
 * @date 2018-05-18
 */
public interface VirtualPreparedStatement<T> extends VirtualStatement<T>, PreparedStatement {
}
