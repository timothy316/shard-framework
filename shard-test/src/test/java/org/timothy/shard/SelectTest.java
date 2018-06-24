package org.timothy.shard;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.timothy.shard.datasource.CloudDataSourceProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhengxun
 * @date 2018-05-28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShardFrameworkApplication.class)
@EnableConfigurationProperties({CloudDataSourceProperties.class})
public class SelectTest {


    @Test
    public void testPlainSelect(){
        List<Table> tableList = new ArrayList<>();
        tableList.add(new Table("test1"));
        tableList.add(new Table("test2"));
        tableList.add(new Table("test3"));
        String table = PlainSelect.getStringList(tableList, true, false);
        System.out.println(table);

    }
}
