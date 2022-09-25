package com.cwt.task.table.view.grid;

import com.cwt.task.table.dao.adapter.RegulardataRecordAdapter;
import com.cwt.task.table.service.TableService;
import com.cwt.task.table.view.grid.order.ColumnOrder;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

import java.util.List;

import static com.cwt.task.table.view.grid.order.ColumnKeys.*;
import static com.cwt.task.table.view.grid.order.ColumnKeys.created;


public class RecordsGrid extends Grid<RegulardataRecordAdapter>{

    public RecordsGrid(Class<RegulardataRecordAdapter> regulardataRecordAdapterClass) {
        super(regulardataRecordAdapterClass);
    }

    @PostConstruct
    public void init() {
        addClassNames("table-grid");
        setWidth("60em");

        ColumnOrder<RegulardataRecordAdapter> columnOrder = new ColumnOrder<>();
        setColumnOrder(
            columnOrder.of(this).byNames
                (id(),name(),comment(),amount(),created(),updated())
            );



        getColumnByKey(id()).setWidth("5em");
        getColumnByKey(amount()).setWidth("5em");
        getColumnByKey(updated()).setWidth("11em");
        getColumnByKey(created()).setWidth("11em");
    }

//    @Bean("recordsGridForLayout")
//    public Grid<RegulardataRecordAdapter> recordsGrid(){
//        return this;
//    }

    public void refreshRecords(TableService service){
        List<RegulardataRecordAdapter> records = useServiceToGetAllRecords(service);
        this.setItems(records);
    }

    private List<RegulardataRecordAdapter> useServiceToGetAllRecords(TableService service){
        return service.getAllRegularDataRecords();
    }




}
