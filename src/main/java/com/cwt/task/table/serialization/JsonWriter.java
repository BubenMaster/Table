package com.cwt.task.table.serialization;

import com.cwt.task.table.adaptation.RegulardataRecordAdapterForJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.spring.annotation.SpringComponent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.List;

@SpringComponent
public class JsonWriter {
    public String createJsonFileOf(List<RegulardataRecordAdapterForJson> records,String localFileName) {
        Writer writer;
        ObjectMapper mapper = new ObjectMapper();


        File localFile = new File(localFileName);
        System.out.println(localFile.getAbsolutePath());

        try {
            writer = new FileWriter(localFile);
            mapper.writeValue(writer, records);
            System.out.println("written to file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return localFile.getAbsolutePath();
    }
}
