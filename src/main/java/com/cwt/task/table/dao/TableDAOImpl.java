package com.cwt.task.table.dao;

import com.cwt.task.table.adaptation.RegulardataRecordAdapterForJson;
import com.cwt.task.table.adaptation.Sorcerer;
import com.cwt.task.table.adaptation.RegulardataRecordAdapter;
import com.cwt.task.table.jooq.entity.tables.records.RegulardataRecord;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.cwt.task.table.jooq.entity.Tables.*;
import static org.jooq.impl.DSL.currentTimestamp;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;

@Repository
public class TableDAOImpl implements TableDAO {

    @Autowired
    DefaultDSLContext dslContext;

    @Autowired
    Sorcerer sorcerer;


    public TableDAOImpl(){
    }

    @Override
    public List<RegulardataRecordAdapter> getAllRegularDataRecords() {
        Result<Record> rawRecords = dslContext
                .select()
                .from(REGULARDATA).fetch();
        return sorcerer.castToRegularDataRecordAdapterList(rawRecords);
    }

    @Override
    public List<RegulardataRecordAdapterForJson> getAllRawRegularDataRecords() {
        Result<Record> rawRecords = dslContext
                .select()
                .from(REGULARDATA).fetch();
        return sorcerer.castToRegulardataRecordAdapterForJson(rawRecords);
    }

    @Override
    public void saveRegularDataRecord(RegulardataRecord record) {
        record.setCreated(askLocalDateTimeFromDataBase());
        record.setUpdated(askLocalDateTimeFromDataBase());
        dslContext.insertInto(REGULARDATA).set(record).execute();
    }

    @Override
    public void deleteByIdRegulardataRecord(RegulardataRecord record) {
        dslContext.deleteFrom(REGULARDATA).where(REGULARDATA.ID.equal(record.getId())).execute();
    }

    @SuppressWarnings("resource")
    @Override
    public void updateRegularDataRecord(RegulardataRecord record) {
        record.setUpdated(askLocalDateTimeFromDataBase());
        dslContext.update(REGULARDATA).set(record).where(REGULARDATA.ID.equal(record.getId())).execute();
    }

    private LocalDateTime askLocalDateTimeFromDataBase(){
        ZoneOffset offset = ZoneId.systemDefault().getRules().getOffset(Instant.now());
        //noinspection UnnecessaryLocalVariable
        LocalDateTime localTime = ((Timestamp) Objects.requireNonNull(
                dslContext.select(currentTimestamp()).fetch().getValue(0, 0)))
                .toLocalDateTime()
                .plusSeconds(offset.getTotalSeconds());
        return localTime;
    }
}
