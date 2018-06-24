package org.timothy.shard.core.utils.hash;

/**
 * @author zhengxun
 * @date 2018-05-25
 */
public interface Hashing {

    long hash(byte[] key);

    long hash(String key);
}
