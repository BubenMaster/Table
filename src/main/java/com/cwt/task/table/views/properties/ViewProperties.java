package com.cwt.task.table.views.properties;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;


public class ViewProperties extends Properties {

    public ViewProperties(String fileName) {
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(fileName)).getPath();
        try {
            this.load(new FileReader((rootPath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public ViewProperties() {
//        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("view.properties")).getPath();
//        try {
//            this.load(new FileReader((rootPath)));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }


}
