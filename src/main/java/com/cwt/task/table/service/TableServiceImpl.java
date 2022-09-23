package com.cwt.task.table.service;

import com.cwt.task.table.dao.TableDAO;
import com.cwt.task.table.dao.TableDAOImpl;
import com.cwt.task.table.dao.adapter.RegulardataRecordAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class TableServiceImpl implements TableService {

    @Autowired
    TableDAO tableDAO = new TableDAOImpl();

    public TableServiceImpl() {
    }

    @Override
    @Transactional
    public List<RegulardataRecordAdapter> getAllRegularDataRecords() {
        return tableDAO.getAllRegularDataRecords();
    }
}
