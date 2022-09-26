package com.cwt.task.table.adaptation;

import com.cwt.task.table.jooq.entity.tables.records.RegulardataRecord;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

@JsonAutoDetect
public class RegulardataRecordAdapterForJson {
    private String name;
    private String comment;
    private Integer amount;

    @JsonIgnore
    private LocalDateTime created;
    @JsonIgnore
    private LocalDateTime updated;

    public RegulardataRecordAdapterForJson(RegulardataRecord sourceRecord) {
        name = sourceRecord.getName();
        comment = sourceRecord.getComment();
        amount = sourceRecord.getAmount();
        created = sourceRecord.getCreated();
        updated = sourceRecord.getUpdated();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "RegulardataRecordAdapterForJson{" +
                "name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", amount=" + amount +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
