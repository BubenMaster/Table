package com.cwt.task.table.dao;

import com.cwt.task.table.dao.adapter.RegulardataRecordAdapter;
import com.cwt.task.table.jooq.entity.tables.records.RegulardataRecord;

import java.util.List;

public interface TableDAO {


    List<RegulardataRecordAdapter> getAllRegularDataRecords();

    void saveRegularDataRecord(RegulardataRecord record);

    void deleteByIdRegulardataRecord(RegulardataRecord actualRegularDataRecord);
}
