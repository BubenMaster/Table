package com.cwt.task.table.view.grid;

import com.cwt.task.table.dao.adapter.RegulardataRecordAdapter;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@SpringComponent
public class GridLayout extends HorizontalLayout {

    @Autowired
    Grid<RegulardataRecordAdapter> gridOnLayout;

    RecordContextMenu recordContextMenu;

    public GridLayout() {
        super();
    }

    @Bean
    public Grid<RegulardataRecordAdapter> gridOnView(){
        setAlignItems(Alignment.CENTER);
        add(gridOnLayout);
        return this.gridOnLayout;
    }

}
