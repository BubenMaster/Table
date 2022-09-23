package com.cwt.task.table.dao;

import com.cwt.task.table.dao.adapter.RegulardataRecordAdapter;
import com.cwt.task.table.configuration.TableConfiguration;
import com.cwt.task.table.jooq.entity.tables.records.RegulardataRecord;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import static com.cwt.task.table.jooq.entity.Tables.*;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TableDAOImpl implements TableDAO {

    @Autowired
    DefaultDSLContext query = new TableConfiguration().dslContext();


    public TableDAOImpl(){
    }

    @Override
    public List<RegulardataRecordAdapter> getAllRegularDataRecords() {
        Result<Record> result = query
                .select()
                .from(REGULARDATA).fetch();
        List<RegulardataRecordAdapter> records = new ArrayList<>();
        for (Record record: result){
            RegulardataRecordAdapter recordAdapter = new RegulardataRecordAdapter((RegulardataRecord) record);
            records.add(recordAdapter);
        }
        return records;
    }
}
