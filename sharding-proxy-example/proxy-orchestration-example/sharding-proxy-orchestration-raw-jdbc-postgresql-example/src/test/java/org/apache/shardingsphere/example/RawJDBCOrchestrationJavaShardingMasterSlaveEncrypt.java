package org.apache.shardingsphere.example;

import org.junit.Test;

import java.sql.SQLException;

public class RawJDBCOrchestrationJavaShardingMasterSlaveEncrypt {
    @Test
    public void assertCommonService() throws SQLException {
        /*DataSource dataSource = DataSourceFactory.newInstance(ShardingType.SHARDING_MASTER_SLAVE_ENCRYPT);
        ExampleService exampleService = new UserServiceImpl(new UserRepositoryImpl(dataSource));
        ExampleExecuteTemplate.run(exampleService);
        RawJdbcAssertUtils.assertShardingMasterSlaveEncrypt(exampleService);*/
    }
}
