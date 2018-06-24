package org.timothy.shard;

import net.sf.jsqlparser.statement.select.PlainSelect;
import org.timothy.shard.dao.UserMapper;
import org.timothy.shard.datasource.CloudDataSourceProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.timothy.shard.domain.User;

import javax.annotation.Resource;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShardFrameworkApplication.class)
@EnableConfigurationProperties({CloudDataSourceProperties.class})
public class InsertTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void test() {
        User user = new User();
        user.setName("111");
        userMapper.insert(user);
    }



    /**
     * 测试用例
     *  1、拆分键为null情况
     *  2、解析出多个table情况
     */
}
