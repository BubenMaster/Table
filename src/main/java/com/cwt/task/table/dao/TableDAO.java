package com.cwt.task.table.dao;

import com.cwt.task.table.dao.adapter.RegulardataRecordAdapter;

import java.util.List;

public interface TableDAO {


    List<RegulardataRecordAdapter> getAllRegularDataRecords();
}
