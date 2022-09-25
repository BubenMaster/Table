package com.cwt.task.table.view;


import com.cwt.task.table.dao.adapter.RegulardataRecordAdapter;

import com.cwt.task.table.jooq.entity.tables.records.RegulardataRecord;
import com.cwt.task.table.service.TableService;
import com.cwt.task.table.view.grid.GridLayout;
import com.cwt.task.table.view.grid.RecordsGrid;
import com.cwt.task.table.view.grid.RecordGridContextMenuContainer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
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
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;


import javax.annotation.PostConstruct;
import java.util.List;

@Route(value = "/table-aphrodite")
@PageTitle("Aphrodite View")
@SpringComponent
@ComponentScan({"com.cwt.task.table"})
@PropertySource("classpath:view.properties")
public class AphroditeGeneralView extends VerticalLayout{

    private final TableService service;

    Binder<RegulardataRecordAdapter> binder;

    @Value("${downloadButton.title}") Button downloadDataButton;
    @Value("${creationRecordButton.title}") Button creationRecordButton;
    @Value("${nameField.title}") TextField nameField;
    @Value("${commentField.title}") TextField commentField;
    @Value("${amountField.title}") TextField amountField;
    @Value("${newRecord.header.title}") String newRecordHeaderTitle;
    @Value("${cancelButton.title}") String newRecordCancelButtonTitle;
    @Value("${grid.title}") H3 gridTitle;
    @Value("${convertToIntegerError.message}") String convertToIntegerErrorMessage;


    List<RegulardataRecordAdapter> records;
    @Autowired
    GridLayout gridLayout;

    @Autowired
    RecordsGrid recordsGridOnView;

    @Autowired
    RecordGridContextMenuContainer contextMenu;

    Dialog newRecord = new Dialog();
    @Value("${saveButton.title}")
    Button saveButton;



    @Autowired
    public AphroditeGeneralView(TableService tableServiceImpl) {
        this.service = tableServiceImpl;
        onEnabledStateChanged(true);
    }


    @PostConstruct
    public void init()
    {
        addClassName("general-view");
        configureGUI();
        onInitServiceRequests();
        configureListeners();
    }

    void configureGUI(){
        setSizeFull();

        newRecordConfigure();

        downloadDataButtonConfigure();
        creationRecordButtonConfigure();
        upperToolBarConfigure();

        add(gridLayout);
//        gridLayout.add(contextMenu);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);
    }

    private void newRecordConfigure(){
        newRecord.setHeaderTitle(newRecordHeaderTitle);
        VerticalLayout newRecordLayout = newRecordLayoutCreate();
        newRecord.add(newRecordLayout);

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button(newRecordCancelButtonTitle, event -> newRecord.close());
        newRecord.getFooter().add(cancelButton);
        newRecord.getFooter().add(saveButton);
    }



    private VerticalLayout newRecordLayoutCreate() {
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
                .withConverter(new StringToIntegerConverter(convertToIntegerErrorMessage))
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
        gridTitle.setWidth("15em");
        VerticalLayout upperLeftLayout = new VerticalLayout(gridTitle);
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
        creationRecordButtonListener();
        saveButtonListener();
        gridContextMenuListener();
        recordsGridOnView.configureBinder(service);
    }

    private void onInitServiceRequests() {
        recordsGridOnView.refreshRecords(service);
    }

    private void saveButtonListener() {
        saveButton.addClickListener(event-> {
            RegulardataRecordAdapter record = new RegulardataRecordAdapter(new RegulardataRecord());
            try {
                binder.writeBean(record);
            } catch (ValidationException ex) {
                throw new RuntimeException(ex);
            }
            service.saveRegularDataRecord(record);
            recordsGridOnView.refreshRecords(service);
            newRecord.close();
        });
    }

    private void creationRecordButtonListener() {
        creationRecordButton.addClickListener(e-> newRecord.open());
    }

    private void gridContextMenuListener(){
        contextMenu.deleteButtonClickListener(service, recordsGridOnView);
    }

}
