package com.example.multidb2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Properties;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionPool {
    private DataSource dataSource;
    private String tenantId;
    private Properties properties;
    private boolean isPrimary;
}
