package com.cwt.task.table.service;

import com.cwt.task.table.dao.adapter.RegulardataRecordAdapter;

import java.util.List;

public interface TableService {

   List<RegulardataRecordAdapter> getAllRegularDataRecords();
}
