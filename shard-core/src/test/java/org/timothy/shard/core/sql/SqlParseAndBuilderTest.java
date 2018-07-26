package org.timothy.shard.core.sql;


import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * SQL解析测试类
 *
 * @author zhengxun
 * @date 2018-06-24
 */
@RunWith(Parameterized.class)
public class SqlParseAndBuilderTest {

    private String a;
    private String b;
    private String c;

    public SqlParseAndBuilderTest(String a,String b,String c){
        this.a = a;
        this.b = b;
        this.c = c;
    };

    @Parameterized.Parameters
    public static Collection<String []> getParams(){
        return Arrays.asList(new String [][]{
                {"a","b","ab"},
                {"a","b","ab"},
        });
    }

    @Test
    public void test(){
//        assertEquals(re(a,b), c);
        String ss = new String();
        System.out.println(StringUtils.isNotBlank(ss));
    }

    public String re(String a,String b){
        return a+b;
    }

}
