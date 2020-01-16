package org.apache.shardingsphere.example;

import org.apache.shardingsphere.example.factory.DataSourceFactory;
import org.apache.shardingsphere.example.type.ShardingType;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

public class RawJDBCOrchestrationJavaShardingEncryptTest {
    @Test
    public void assertCommonService() throws SQLException {
        DataSource dataSource = DataSourceFactory.newInstance(ShardingType.SHARDING_ENCRYPT);
        /*ExampleService exampleService = new UserServiceImpl(new UserRepositoryImpl(dataSource));
        ExampleExecuteTemplate.run(exampleService);
        RawJdbcAssertUtils.assertShardingEncrypt(exampleService);*/
    }
}
