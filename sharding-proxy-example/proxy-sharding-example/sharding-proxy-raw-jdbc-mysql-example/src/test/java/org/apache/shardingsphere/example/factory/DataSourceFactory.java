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

import org.apache.shardingsphere.example.core.api.DataSourceUtil;
import org.apache.shardingsphere.example.core.api.DatabaseType;
import org.apache.shardingsphere.example.type.ShardingType;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DataSourceFactory {
    
    public static DataSource newInstance(final ShardingType shardingType) throws SQLException {
        switch (shardingType) {
            case SHARDING_ENCRYPT:
                return DataSourceUtil.createDataSource("sharding_encrypt", DatabaseType.PROXY_MYSQL);
            case SHARDING_DATABASES_AND_TABLES:
                return DataSourceUtil.createDataSource("sharding_db", DatabaseType.PROXY_MYSQL);
            case MASTER_SLAVE:
                return DataSourceUtil.createDataSource("master_slave_db", DatabaseType.PROXY_MYSQL);
            case SHARDING_MASTER_SLAVE:
                return DataSourceUtil.createDataSource("sharding_master_slave_db", DatabaseType.PROXY_MYSQL);
            case SHARDING_MASTER_SLAVE_ENCRYPT:
                return DataSourceUtil.createDataSource("sharding_master_slave_encrypt_db", DatabaseType.PROXY_MYSQL);
            case ENCRYPT:
                return DataSourceUtil.createDataSource("encrypt_db", DatabaseType.PROXY_MYSQL);
            default:
                throw new UnsupportedOperationException(shardingType.name());
        }
    }
}
