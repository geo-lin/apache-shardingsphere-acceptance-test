package org.apache.shardingsphere.example;

import org.apache.shardingsphere.example.core.api.ExampleExecuteTemplate;
import org.apache.shardingsphere.example.core.api.service.ExampleService;
import org.apache.shardingsphere.example.core.mybatis.common.SpringResultAssertUtils;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

public class SpringNamespaceShardingMasterSlaveTest {

    private static final String CONFIG_FILE = "META-INF/application-sharding-master-slave.xml";

    @Test
    public void assertCommonService() throws SQLException {
        try (ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext(CONFIG_FILE)) {
            ExampleService exampleService = applicationContext.getBean(ExampleService.class);
            ExampleExecuteTemplate.run(exampleService);
            SpringResultAssertUtils.assertExampleServiceShardingMasterSlaveResult(exampleService);
        }
    }
}
