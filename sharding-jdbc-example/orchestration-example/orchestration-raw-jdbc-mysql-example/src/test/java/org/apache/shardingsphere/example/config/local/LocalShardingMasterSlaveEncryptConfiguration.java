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

package org.apache.shardingsphere.example.config.local;

import com.google.common.collect.Lists;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.encrypt.api.EncryptColumnRuleConfiguration;
import org.apache.shardingsphere.encrypt.api.EncryptRuleConfiguration;
import org.apache.shardingsphere.encrypt.api.EncryptTableRuleConfiguration;
import org.apache.shardingsphere.encrypt.api.EncryptorRuleConfiguration;
import org.apache.shardingsphere.example.algorithm.PreciseModuloShardingDatabaseAlgorithm;
import org.apache.shardingsphere.example.algorithm.PreciseModuloShardingTableAlgorithm;
import org.apache.shardingsphere.example.config.ExampleConfiguration;
import org.apache.shardingsphere.example.core.api.DataSourceUtil;
import org.apache.shardingsphere.example.core.api.DatabaseType;
import org.apache.shardingsphere.orchestration.center.configuration.InstanceConfiguration;
import org.apache.shardingsphere.orchestration.center.configuration.OrchestrationConfiguration;
import org.apache.shardingsphere.shardingjdbc.orchestration.api.OrchestrationShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

public final class LocalShardingMasterSlaveEncryptConfiguration implements ExampleConfiguration {
    
    private final Map<String, InstanceConfiguration> instanceConfigurationMap;
    
    public LocalShardingMasterSlaveEncryptConfiguration(final Map<String, InstanceConfiguration> instanceConfigurationMap) {
        this.instanceConfigurationMap = instanceConfigurationMap;
    }
    
    @Override
    public DataSource getDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getTableRuleConfiguration());
        shardingRuleConfig.setMasterSlaveRuleConfigs(getMasterSlaveRuleConfigurations());
        shardingRuleConfig.setEncryptRuleConfig(getEncryptRuleConfiguration());
        OrchestrationConfiguration orchestrationConfig = new OrchestrationConfiguration(instanceConfigurationMap);
        return OrchestrationShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, new Properties(), orchestrationConfig);
    }
    
    private static TableRuleConfiguration getTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("t_user", "ds_${0..1}.t_user_${[0, 1]}");
        result.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds_${user_id % 2}"));
        result.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id","t_user_${user_id % 2}"));
        return result;
    }
    
    private static List<MasterSlaveRuleConfiguration> getMasterSlaveRuleConfigurations() {
        MasterSlaveRuleConfiguration masterSlaveRuleConfig1 = new MasterSlaveRuleConfiguration("ds_0", "demo_ds_master_0", Arrays.asList("demo_ds_master_0_slave_0", "demo_ds_master_0_slave_1"));
        MasterSlaveRuleConfiguration masterSlaveRuleConfig2 = new MasterSlaveRuleConfiguration("ds_1", "demo_ds_master_1", Arrays.asList("demo_ds_master_1_slave_0", "demo_ds_master_1_slave_1"));
        return Lists.newArrayList(masterSlaveRuleConfig1, masterSlaveRuleConfig2);
    }
    
    private EncryptRuleConfiguration getEncryptRuleConfiguration() {
        Properties props = new Properties();
        props.setProperty("aes.key.value", "123456");
        EncryptorRuleConfiguration encryptorAES = new EncryptorRuleConfiguration("AES", props);
        EncryptorRuleConfiguration encryptorMD5 = new EncryptorRuleConfiguration("MD5",new Properties());
        Map<String, EncryptColumnRuleConfiguration> columns = new HashMap<>();
        EncryptColumnRuleConfiguration columnUserName = new EncryptColumnRuleConfiguration("user_name", "user_name_cipher", "", "aes");
        EncryptColumnRuleConfiguration columnPwd = new EncryptColumnRuleConfiguration("pwd_plain", "pwd_cipher", "", "md5");
        columns.put("user_name",columnUserName);
        columns.put("pwd",columnPwd);
        EncryptTableRuleConfiguration tableConfig = new EncryptTableRuleConfiguration(columns);
        EncryptRuleConfiguration result = new EncryptRuleConfiguration();
        result.getEncryptors().put("aes", encryptorAES);
        result.getEncryptors().put("md5",encryptorMD5);
        result.getTables().put("t_user", tableConfig);
        return result;
    }
    
    private static Map<String, DataSource> createDataSourceMap() {
        final Map<String, DataSource> result = new HashMap<>();
        result.put("demo_ds_master_0", DataSourceUtil.createDataSource("demo_ds_master_0", DatabaseType.MYSQL));
        result.put("demo_ds_master_0_slave_0", DataSourceUtil.createDataSource("demo_ds_master_0_slave_0", DatabaseType.MYSQL));
        result.put("demo_ds_master_0_slave_1", DataSourceUtil.createDataSource("demo_ds_master_0_slave_1", DatabaseType.MYSQL));
        result.put("demo_ds_master_1", DataSourceUtil.createDataSource("demo_ds_master_1", DatabaseType.MYSQL));
        result.put("demo_ds_master_1_slave_0", DataSourceUtil.createDataSource("demo_ds_master_1_slave_0", DatabaseType.MYSQL));
        result.put("demo_ds_master_1_slave_1", DataSourceUtil.createDataSource("demo_ds_master_1_slave_1", DatabaseType.MYSQL));
        return result;
    }
    
    private static Properties getProperties() {
        Properties result = new Properties();
        result.setProperty("worker.id", "123");
        return result;
    }
}
