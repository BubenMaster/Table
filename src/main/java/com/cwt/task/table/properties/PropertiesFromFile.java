package com.cwt.task.table.properties;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class PropertiesFromFile {
    Properties properties;
    public PropertiesFromFile(String fileName) throws IOException {
        properties = new Properties();
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(fileName)).getPath();
        properties.load(new FileReader((rootPath)));
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }
}