package com.cwt.task.table.configuration.properties;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ApplicationProperties extends Properties {

    public ApplicationProperties(String fileName) throws IOException {
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(fileName)).getPath();
        this.load(new FileReader((rootPath)));
    }

    public ApplicationProperties() {
    }

    public ApplicationProperties(int initialCapacity) {
        super(initialCapacity);
    }

    public ApplicationProperties(Properties defaults) {
        super(defaults);
    }
}