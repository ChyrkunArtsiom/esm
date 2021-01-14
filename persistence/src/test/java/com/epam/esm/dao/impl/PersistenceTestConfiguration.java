package com.epam.esm.dao.impl;

import com.epam.esm.PersistenceConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@Configuration
@Import(PersistenceConfiguration.class)
public class PersistenceTestConfiguration {

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:mem:testdb;MODE=PostgreSQL");
        dataSourceBuilder.username("esm_user_for_jenkins");
        dataSourceBuilder.password("127001");
        return dataSourceBuilder.build();
    }
}
