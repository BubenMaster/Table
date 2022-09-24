package com.cwt.task.table.views;


import com.cwt.task.table.controller.TableController;
import com.cwt.task.table.dao.adapter.RegulardataRecordAdapter;

import com.cwt.task.table.jooq.entity.tables.records.RegulardataRecord;
import com.cwt.task.table.views.order.ColumnOrder;
import com.cwt.task.table.views.properties.ViewProperties;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.theme.Theme;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


import java.util.List;


import static com.cwt.task.table.views.order.ColumnKeys.*;

@Route(value = "/table-aphrodite")
@RouteScope
@PageTitle("Aphrodite View")
@SpringComponent
@ComponentScan({"com.cwt.task.table"})
public class AphroditeGeneralView extends VerticalLayout{


    ViewProperties viewProperties = new ViewProperties("view.properties");
    TableController tableController = new TableController();

    Binder<RegulardataRecordAdapter> binder;
    Grid<RegulardataRecordAdapter> grid = new Grid<>(RegulardataRecordAdapter.class);

    Button downloadDataButton = new Button(viewProperties.getProperty("downloadButton.title"));
    Button creationRecordButton = new Button(viewProperties.getProperty("creationRecordButton.title"));
    Dialog newRecord = new Dialog();
    Button saveButton = new Button(viewProperties.getProperty("saveButton.title"));



    public AphroditeGeneralView() {

        addClassName("general-view");
        configureGUI();
        configureListeners();

    }


    void configureGUI(){
        setSizeFull();

        gridConfigure();
        HorizontalLayout gridLayout = new HorizontalLayout(grid);
        gridLayout.setAlignItems(Alignment.CENTER);

        newRecordConfigure();
        downloadDataButtonConfigure();
        creationRecordButtonConfigure();
        upperToolBarConfigure();

        add(gridLayout);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);
    }


    private void gridConfigure() {
        refreshGridRecords();

        grid.addClassNames("table-grid");
        grid.setWidth("60em");
        grid.addContextMenu();
        ColumnOrder<RegulardataRecordAdapter> columnOrder = new ColumnOrder<>();
        grid.setColumnOrder(
                columnOrder.of(grid).byNames
                        (id(),name(),comment(),amount(),created(),updated())
        );
        grid.getColumnByKey(id()).setWidth("5em");
//        grid.getColumnByKey(name()).setWidth("10em");
        grid.getColumnByKey(amount()).setWidth("5em");
        grid.getColumnByKey(updated()).setWidth("15em");
        grid.getColumnByKey(created()).setWidth("15em");
    }

    private void refreshGridRecords(){
        List<RegulardataRecordAdapter> records = tableController.showAllRegularDataRecords();
        grid.setItems(records);
    }


    private void newRecordConfigure(){
        newRecord.setHeaderTitle(viewProperties.getProperty("newRecord.headerTitle"));
        VerticalLayout newRecordLayout = newRecordLayoutCreate();
        newRecord.add(newRecordLayout);

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button(viewProperties.getProperty("cancelButton.title"), e -> newRecord.close());
        newRecord.getFooter().add(cancelButton);
        newRecord.getFooter().add(saveButton);
    }


    private VerticalLayout newRecordLayoutCreate() {

        TextField nameField = new TextField(viewProperties.getProperty("nameField.title"));
        TextField commentField = new TextField(viewProperties.getProperty("commentField.title"));
        TextField amountField = new TextField(viewProperties.getProperty("amountField.title"));

        VerticalLayout dialogLayout = new VerticalLayout(
                nameField,
                commentField,
                amountField);

        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        binder = new Binder<>(RegulardataRecordAdapter.class);
        binder.forField(nameField)
                .bind(RegulardataRecordAdapter::getName,RegulardataRecordAdapter::setName);
        binder.forField(commentField)
                .bind(RegulardataRecordAdapter::getComment,RegulardataRecordAdapter::setComment);
        binder.forField(amountField)
                .withConverter(new StringToIntegerConverter("Must be a number"))
                .bind(RegulardataRecordAdapter::getAmount,RegulardataRecordAdapter::setAmount);

        return dialogLayout;
    }


    private void downloadDataButtonConfigure() {
        downloadDataButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
    }

    private void creationRecordButtonConfigure() {
        creationRecordButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    }

    private void upperToolBarConfigure() {
        H3 regularRecords = new H3("Regular records");
        regularRecords.setWidth("15em");
        VerticalLayout upperLeftLayout = new VerticalLayout(regularRecords);
        upperLeftLayout.setAlignItems(Alignment.START);
        upperLeftLayout.setJustifyContentMode(JustifyContentMode.END);

        VerticalLayout upperRightLayout = new VerticalLayout(downloadDataButton,creationRecordButton);
        upperRightLayout.setAlignItems(Alignment.END);
        upperRightLayout.setJustifyContentMode(JustifyContentMode.END);

        HorizontalLayout upperLayout = new HorizontalLayout(upperLeftLayout, upperRightLayout);
        upperLayout.setWidth("60em");

        add(upperLayout);
    }

    private void configureListeners() {
        saveButtonListener();
        creationRecordButtonListener();
    }

    private void saveButtonListener() {
        saveButton.addClickListener(e-> {
            RegulardataRecordAdapter record = new RegulardataRecordAdapter(new RegulardataRecord());
            try {
                binder.writeBean(record);
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            }
            tableController.saveRegularDataRecord(record.actualRegularDataRecord());
            refreshGridRecords();
            newRecord.close();});
    }

    private void creationRecordButtonListener() {
        creationRecordButton.addClickListener(e-> newRecord.open());
    }

}
