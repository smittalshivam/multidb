package com.example.multidb2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.context.support.GenericWebApplicationContext;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.multidb2",
        entityManagerFactoryRef = "multiEntityManager",
        transactionManagerRef = "multiTransactionManager"
)
public class PersistenceConfiguration {

    @Value("${db.details.file.path}")
    private String dbDetailsFilePath;

    private final String PACKAGE_SCAN = "com.example.multidb2";

    @Autowired
    private GenericWebApplicationContext context;


    public LinkedHashMap<String, ConnectionPool> getDatasourceMap() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String details = new String(Files.readAllBytes(Paths.get(dbDetailsFilePath)));
        LinkedHashMap<String, DBDetail> dbDetails = mapper.readValue(details, new TypeReference<LinkedHashMap<String, DBDetail>>() {
        });
        LinkedHashMap<String, ConnectionPool> connectionPools = new LinkedHashMap<>();
        Set<Map.Entry<String, DBDetail>> entrySet = dbDetails.entrySet();

        for (Map.Entry<String, DBDetail> entry : entrySet) {

            DBDetail dbDetail = entry.getValue();

            ConnectionPool connectionPool = new ConnectionPool();
            HikariDataSource hikariDataSource = new HikariDataSource();

            String tenantId = entry.getKey();
            String url = dbDetail.getDatasourceUrl();
            String userName = dbDetail.getDatasourceUsername();
            String password = dbDetail.getDatasourcePassword();
            String schema = dbDetail.getSchemaName();

            Map<String, String> properties = dbDetail.getHibernateProperties();

            boolean isPrimary;
            if (tenantId.equals("MAIN"))
                isPrimary = true;
            else
                isPrimary = false;

            hikariDataSource.setJdbcUrl(url);
            hikariDataSource.setUsername(userName);
            hikariDataSource.setPassword(password);

            if (schema != null && !schema.isEmpty())
                hikariDataSource.setSchema(schema);


            Properties props = new Properties();
            props.putAll(properties);

            String beanName = tenantId + "datasource";

            context.registerBean(beanName, DataSource.class, connectionPool::getDataSource, bd -> bd.setPrimary(isPrimary));

            connectionPool.setDataSource(hikariDataSource);
            connectionPool.setTenantId(tenantId);
            connectionPool.setProperties(props);
            connectionPool.setPrimary(isPrimary);

            connectionPools.put(tenantId, connectionPool);
        }

        if (connectionPools.get("MAIN")==null)
            throw new Exception("No main datasource found");

        return connectionPools;
    }


    @Bean
    @PostConstruct
    public LinkedHashMap<String, ConnectionPool> postConstruct() throws Exception {
        LinkedHashMap<String, ConnectionPool> connectionPoolLinkedHashMap = this.getDatasourceMap();
        generateBeansForDiffDs(connectionPoolLinkedHashMap);
        return connectionPoolLinkedHashMap;
    }


    @Bean(name = "multiRoutingDataSource")
    public DataSource multiRoutingDataSource(LinkedHashMap<String, ConnectionPool> connectionPools) {

        Set<Map.Entry<String, ConnectionPool>> entrySet = connectionPools.entrySet();

        HikariDataSource mainDataSource = null;

        Map<Object, Object> targetDataSources = new HashMap<>();

        for (Map.Entry<String, ConnectionPool> entry : entrySet) {
            if (entry.getKey().equalsIgnoreCase("MAIN")) {
                mainDataSource = (HikariDataSource) entry.getValue().getDataSource();
                targetDataSources.put(entry.getKey(), mainDataSource);
            }
            targetDataSources.put(entry.getKey(), entry.getValue().getDataSource());
        }

        MultiRoutingDataSource multiRoutingDataSource = new MultiRoutingDataSource();
        multiRoutingDataSource.setDefaultTargetDataSource(mainDataSource);
        multiRoutingDataSource.setTargetDataSources(targetDataSources);

        return multiRoutingDataSource;
    }

    @Bean(name = "multiEntityManager")
    public LocalContainerEntityManagerFactoryBean multiEntityManager(LinkedHashMap<String, ConnectionPool> connectionPools) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(multiRoutingDataSource(connectionPools));
        em.setPackagesToScan(PACKAGE_SCAN);
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        ConnectionPool connectionPool = connectionPools.get("MAIN");
        em.setJpaProperties(connectionPool.getProperties());
        return em;
    }

    @Primary
    @Bean(name = "multiTransactionManager")
    public PlatformTransactionManager multiTransactionManager(LinkedHashMap<String, ConnectionPool> connectionPools) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(multiEntityManager(connectionPools).getObject());
        return transactionManager;
    }

    @Primary
    @Bean(name = "dbSessionFactory")
    public LocalSessionFactoryBean dbSessionFactory(LinkedHashMap<String, ConnectionPool> connectionPools) {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(multiRoutingDataSource(connectionPools));
        sessionFactoryBean.setPackagesToScan(PACKAGE_SCAN);
        ConnectionPool connectionPool = connectionPools.get("MAIN");
        sessionFactoryBean.setHibernateProperties(connectionPool.getProperties());
        return sessionFactoryBean;
    }

    public void generateBeansForDiffDs(LinkedHashMap<String, ConnectionPool> connectionPoolLinkedHashMap) {
        Set<Map.Entry<String, ConnectionPool>> entrySet = connectionPoolLinkedHashMap.entrySet();
        for (Map.Entry<String, ConnectionPool> entry : entrySet) {
            if (!entry.getKey().equals("MAIN")) {
                ConnectionPool connectionPool = entry.getValue();

                LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
                em.setDataSource(entry.getValue().getDataSource());
                em.setPackagesToScan(PACKAGE_SCAN);
                HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
                em.setJpaVendorAdapter(vendorAdapter);
                em.setJpaProperties(connectionPool.getProperties());

                JpaTransactionManager transactionManager = new JpaTransactionManager();
                transactionManager.setEntityManagerFactory(em.getObject());

                LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
                sessionFactoryBean.setDataSource(entry.getValue().getDataSource());
                sessionFactoryBean.setPackagesToScan(PACKAGE_SCAN);
                sessionFactoryBean.setHibernateProperties(connectionPool.getProperties());

                context.registerBean("entityManager" + entry.getKey(), LocalContainerEntityManagerFactoryBean.class, () -> em);
                context.registerBean("transactionManager" + entry.getKey(), PlatformTransactionManager.class, () -> transactionManager);
                context.registerBean("sessionFactory" + entry.getKey(), LocalSessionFactoryBean.class, () -> sessionFactoryBean);

            }
        }
    }

}