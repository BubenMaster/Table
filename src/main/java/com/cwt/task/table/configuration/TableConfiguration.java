package com.cwt.task.table.configuration;


import com.vaadin.cdi.annotation.VaadinServiceEnabled;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.SQLDialect;
import org.jooq.impl.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.sql.SQLException;


@SpringBootConfiguration
@ComponentScan(basePackages = "com.cwt.task.table")
@EnableVaadin
@PropertySource(value = "classpath:application.properties")
public class TableConfiguration {

    @Value("${spring.jooq.sql-dialect}")
    String sqlDialectName;

    @Value("${spring.datasource.driver-class-name}")
    String driverClassName;

    @Value("${spring.datasource.url}")
    String jdbcUrl;

    public TableConfiguration() {}

    @Bean
    public DefaultDSLContext dslContext(){
        return new DefaultDSLContext(jooqConfiguration());
    }

    @Bean
    public DefaultConfiguration jooqConfiguration() {
        DefaultConfiguration jooqConfig = new DefaultConfiguration();
        jooqConfig.set(connectionProvider());
        jooqConfig.set(new DefaultExecuteListenerProvider(
                jooqToSpringExceptionTransformer()));
        SQLDialect dialect = SQLDialect.valueOf(sqlDialectName);
        jooqConfig.set(dialect);
        return jooqConfig;
    }

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(transactionAwareDatasource());
    }

    @Bean
    public TransactionAwareDataSourceProxy transactionAwareDatasource() {
        return new TransactionAwareDataSourceProxy(lazyConnectionDataSource());
    }

    @Bean
    public LazyConnectionDataSourceProxy lazyConnectionDataSource() {
        return new LazyConnectionDataSourceProxy(dataSource());
    }
    @Bean
    public DataSource dataSource(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(jdbcUrl);
        return new HikariDataSource(config);
    }

    @Bean
    public JOOQToSpringExceptionTransformer jooqToSpringExceptionTransformer() {
        return new JOOQToSpringExceptionTransformer();
    }

}
