package com.cwt.task.table.view.grid;

import com.cwt.task.table.dao.adapter.RegulardataRecordAdapter;
import com.cwt.task.table.view.grid.order.ColumnOrder;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Bean;

import static com.cwt.task.table.view.grid.order.ColumnKeys.*;
import static com.cwt.task.table.view.grid.order.ColumnKeys.created;

@SpringComponent
public class RecordsGrid{

    Grid<RegulardataRecordAdapter> grid = new Grid<>(RegulardataRecordAdapter.class);

    public RecordsGrid() {
        grid.addClassNames("table-grid");
        grid.setWidth("60em");
        grid.addContextMenu();
        ColumnOrder<RegulardataRecordAdapter> columnOrder = new ColumnOrder<>();
        grid.setColumnOrder(
                columnOrder.of(grid).byNames
                        (id(),name(),comment(),amount(),created(),updated())
        );
        grid.getColumnByKey(id()).setWidth("5em");
        grid.getColumnByKey(amount()).setWidth("5em");
        grid.getColumnByKey(updated()).setWidth("11em");
        grid.getColumnByKey(created()).setWidth("11em");
    }

    @Bean
    public Grid<RegulardataRecordAdapter> gridOnLayout(){
        return this.grid;
    }
}
