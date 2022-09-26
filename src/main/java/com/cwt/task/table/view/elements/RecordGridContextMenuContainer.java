package com.cwt.task.table.view.elements;

import com.cwt.task.table.adaptation.RegulardataRecordAdapter;
import com.cwt.task.table.jooq.entity.tables.records.RegulardataRecord;
import com.cwt.task.table.service.TableService;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.contextmenu.GridMenuItem;

import javax.annotation.PostConstruct;
import java.util.Optional;


public class RecordGridContextMenuContainer {

    private GridMenuItem<RegulardataRecordAdapter> deleteButton;

    private final GridContextMenu<RegulardataRecordAdapter> recordGridContextMenu;

    public RecordGridContextMenuContainer(GridContextMenu<RegulardataRecordAdapter> contextMenu) {
        recordGridContextMenu = contextMenu;
    }

    @PostConstruct
    public void init(){
     deleteButton = recordGridContextMenu.addItem("Delete");
    }

    public void deleteButtonClickListener(TableService service, RecordsGrid recordsGrid){
        deleteButton.addMenuItemClickListener( event -> {
                    prepareRecordForService(service, event.getItem());
                    refreshParentRecords(service, recordsGrid);
                });
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private void prepareRecordForService(TableService service, Optional<RegulardataRecordAdapter> item) {
        RegulardataRecordAdapter record = item.orElseGet(() -> new RegulardataRecordAdapter(new RegulardataRecord()));
        useServiceToDeleteRecord(service, record);
    }

    private void useServiceToDeleteRecord(TableService service, RegulardataRecordAdapter record){
        service.deleteRegulardataRecord(record);
    }

    private void refreshParentRecords(TableService service, RecordsGrid recordsGrid) {
        recordsGrid.refreshRecords(service);
    }

    public GridContextMenu<RegulardataRecordAdapter> getRecordGridContextMenu() {
        return recordGridContextMenu;
    }
}
