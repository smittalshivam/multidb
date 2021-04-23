package com.example.multidb2;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//@Configuration
//@EnableTransactionManagement
public class PersistenceConfigurationA {
//    private final String PACKAGE_SCAN = "com.example.multidb2";
//
//    @Bean(name = "clientADataSource")
//    public static DataSource clientADataSource() {
//        HikariDataSource hikariDataSource = new HikariDataSource();
//        hikariDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/multi_client_a?useSSL=false");
//        hikariDataSource.setUsername("admin");
//        hikariDataSource.setPassword("Admin@123");
//        return hikariDataSource;
//    }
//
//    @Bean(name = "entityManagerClientA")
//    public LocalContainerEntityManagerFactoryBean multiEntityManager2() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(clientADataSource());
//        em.setPackagesToScan(PACKAGE_SCAN);
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//        em.setJpaProperties(hibernateProperties());
//        return em;
//    }
//
//    @Bean(name = "transactionManagerClientA")
//    public PlatformTransactionManager multiTransactionManager1() {
//        JpaTransactionManager transactionManager
//                = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(
//                multiEntityManager2().getObject());
//        return transactionManager;
//    }
//
//    @Bean(name = "dbSessionFactoryClientA")
//    public LocalSessionFactoryBean dbSessionFactory() {
//        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//        sessionFactoryBean.setDataSource(clientADataSource());
//        sessionFactoryBean.setPackagesToScan(PACKAGE_SCAN);
//        sessionFactoryBean.setHibernateProperties(hibernateProperties());
//        return sessionFactoryBean;
//    }
//
//    private Properties hibernateProperties() {
//        Properties properties = new Properties();
//        properties.put("hibernate.show_sql", true);
//        properties.put("hibernate.format_sql", true);
//        properties.put("hibernate.hbm2ddl.auto", "update");
//        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
//        return properties;
//    }
}