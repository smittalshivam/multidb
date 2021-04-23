package com.example.multidb2;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.LinkedHashMap;

@Service
public class ConnectionPool {

    private LinkedHashMap<String, DataSource> dataSourceMap = new LinkedHashMap<>();

    public LinkedHashMap<String, DataSource> getDataSourceMap() {
        return dataSourceMap;
    }

    public void setDataSourceMap(LinkedHashMap<String, DataSource> dataSourceMap) {
        this.dataSourceMap = dataSourceMap;
    }

    public DataSource getDs(String type) {
        return dataSourceMap.get(type);
    }
}
