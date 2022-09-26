package com.cwt.task.table.adaptation;

import com.cwt.task.table.jooq.entity.tables.records.RegulardataRecord;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.jooq.Record;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;

@SpringComponent
public class Sorcerer {

    public List<RegulardataRecordAdapterForJson> castToRegulardataRecordAdapterForJson(Result<Record> rawRecords) {
        List<RegulardataRecordAdapterForJson> records = new ArrayList<>();
        for (Record record: rawRecords){

            records.add(new RegulardataRecordAdapterForJson((RegulardataRecord) record));

        }
        return records;
    }

    public List<RegulardataRecordAdapter> castToRegularDataRecordAdapterList(Result<Record> rawRecords) {
        List<RegulardataRecordAdapter> records = new ArrayList<>();
        for (Record record: rawRecords){
            RegulardataRecordAdapter recordAdapter = new RegulardataRecordAdapter((RegulardataRecord) record);
            records.add(recordAdapter);
        }
        return records;
    }
}
