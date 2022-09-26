package com.cwt.task.table.view.elements.order;

import com.cwt.task.table.jooq.entity.Tables;

public class ColumnKeys {

    public static String id(){
        return Tables.REGULARDATA.ID.getName();
    }
    public static String name(){ return Tables.REGULARDATA.NAME.getName();}
    public static String comment(){
        return Tables.REGULARDATA.COMMENT.getName();
    }
    public static String amount(){
        return Tables.REGULARDATA.AMOUNT.getName();
    }
    public static String created(){
        return Tables.REGULARDATA.CREATED.getName();
    }
    public static String updated(){
        return Tables.REGULARDATA.UPDATED.getName();
    }
}
