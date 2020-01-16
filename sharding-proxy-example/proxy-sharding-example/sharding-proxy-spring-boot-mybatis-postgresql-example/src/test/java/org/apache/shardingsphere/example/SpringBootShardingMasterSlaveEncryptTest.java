package org.apache.shardingsphere.example;

import org.apache.shardingsphere.example.core.api.service.ExampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * @author linzesi
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestMain.class)
@ActiveProfiles("sharding-ms-encrypt")
public class SpringBootShardingMasterSlaveEncryptTest {
    
    @Resource(name = "encrypt")
    ExampleService exampleService;
    
    @Test
    public void commonService() throws SQLException {
       /* ExampleExecuteTemplate.run(exampleService);
        SpringResultAssertUtils.assertExampleServiceMasterSlaveEncryptResult(exampleService);*/
    }
}
