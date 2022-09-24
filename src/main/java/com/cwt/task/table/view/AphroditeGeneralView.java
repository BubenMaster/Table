package com.cwt.task.table.view;


import com.cwt.task.table.dao.adapter.RegulardataRecordAdapter;

import com.cwt.task.table.jooq.entity.tables.records.RegulardataRecord;
import com.cwt.task.table.service.TableService;
import com.cwt.task.table.view.order.ColumnOrder;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;


import javax.annotation.PostConstruct;
import java.util.List;


import static com.cwt.task.table.view.order.ColumnKeys.*;

@Route(value = "/table-aphrodite")
@RouteScope
@PageTitle("Aphrodite View")
@SpringComponent
@ComponentScan({"com.cwt.task.table"})
@PropertySource("classpath:view.properties")
public class AphroditeGeneralView extends VerticalLayout{

    private final TableService service;

    Binder<RegulardataRecordAdapter> binder;
    Grid<RegulardataRecordAdapter> grid = new Grid<>(RegulardataRecordAdapter.class);

    @Value("${downloadButton.title}")
    Button downloadDataButton;

    @Value("${creationRecordButton.title}")
    Button creationRecordButton;

    Dialog newRecord = new Dialog();


    @Value("${saveButton.title}")
    Button saveButton;

    List<RegulardataRecordAdapter> records;



    @Autowired
    public AphroditeGeneralView(TableService tableServiceImpl) {
        this.service = tableServiceImpl;
    }


    @PostConstruct
    public void init()
    {
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
        grid.getColumnByKey(amount()).setWidth("5em");
        grid.getColumnByKey(updated()).setWidth("11em");
        grid.getColumnByKey(created()).setWidth("11em");
    }

    private void refreshGridRecords(){
        records = service.getAllRegularDataRecords();
        grid.setItems(records);
    }



    private void newRecordConfigure(){
        newRecord.setHeaderTitle("newRecord.headerTitle");
        VerticalLayout newRecordLayout = newRecordLayoutCreate();
        newRecord.add(newRecordLayout);

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button("cancelButton.title", event -> newRecord.close());
        newRecord.getFooter().add(cancelButton);
        newRecord.getFooter().add(saveButton);
    }


    private VerticalLayout newRecordLayoutCreate() {

        TextField nameField = new TextField("nameField.title");
        TextField commentField = new TextField("commentField.title");
        TextField amountField = new TextField("amountField.title");

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
            service.saveRegularDataRecord(record.actualRegularDataRecord());
            refreshGridRecords();
            newRecord.close();});
    }

    private void creationRecordButtonListener() {
        creationRecordButton.addClickListener(e-> newRecord.open());
    }

}
