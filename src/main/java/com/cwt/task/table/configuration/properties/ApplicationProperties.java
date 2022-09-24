package com.cwt.task.table.configuration.properties;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ApplicationProperties {
    Properties properties;
    public ApplicationProperties(String fileName) throws IOException {
        properties = new Properties();
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(fileName)).getPath();
        properties.load(new FileReader((rootPath)));
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }
}