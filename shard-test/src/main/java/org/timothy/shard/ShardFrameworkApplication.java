package org.timothy.shard;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

/**
 * @author zhengxun
 * @date 2018-05-27
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MybatisAutoConfiguration.class})
@ImportResource({
        "classpath:spring-datasource.xml",
        "classpath:spring-dao.xml"
})
public class ShardFrameworkApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ShardFrameworkApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ShardFrameworkApplication.class, args);
    }
}
