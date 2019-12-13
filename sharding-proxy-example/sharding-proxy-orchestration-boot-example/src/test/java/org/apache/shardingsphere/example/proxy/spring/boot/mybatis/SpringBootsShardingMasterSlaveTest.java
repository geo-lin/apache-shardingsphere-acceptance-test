package org.apache.shardingsphere.example.proxy.spring.boot.mybatis;

import org.apache.shardingsphere.example.core.api.senario.AnnotationCommonServiceScenario;
import org.apache.shardingsphere.example.core.mybatis.common.SpringResultAssertUtils;
import org.apache.shardingsphere.example.core.mybatis.service.SpringPojoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestMain.class)
@ActiveProfiles("sharding-master-slave")
public class SpringBootsShardingMasterSlaveTest {
    
    @Autowired
    private SpringPojoService commonService;
    
    @Test
    public void assertCommonService() throws SQLException {
        /**
         * sharding-proxy cannot query data when 'max.connections.size.per.query=1' 
         * for server.yaml using springboot and mybatis framework
         */
        AnnotationCommonServiceScenario scenario = new AnnotationCommonServiceScenario(commonService);
        scenario.process();
        SpringResultAssertUtils.assertShardingMasterSlaveResult(commonService);
    }
}
