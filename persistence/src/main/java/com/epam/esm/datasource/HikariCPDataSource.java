package com.epam.esm.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Configuration class for connection to database.
 */
@PropertySource({"classpath:/datasource.properties"})
@Configuration
@EnableTransactionManagement
public class HikariCPDataSource {
    @Value("${dataSource.user}")
    private String username;
    @Value("${dataSource.password}")
    private String password;
    @Value("${dataSource.dbUrl}")
    private String url;
    @Value("${dataSource.driverName}")
    private String driverName;

    /**
     * Creates data source for postgresql database.
     *
     * @return the DataSource object.
     */
    @Bean
    public DataSource postgresqlDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setDriverClassName(driverName);
        config.setUsername(username);
        config.setPassword(password);

        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    /**
     * Creates Transaction Manager for @Transactional functionality.
     *
     * @return the PlatformTransactionManager object
     */
    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(postgresqlDataSource());
    }

    /**
     * Creates jdbcTemplate object.
     *
     * @param dataSource the data source
     * @return the jdbcTemplate object
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
