package com.cwt.task.table.dao.adapter;

import com.cwt.task.table.jooq.entity.tables.records.RegulardataRecord;

import java.time.LocalDateTime;

public class RegulardataRecordAdapter {

    private RegulardataRecord record;

    public RegulardataRecordAdapter(RegulardataRecord record) {
        this.record = record;
    }

    public Integer getId() {
        return this.record.getId();
    }

    public void setId(Integer value){
        this.record.setId(value);
    }

    public String getName() {
        return this.record.getName();
    }
    public void setName(String value) {
        this.record.setName(value);
    }

    public String getComment() {
        return this.record.getComment();
    }

    public void setComment(String value) {
        this.record.setComment(value);
    }

    public Integer getAmount() {
        return this.record.getAmount();
    }

    public void setAmount(Integer value) {
        this.record.setAmount(value);
    }


    public LocalDateTime getUpdated() {
        return this.record.getUpdated();
    }

    public LocalDateTime getCreated() {
        return this.record.getCreated();
    }

    public RegulardataRecord actualRegularDataRecord() {
        return record;
    }
}
