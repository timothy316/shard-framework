package org.timothy.shard;

import net.sf.jsqlparser.schema.Table;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhengxun
 * @date 2018-05-28
 */
public class CollectionTest {

    @Test
    public void testMap(){
        Map<Table, Table> tableMap = new HashMap<>();
        Table table = new Table();
        Table table2 = new Table();
        tableMap.put(table, table);
        tableMap.put(table2, table2);
        System.out.println(tableMap);
    }
}
