package com.example.multidb2;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication
public class Multidb2Application {

//	@Autowired
//	ConnectionPool connectionPool;
//
//	public LinkedHashMap<String,Object> j() throws Exception {
//		File[] files = Paths.get("tenants").toFile().listFiles();
//		LinkedHashMap<String, Object> resolvedDataSources = new LinkedHashMap<>();
//
//		for (File propertyFile : files) {
//			Properties tenantProperties = new Properties();
//			HikariDataSource hikariDataSource = new HikariDataSource();
//
//			try {
//				tenantProperties.load(new FileInputStream(propertyFile));
//
//				String tenantId = tenantProperties.getProperty("name");
//
//				hikariDataSource.setJdbcUrl(tenantProperties.getProperty("datasource.url"));
//				hikariDataSource.setUsername(tenantProperties.getProperty("datasource.username"));
//				hikariDataSource.setPassword(tenantProperties.getProperty("datasource.password"));
//				String schema = tenantProperties.getProperty("datasource.schema");
//				if(schema!=null && !schema.isEmpty())
//					hikariDataSource.setSchema(schema);
//
//				resolvedDataSources.put(tenantId, hikariDataSource);
//
//
//			} catch (IOException e) {
//				throw new Exception(e.getMessage());
//			}
//		}
//		return resolvedDataSources;
//	}



	public static void main(String[] args) {
		SpringApplication.run(Multidb2Application.class, args);
	}

}
