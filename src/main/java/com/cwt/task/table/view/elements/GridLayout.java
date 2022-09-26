package com.cwt.task.table.view.elements;

import com.cwt.task.table.adaptation.RegulardataRecordAdapter;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringComponent
public class GridLayout extends HorizontalLayout {

    RecordsGrid recordsGrid = new RecordsGrid(RegulardataRecordAdapter.class);
    RecordGridContextMenuContainer contextMenu;

    public GridLayout() {
        super();
    }

    @PostConstruct
    public void init(){
        setAlignItems(Alignment.CENTER);
        contextMenu = new RecordGridContextMenuContainer(recordsGrid.addContextMenu());
        add(recordsGrid, contextMenu.getRecordGridContextMenu());
    }

    @Bean("recordsGridOnView")
    public RecordsGrid recordsGridOnView(){
        return recordsGrid;
    }

    @Bean
    public RecordGridContextMenuContainer contextMenu(){
        return contextMenu;
    }
}
