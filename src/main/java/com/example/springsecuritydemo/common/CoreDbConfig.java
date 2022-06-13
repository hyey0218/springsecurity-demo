package com.example.springsecuritydemo.common;

import com.example.springsecuritydemo.constants.JpaConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
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

    protected Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<>();
//        props.put("hibernate.physical_naming_strategy", CustomNamingStrategy.class.getName());
        props.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        return props;
    }

}
