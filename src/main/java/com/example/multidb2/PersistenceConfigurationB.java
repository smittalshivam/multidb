package com.example.multidb2;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//@Configuration
//@EnableTransactionManagement
public class PersistenceConfigurationB {
//    private final String PACKAGE_SCAN = "com.example.multidb2";

//    @Bean(name = "clientBDataSource")
//    public static DataSource clientBDataSource() {
//        HikariDataSource hikariDataSource = new HikariDataSource();
//        hikariDataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
//        hikariDataSource.setUsername("postgres");
//        hikariDataSource.setPassword("admin");
//        hikariDataSource.setSchema("test2");
//        return hikariDataSource;
//    }
//
//    @Bean(name = "entityManagerClientB")
//    public LocalContainerEntityManagerFactoryBean multiEntityManager2() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(clientBDataSource());
//        em.setPackagesToScan(PACKAGE_SCAN);
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//        em.setJpaProperties(hibernateProperties());
//        return em;
//    }
//
//    @Bean(name = "transactionManagerClientB")
//    public PlatformTransactionManager multiTransactionManager1() {
//        JpaTransactionManager transactionManager
//                = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(
//                multiEntityManager2().getObject());
//        return transactionManager;
//    }
//
//    @Bean(name = "dbSessionFactoryCLientB")
//    public LocalSessionFactoryBean dbSessionFactory() {
//        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//        sessionFactoryBean.setDataSource(clientBDataSource());
//        sessionFactoryBean.setPackagesToScan(PACKAGE_SCAN);
//        sessionFactoryBean.setHibernateProperties(hibernateProperties());
//        return sessionFactoryBean;
//    }

//    private Properties hibernateProperties() {
//        Properties properties = new Properties();
//        properties.put("hibernate.show_sql", true);
//        properties.put("hibernate.format_sql", true);
//        properties.put("hibernate.hbm2ddl.auto", "update");
//        properties.put("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");
//        return properties;
//    }
}