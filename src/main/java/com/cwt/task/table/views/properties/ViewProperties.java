package com.cwt.task.table.views.properties;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ViewProperties extends Properties {

    public ViewProperties(String fileName) throws IOException {
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(fileName)).getPath();
        this.load(new FileReader((rootPath)));
    }

    public ViewProperties() {
    }

    public ViewProperties(int initialCapacity) {
        super(initialCapacity);
    }

    public ViewProperties(Properties defaults) {
        super(defaults);
    }


}
