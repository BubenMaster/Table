package com.cwt.task.table.configuration;

import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultExecuteListener;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;
import org.springframework.stereotype.Component;


@Component
public class JOOQToSpringExceptionTransformer extends DefaultExecuteListener {

    @SuppressWarnings("ConstantConditions")
    @Override
    public void exception(ExecuteContext ctx) {
        SQLDialect dialect = ctx.configuration().dialect();
        SQLExceptionTranslator translator = (dialect != null)
                ? new SQLErrorCodeSQLExceptionTranslator(dialect.name())
                : new SQLStateSQLExceptionTranslator();

        //noinspection SqlNoDataSourceInspection,SqlDialectInspection,ConstantConditions
        ctx.exception(translator.translate("jOOQ", ctx.sql(), ctx.sqlException()));
    }
}
