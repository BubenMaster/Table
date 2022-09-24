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
import static org.jooq.impl.DSL.currentTimestamp;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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

    @Override
    public void saveRegularDataRecord(RegulardataRecord record) {
        ZoneOffset offset = ZoneId.systemDefault().getRules().getOffset(Instant.now());
        LocalDateTime localTime =((Timestamp) query.select(currentTimestamp()).fetch().getValue(0,0))
        .toLocalDateTime().plusSeconds(offset.getTotalSeconds());
        record.setCreated(localTime);
        record.setUpdated(localTime);
        query.insertInto(REGULARDATA).set(record).execute();
    }
}