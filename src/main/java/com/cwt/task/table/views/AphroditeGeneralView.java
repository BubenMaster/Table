package com.cwt.task.table.views;


import com.cwt.task.table.jooq.entity.adapter.RegulardataRecordAdapter;

import com.cwt.task.table.views.order.ColumnOrder;
import com.cwt.task.table.views.properties.ViewProperties;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.RouteScope;


import java.io.IOException;


import static com.cwt.task.table.views.order.ColumnKeys.*;

@Route(value = "/table-aphrodite/")
@RouteScope
@PageTitle("Aphrodite View")
public class AphroditeGeneralView extends VerticalLayout {

    ViewProperties viewProperties = new ViewProperties("view.properties");


    Grid<RegulardataRecordAdapter> grid = new Grid<>(RegulardataRecordAdapter.class);
    Button downloadDataButton = new Button(viewProperties.getProperty("downloadButton.title"));
    Button creationRecordButton = new Button(viewProperties.getProperty("creationRecordButton.title"));
    Dialog newRecord = new Dialog();
    Button saveButton = new Button(viewProperties.getProperty("saveButton.title"));



    public AphroditeGeneralView() throws IOException {
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
        grid.addClassNames("table-grid");
        grid.setWidth("40em");
        grid.addContextMenu();
        ColumnOrder<RegulardataRecordAdapter> columnOrder = new ColumnOrder<>();
        grid.setColumnOrder(
                columnOrder.of(grid).byNames
                (name(),comment(),amount(),created(),updated())
        );
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
        NumberField amountField = new NumberField(viewProperties.getProperty("amountField.title"));

        VerticalLayout dialogLayout = new VerticalLayout(
                nameField,
                commentField,
                amountField);

        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }


    private void downloadDataButtonConfigure() {
        downloadDataButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
    }

    private void creationRecordButtonConfigure() {
        creationRecordButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    }

    private void upperToolBarConfigure() {
        HorizontalLayout upperToolBar = new HorizontalLayout(creationRecordButton, downloadDataButton);
        upperToolBar.addClassName("upper-toolbar");
        add(upperToolBar);
    }

    private void configureListeners() {
        saveButtonListener();
        creationRecordButtonListener();
    }

    private void saveButtonListener() {
        saveButton.addClickListener(e-> newRecord.close());
    }

    private void creationRecordButtonListener() {
        creationRecordButton.addClickListener(e-> newRecord.open());
    }
}
