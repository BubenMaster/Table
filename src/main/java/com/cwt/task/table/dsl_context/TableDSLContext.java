package com.cwt.task.table.dsl_context;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.SQLDialect;
import org.jooq.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan({"com.cwt.task.table"})
@EnableTransactionManagement
@PropertySource(value = "classpath:application.properties")
public class TableDSLContext {

    @Autowired
    private Environment env;


    @Bean
    public DefaultDSLContext dslContext(){
        return new DefaultDSLContext(jooqConfiguration());

    }

    @Bean
    public DefaultConfiguration jooqConfiguration() {
        DefaultConfiguration jooqConfig = new DefaultConfiguration();
        jooqConfig.setConnectionProvider(connectionProvider());

        jooqConfig.set(connectionProvider());
        jooqConfig.set(new DefaultExecuteListenerProvider(
                jooqToSpringExceptionTransformer()));

        String sqlDialectName = env.getProperty("spring.jooq.sql-dialect");
        SQLDialect dialect = SQLDialect.valueOf(sqlDialectName);
        jooqConfig.set(dialect);

        return jooqConfig;
    }

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(transactionAwareDatasource());
    }

    private TransactionAwareDataSourceProxy transactionAwareDatasource() {
        return new TransactionAwareDataSourceProxy(lazyConnectionDataSource());
    }

    private LazyConnectionDataSourceProxy lazyConnectionDataSource() {
        return new LazyConnectionDataSourceProxy(dataSource());
    }
    @Bean()
    public DataSource dataSource(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        config.setJdbcUrl("jdbc:sqlite:database\\table.db");
        HikariDataSource dSource = new HikariDataSource(config);
        return dSource;
    }

    @Bean
    public JOOQToSpringExceptionTransformer jooqToSpringExceptionTransformer() {
        return new JOOQToSpringExceptionTransformer();
    }




}
