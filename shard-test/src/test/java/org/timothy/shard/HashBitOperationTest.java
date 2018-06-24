package org.timothy.shard;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.timothy.shard.core.utils.hash.MurmurHash;

import java.util.HashMap;
import java.util.Map;

/**
 * 哈希混乱运算主要参考JDK hashMap的思想，高位与低位做异或运算
 * 最终结果为做过异或运算的分布非常平均，直接哈希与运算的分布差异很大
 * @see org.timothy.shard.core.sharding.router.HashShardRouterStrategy
 * @author zhengxun
 * @date 2018-06-24
 */
public class HashBitOperationTest {


    @Test
    public void testMurmurHash() {
        Map<Long, Long> map = new HashMap();
        MurmurHash murmurHash = new MurmurHash();
        for (int i = 0; i < 1000000; i++) {
            long h;
            //直接取哈希值
//            long hash = (h = murmurHash.hash(String.valueOf(i)));
            //高位低位异或运算
            long hash = (h = murmurHash.hash(String.valueOf(i))) ^ (h >>> 32);
            long result = hash & (128 - 1);
            if (map.containsKey(result)) {
                map.put(result, map.get(result) + 1);
            } else {
                map.put(result, 1L);
            }
        }
        System.out.println(JSON.toJSONString(map));
    }

    @Test
    public void testNativeHash() {
        Map<Long, Long> map = new HashMap();
        for (int i = 0; i < 1000000; i++) {
            long h;
            //直接取哈希值
//            long hash = (h = String.valueOf(i).hashCode());
            //高位低位异或运算
            long hash = (h = String.valueOf(i).hashCode()) ^ (h >>> 16);
            long result = hash & (128 - 1);
            if (map.containsKey(result)) {
                map.put(result, map.get(result) + 1);
            } else {
                map.put(result, 1L);
            }
        }
        System.out.println(JSON.toJSONString(map));
    }
}
