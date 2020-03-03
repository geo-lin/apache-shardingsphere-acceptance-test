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

import org.apache.shardingsphere.example.config.*;
import org.apache.shardingsphere.example.config.cloud.CloudEncryptConfiguration;
import org.apache.shardingsphere.example.config.cloud.CloudMasterSlaveConfiguration;
import org.apache.shardingsphere.example.config.cloud.CloudShardingDatabasesAndTablesConfiguration;
import org.apache.shardingsphere.example.config.local.*;
import org.apache.shardingsphere.example.type.RegistryCenterType;
import org.apache.shardingsphere.example.type.ShardingType;
import org.apache.shardingsphere.orchestration.center.configuration.InstanceConfiguration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

public class DataSourceFactory {
    
    private static boolean loadConfigFromRegCenter = false;
    //    private static boolean loadConfigFromRegCenter = true;
    
    private static RegistryCenterType registryCenterType = RegistryCenterType.ZOOKEEPER;
//    private static RegistryCenterType registryCenterType = RegistryCenterType.NACOS;
    
    public static DataSource newInstance(final ShardingType shardingType) throws SQLException {
        Map<String, InstanceConfiguration> instanceConfigurationMap = getRegistryCenterConfiguration(registryCenterType, shardingType);
        ExampleConfiguration configuration;
        switch (shardingType) {
            case SHARDING_DATABASES_AND_TABLES:
                configuration = loadConfigFromRegCenter
                    ? new CloudShardingDatabasesAndTablesConfiguration(instanceConfigurationMap) : new LocalShardingDatabasesAndTablesConfiguration(instanceConfigurationMap);
                break;
            case MASTER_SLAVE:
                configuration = loadConfigFromRegCenter ? new CloudMasterSlaveConfiguration(instanceConfigurationMap) : new LocalMasterSlaveConfiguration(instanceConfigurationMap);
                break;
            case ENCRYPT:
                configuration = loadConfigFromRegCenter ? new CloudEncryptConfiguration(instanceConfigurationMap) : new LocalEncryptConfiguration(instanceConfigurationMap);
                break;
            case SHARDING_ENCRYPT:
                configuration = loadConfigFromRegCenter ? new CloudEncryptConfiguration(instanceConfigurationMap) : new LocalShardingEncryptConfiguration(instanceConfigurationMap);
                break;
            case SHARDING_MASTER_SLAVE_ENCRYPT:
                configuration = loadConfigFromRegCenter ? new CloudEncryptConfiguration(instanceConfigurationMap) : new LocalShardingMasterSlaveEncryptConfiguration(instanceConfigurationMap);
                break;
            default:
                throw new UnsupportedOperationException(shardingType.name());
        }
        return configuration.getDataSource();
    }
    
    private static Map<String, InstanceConfiguration> getRegistryCenterConfiguration(final RegistryCenterType registryCenterType, ShardingType shardingType) {
        return RegistryCenterType.ZOOKEEPER == registryCenterType ? RegistryCenterConfigurationUtil.getZooKeeperConfiguration(String.valueOf(!loadConfigFromRegCenter), shardingType) :
            RegistryCenterConfigurationUtil.getNacosConfiguration(String.valueOf(!loadConfigFromRegCenter), shardingType);
    }
}
