package com.cwt.task.table.service;

import com.cwt.task.table.dao.adapter.RegulardataRecordAdapter;
import com.cwt.task.table.jooq.entity.tables.records.RegulardataRecord;

import java.util.List;

public interface TableService {

   List<RegulardataRecordAdapter> getAllRegularDataRecords();

    void saveRegularDataRecord(RegulardataRecordAdapter record);

    void deleteRegulardataRecord(RegulardataRecordAdapter regulardataRecordAdapter);

    void updateRegularDataRecord(RegulardataRecordAdapter record);
}
