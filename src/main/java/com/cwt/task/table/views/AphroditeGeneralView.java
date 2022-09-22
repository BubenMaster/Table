package com.cwt.task.table.views;


import com.cwt.task.table.jooq.entity.adapter.RegulardataRecordAdapter;

import com.cwt.task.table.views.order.ColumnOrder;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

import static com.cwt.task.table.views.order.ColumnKeys.*;

@Configuration
@Route(value = "/table-aphrodite")
@PageTitle("Aphrodite View")
@PropertySource(value = "classpath:view.properties")
public class AphroditeGeneralView extends VerticalLayout {

    @Autowired
    private Environment env = new StandardEnvironment();

    Grid<RegulardataRecordAdapter> grid = new Grid<>(RegulardataRecordAdapter.class);
    Button downloadDataButton = new Button("Download data"/*env.getProperty("downloadButton.title")*/);
    Button creationRecordButton = new Button("Create record"/*env.getProperty("creationRecordButton.title")*/);
    Dialog newRecord = new Dialog();
    Button saveButton = new Button("Save"/*env.getProperty("saveButton.title")*/);

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
        grid.addClassNames("table-grid"); //
        grid.setWidth("40em");//
        grid.addContextMenu();
        ColumnOrder<RegulardataRecordAdapter> columnOrder = new ColumnOrder<>();
        grid.setColumnOrder(columnOrder.of(grid).byNames(name(),comment(),amount(),created(),updated()));
    }


    private void newRecordConfigure(){
        newRecord.setHeaderTitle("New Record"/*env.getProperty("newRecord.headerTitle")*/);
        VerticalLayout newRecordLayout = newRecordLayoutCreate();
        newRecord.add(newRecordLayout);

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button("Cancel"/*env.getProperty("cancelButton.title")*/, e -> newRecord.close());
        newRecord.getFooter().add(cancelButton);
        newRecord.getFooter().add(saveButton);
    }
    private VerticalLayout newRecordLayoutCreate() {

        TextField nameField = new TextField("Name"/*env.getProperty("nameField.title")*/);
        TextField commentField = new TextField("Comment"/*env.getProperty("commentField.title")*/);
        NumberField amountField = new NumberField("Amount"/*env.getProperty("amountField.title")*/);

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
