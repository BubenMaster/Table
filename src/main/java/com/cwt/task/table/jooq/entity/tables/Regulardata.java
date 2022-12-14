/*
 * This file is generated by jOOQ.
 */
package com.cwt.task.table.jooq.entity.tables;


import com.cwt.task.table.jooq.entity.DefaultSchema;
import com.cwt.task.table.jooq.entity.tables.records.RegulardataRecord;
import org.jooq.Record;
import org.jooq.*;
import com.cwt.task.table.jooq.entity.Keys;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Regulardata extends TableImpl<RegulardataRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>regulardata</code>
     */
    public static final Regulardata REGULARDATA = new Regulardata();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RegulardataRecord> getRecordType() {
        return RegulardataRecord.class;
    }

    /**
     * The column <code>regulardata.id</code>.
     */
    public final TableField<RegulardataRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>regulardata.name</code>.
     */
    public final TableField<RegulardataRecord, String> NAME = createField(DSL.name("name"), SQLDataType.CHAR(1), this, "");

    /**
     * The column <code>regulardata.comment</code>.
     */
    public final TableField<RegulardataRecord, String> COMMENT = createField(DSL.name("comment"), SQLDataType.CHAR, this, "");

    /**
     * The column <code>regulardata.amount</code>.
     */
    public final TableField<RegulardataRecord, Integer> AMOUNT = createField(DSL.name("amount"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.field("1", SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>regulardata.created</code>.
     */
    public final TableField<RegulardataRecord, LocalDateTime> CREATED = createField(DSL.name("created"), SQLDataType.LOCALDATETIME(0), this, "");

    /**
     * The column <code>regulardata.updated</code>.
     */
    public final TableField<RegulardataRecord, LocalDateTime> UPDATED = createField(DSL.name("updated"), SQLDataType.LOCALDATETIME(0), this, "");

    private Regulardata(Name alias, Table<RegulardataRecord> aliased) {
        this(alias, aliased, null);
    }

    private Regulardata(Name alias, Table<RegulardataRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>regulardata</code> table reference
     */
    public Regulardata(String alias) {
        this(DSL.name(alias), REGULARDATA);
    }

    /**
     * Create an aliased <code>regulardata</code> table reference
     */
    public Regulardata(Name alias) {
        this(alias, REGULARDATA);
    }

    /**
     * Create a <code>regulardata</code> table reference
     */
    public Regulardata() {
        this(DSL.name("regulardata"), null);
    }

    public <O extends Record> Regulardata(Table<O> child, ForeignKey<O, RegulardataRecord> key) {
        super(child, key, REGULARDATA);
    }

    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<RegulardataRecord, Integer> getIdentity() {
        return (Identity<RegulardataRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<RegulardataRecord> getPrimaryKey() {
        return Keys.PK_REGULARDATA;
    }

    @Override
    public List<UniqueKey<RegulardataRecord>> getKeys() {
        return Arrays.<UniqueKey<RegulardataRecord>>asList(Keys.PK_REGULARDATA, Keys.SQLITE_AUTOINDEX_REGULARDATA_1);
    }

    @Override
    public Regulardata as(String alias) {
        return new Regulardata(DSL.name(alias), this);
    }

    @Override
    public Regulardata as(Name alias) {
        return new Regulardata(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Regulardata rename(String name) {
        return new Regulardata(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Regulardata rename(Name name) {
        return new Regulardata(name, null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<Integer, String, String, Integer, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row6) super.fieldsRow();
    }
}
