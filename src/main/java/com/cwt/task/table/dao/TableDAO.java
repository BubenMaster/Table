package com.cwt.task.table.dao;

import com.cwt.task.table.adaptation.RegulardataRecordAdapter;
import com.cwt.task.table.adaptation.RegulardataRecordAdapterForJson;
import com.cwt.task.table.jooq.entity.tables.records.RegulardataRecord;

import java.util.List;

public interface TableDAO {


    List<RegulardataRecordAdapter> getAllRegularDataRecords();

    void saveRegularDataRecord(RegulardataRecord record);

    void deleteByIdRegulardataRecord(RegulardataRecord actualRegularDataRecord);

    void updateRegularDataRecord(RegulardataRecord record);

    List<RegulardataRecordAdapterForJson> getAllRawRegularDataRecords();
}
