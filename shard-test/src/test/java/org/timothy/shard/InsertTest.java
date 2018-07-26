package org.timothy.shard;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.timothy.shard.dao.UserMapper;
import org.timothy.shard.datasource.CloudDataSourceProperties;
import org.timothy.shard.domain.User;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Unit test for simple App.
 */
@RunWith(Parameterized.class)
@SpringBootTest(classes = ShardFrameworkApplication.class)
@ContextConfiguration
@EnableConfigurationProperties({CloudDataSourceProperties.class})
public class InsertTest {
    private TestContextManager testContextManager;

    @Before
    public void setUp() throws Exception {
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);
    }

    private User user;

    public InsertTest(User user) {
        this.user = user;
    }

    @Parameterized.Parameters
    public static Collection parameters() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            userList.add(User.builder().name(String.valueOf(i)).build());
        }
        return userList;
    }

    @Resource
    private UserMapper userMapper;

    @Test
    public void test() {
        userMapper.insert(user);
    }


    /**
     * 测试用例
     *  1、拆分键为null情况
     *  2、解析出多个table情况
     */
}
