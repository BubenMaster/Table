package com.cwt.task.table.view;


import com.cwt.task.table.adaptation.RegulardataRecordAdapter;

import com.cwt.task.table.jooq.entity.tables.records.RegulardataRecord;
import com.cwt.task.table.service.TableService;
import com.cwt.task.table.view.elements.GridLayout;
import com.cwt.task.table.view.elements.RecordsGrid;
import com.cwt.task.table.view.elements.RecordGridContextMenuContainer;
import com.vaadin.flow.component.Component;
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
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.vaadin.stefan.LazyDownloadButton;


import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Route(value = "")
@PageTitle("View")
@SpringComponent
@ComponentScan({"com.cwt.task.table"})
@PropertySource("classpath:view.properties")
public class AphroditeGeneralView extends VerticalLayout{

    private final TableService service;

    Binder<RegulardataRecordAdapter> binder;



    @Value("${nameField.title}") TextField nameField;
    @Value("${commentField.title}") TextField commentField;
    @Value("${amountField.title}") TextField amountField;
    @Value("${newRecord.header.title}") String newRecordHeaderTitle;
    @Value("${cancelButton.title}") String newRecordCancelButtonTitle;
    @Value("${grid.title}") H3 gridTitle;
    @Value("${convertToIntegerError.message}") String convertToIntegerErrorMessage;
    @Value("${downloadButton.title}") String downloadDataButtonTitle;
    @Value("${creationRecordButton.title}") String creationRecordButtonTitle;

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
        configureLayouts();
        onInitServiceRequests();
        configureListeners();
    }

    void configureLayouts(){
        setSizeFull();

        newRecordConfigure();

        upperToolBarConfigure();

        add(gridLayout);
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


    private Component downloadDataButton() {
        LazyDownloadButton lazyDownloadButton = new LazyDownloadButton(downloadDataButtonTitle,
                () -> "tableData.json",
                this::loadStreamFromLocalFileOfDataBase
        );

        lazyDownloadButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        return lazyDownloadButton;
    }

    private InputStream loadStreamFromLocalFileOfDataBase() {
        String localFileName = "table.json";
        service.readDataToLocalFile(localFileName);
        return loadFromLocalFile(localFileName);

    }

    private InputStream loadFromLocalFile(String localFileName) {
        try {
            return Files.newInputStream(Paths.get(localFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private Component creationRecordButton() {
        Button creationRecordButton = new Button(creationRecordButtonTitle);
        creationRecordButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        creationRecordButtonListener(creationRecordButton);

        return creationRecordButton;
    }

    private void creationRecordButtonListener(Button creationRecordButton) {
        creationRecordButton.addClickListener(e-> newRecord.open());
    }

    private void upperToolBarConfigure() {
        gridTitle.setWidth("15em");
        VerticalLayout upperLeftLayout = new VerticalLayout(gridTitle);
        upperLeftLayout.setAlignItems(Alignment.START);
        upperLeftLayout.setJustifyContentMode(JustifyContentMode.END);

        VerticalLayout upperRightLayout = new VerticalLayout(downloadDataButton(), creationRecordButton());
        upperRightLayout.setAlignItems(Alignment.END);
        upperRightLayout.setJustifyContentMode(JustifyContentMode.END);

        HorizontalLayout upperLayout = new HorizontalLayout(upperLeftLayout, upperRightLayout);
        upperLayout.setWidth("60em");

        add(upperLayout);
    }

    private void onInitServiceRequests() {
        recordsGridOnView.refreshRecords(service);
    }

    private void configureListeners() {
        saveButtonListener();
        gridContextMenuListener();
        inlineEditorListener();
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
    private void gridContextMenuListener(){
        contextMenu.deleteButtonClickListener(service, recordsGridOnView);
    }

    private void inlineEditorListener() {recordsGridOnView.configureInlineEditor(service);
    }
}
