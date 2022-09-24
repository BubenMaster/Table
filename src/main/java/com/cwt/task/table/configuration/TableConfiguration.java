package com.cwt.task.table.configuration;

import com.cwt.task.table.configuration.properties.ApplicationProperties;
import com.cwt.task.table.views.properties.ViewProperties;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.SQLDialect;
import org.jooq.impl.*;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.io.IOException;


@SpringBootConfiguration
@ComponentScan(basePackages = "com.cwt.task.table")
@EnableVaadin
@EnableTransactionManagement
@PropertySource(value = "classpath:application.properties")
public class TableConfiguration {

//    @Autowired
    private final ApplicationProperties env;

    {
        try {
            env = new ApplicationProperties("application.properties");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TableConfiguration() {
    }


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
    @Bean
    public DataSource dataSource(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        config.setJdbcUrl("jdbc:sqlite:database\\table.db"); // TODO: 22.09.2022 Implement reading from properties
        HikariDataSource dSource = new HikariDataSource(config);

        return dSource;
    }

    @Bean
    public JOOQToSpringExceptionTransformer jooqToSpringExceptionTransformer() {
        return new JOOQToSpringExceptionTransformer();
    }

}
