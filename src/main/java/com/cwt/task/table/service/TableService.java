package com.cwt.task.table.service;

import com.cwt.task.table.adaptation.RegulardataRecordAdapter;

import java.util.List;

public interface TableService {

   List<RegulardataRecordAdapter> getAllRegularDataRecords();

    void saveRegularDataRecord(RegulardataRecordAdapter record);

    void deleteRegulardataRecord(RegulardataRecordAdapter regulardataRecordAdapter);

    void updateRegularDataRecord(RegulardataRecordAdapter record);

    String readDataToLocalFile(String localFileName);
}
