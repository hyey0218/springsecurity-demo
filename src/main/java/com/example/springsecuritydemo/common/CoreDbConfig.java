package com.example.springsecuritydemo.common;

import com.example.springsecuritydemo.constants.JpaConstant;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.example.springsecuritydemo.repository",
        entityManagerFactoryRef = JpaConstant.CORE_EMF,
        transactionManagerRef = JpaConstant.CORE_TM)
public class CoreDbConfig {

    @Autowired
    private Environment env;

    @Bean(name = JpaConstant.CORE_DATA_SOURCE)
    @Qualifier(JpaConstant.CORE_DATA_SOURCE)
    @Primary
    @ConfigurationProperties(prefix = "core.datasource")
    public DataSource coreDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class).build();
    }

    protected Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.physical_naming_strategy", CustomNamingStrategy.class.getName());
        props.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        return props;
    }

    @Primary
    @Bean(name = JpaConstant.CORE_EMF)
    public LocalContainerEntityManagerFactoryBean coreEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(coreDataSource())
                .packages(".core")
                .persistenceUnit(JpaConstant.PERSISTENCE_UNIT_CORE)
                .properties(jpaProperties())
                .build();
    }

    @Primary
    @Bean(JpaConstant.CORE_TM)
    public PlatformTransactionManager coreTransactionManager(
            final @Qualifier("coreEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }

}
