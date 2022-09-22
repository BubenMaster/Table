package com.cwt.task.table.jooq.entity.adapter;

import com.cwt.task.table.jooq.entity.tables.records.RegulardataRecord;

import java.time.LocalDateTime;

public class RegulardataRecordAdapter {

    private RegulardataRecord record;

    public RegulardataRecordAdapter(RegulardataRecord record) {
        this.record = record;
    }

    public String id() {
        return this.record.getName();
    }

    public void setId(int value){
        this.record.setId(value);
    }

    public void setName(String value) {
        this.record.setName(value);
    }

    public String getName() {
        return this.record.getName();
    }

    public void setComment(String value) {
        this.record.setComment(value);
    }

    public String getComment() {
        return this.record.getComment();
    }

    public void setAmount(int value) {
        this.record.setAmount(value);
    }

    public int getAmount() {
        return this.record.getAmount();
    }

    public LocalDateTime getUpdated() {
        return this.record.getUpdated();
    }

    public LocalDateTime getCreated() {
        return this.record.getCreated();
    }

    public RegulardataRecord record() {
        return record;
    }
}
