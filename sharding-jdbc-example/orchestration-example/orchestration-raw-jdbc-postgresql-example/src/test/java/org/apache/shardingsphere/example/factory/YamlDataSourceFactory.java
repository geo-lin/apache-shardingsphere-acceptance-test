/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.example.factory;

import org.apache.shardingsphere.example.type.ShardingType;
import org.apache.shardingsphere.shardingjdbc.orchestration.api.yaml.YamlOrchestrationEncryptDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.orchestration.api.yaml.YamlOrchestrationMasterSlaveDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.orchestration.api.yaml.YamlOrchestrationShardingDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class YamlDataSourceFactory {
    
    public static DataSource newInstance(final ShardingType shardingType) throws SQLException, IOException {
        switch (shardingType) {
            case SHARDING_DATABASES:
                return YamlOrchestrationShardingDataSourceFactory.createDataSource(getFile("/META-INF/sharding-databases.yaml"));
            case SHARDING_TABLES:
                return YamlOrchestrationShardingDataSourceFactory.createDataSource(getFile("/META-INF/sharding-tables.yaml"));
            case SHARDING_DATABASES_AND_TABLES:
                return YamlOrchestrationShardingDataSourceFactory.createDataSource(getFile("/META-INF/sharding-databases-tables.yaml"));
            case ENCRYPT:
                return YamlOrchestrationEncryptDataSourceFactory.createDataSource(getFile("/META-INF/encrypt-only.yaml"));
            case SHARDING_ENCRYPT:
                return YamlOrchestrationShardingDataSourceFactory.createDataSource(getFile("/META-INF/sharding-encrypt.yaml"));
            case MASTER_SLAVE:
                return YamlOrchestrationMasterSlaveDataSourceFactory.createDataSource(getFile("/META-INF/master-slave.yaml"));
            case SHARDING_MASTER_SLAVE:
                return YamlOrchestrationShardingDataSourceFactory.createDataSource(getFile("/META-INF/sharding-master-slave.yaml"));
            case SHARDING_MASTER_SLAVE_ENCRYPT:
                return YamlOrchestrationShardingDataSourceFactory.createDataSource(getFile("/META-INF/sharding-master-slave-encrypt.yaml"));
            default:
                throw new UnsupportedOperationException(shardingType.name());
        }
    }
    
    private static File getFile(final String fileName) {
        return new File(Thread.currentThread().getClass().getResource(fileName).getFile());
    }
}
