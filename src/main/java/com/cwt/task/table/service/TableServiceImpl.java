package com.cwt.task.table.service;

import com.cwt.task.table.dao.TableDAO;
import com.cwt.task.table.entity_adapter.RegulardataRecordAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TableServiceImpl implements TableService {

    @Autowired
    TableDAO tableDAO;

    public TableServiceImpl() {
    }

    @Override
    @Transactional
    public List<RegulardataRecordAdapter> getAllRegularDataRecords() {
        return tableDAO.getAllRegularDataRecords();
    }

    @Override
    @Transactional
    public void saveRegularDataRecord(RegulardataRecordAdapter record) {
        tableDAO.saveRegularDataRecord(record.actualRegularDataRecord());
    }

    @Override
    @Transactional
    public void deleteRegulardataRecord(RegulardataRecordAdapter record) {
        tableDAO.deleteByIdRegulardataRecord(record.actualRegularDataRecord());
    }

    @Override
    public void updateRegularDataRecord(RegulardataRecordAdapter record) {
        tableDAO.updateRegularDataRecord(record.actualRegularDataRecord());
    }
}
