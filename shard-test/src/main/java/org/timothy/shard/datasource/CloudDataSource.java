package org.timothy.shard.datasource;

import com.alibaba.fastjson.JSON;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author zhengxun
 * @date 2018-05-27
 */
//@Configuration
//@MapperScan(basePackages = "com.timothy.shard.dao")
//@EnableConfigurationProperties({CloudDataSourceProperties.class})
//@ConditionalOnProperty(name = {"cloudDataSourceProperties.enable"}, matchIfMissing = true)
public class CloudDataSource {

    private static final String URL = "url";

    @Resource
    private CloudDataSourceProperties cloudDataSourceProperties;

//    @Bean("dataSourceMap")
    public Map<String, DataSource> dataSourceMap() throws Exception {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        Iterator<Map.Entry<String, String>> cloudDataSourcePropertiesIterator = cloudDataSourceProperties.getUrlMap().entrySet().iterator();
        while (cloudDataSourcePropertiesIterator.hasNext()) {
            Map.Entry<String, String> dataSourceProperty = cloudDataSourcePropertiesIterator.next();
            DataSource dataSource = createDataSource(dataSourceProperty.getValue());
            dataSourceMap.put(dataSourceProperty.getKey(), dataSource);
        }
        return dataSourceMap;
    }

    private DataSource createDataSource(String url) throws Exception {
        String cloudDataSourcePropertiesStr = JSON.toJSONString(cloudDataSourceProperties);
        Properties properties = JSON.parseObject(cloudDataSourcePropertiesStr, Properties.class);
        properties.setProperty(URL, url);
        DataSource dataSource = BasicDataSourceFactory.createDataSource(properties);
        return dataSource;
    }
}
