package com.cwt.task.table.adaptation;

import com.cwt.task.table.jooq.entity.tables.records.RegulardataRecord;

import java.time.LocalDateTime;
import java.util.Objects;


public class RegulardataRecordAdapter {

    @SuppressWarnings("FieldMayBeFinal")
    private RegulardataRecord record;

    private String name;
    private String comment;
    private Integer amount;


    public RegulardataRecordAdapter(RegulardataRecord record) {
        this.record = record;
    }

    public Integer Id() {
        return this.record.getId();
    }

    public void Id(Integer value){
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

    @Override
    public String toString() {
        return "Record is: {" +
                " " + record.getId() +
                " " + record.getName() +
                " " + record.getComment() +
                " " + record.getAmount() +
                " " + record.getUpdated() +
                " " + record.getCreated() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegulardataRecordAdapter that = (RegulardataRecordAdapter) o;
        return Objects.equals(this.getName(), that.getName())
                && Objects.equals(this.getComment(), that.getComment())
                && Objects.equals(this.getAmount(), that.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getComment(), getAmount());
    }
}
