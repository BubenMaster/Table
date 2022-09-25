package com.cwt.task.table.view.grid;

import com.cwt.task.table.dao.adapter.RegulardataRecordAdapter;
import com.cwt.task.table.jooq.entity.tables.records.RegulardataRecord;
import com.cwt.task.table.service.TableService;
import com.cwt.task.table.view.grid.order.ColumnOrder;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;

import javax.annotation.PostConstruct;

import java.util.List;

import static com.cwt.task.table.view.grid.order.ColumnKeys.*;
import static com.cwt.task.table.view.grid.order.ColumnKeys.created;


public class RecordsGrid extends Grid<RegulardataRecordAdapter>{

    private final RegulardataRecordAdapter recordImage = new RegulardataRecordAdapter(new RegulardataRecord());

    public RecordsGrid(Class<RegulardataRecordAdapter> regulardataRecordAdapterClass) {
        super(regulardataRecordAdapterClass);
    }

    @PostConstruct
    public void init() {
        addClassNames("table-grid");
        setWidth("60em");

        ColumnOrder<RegulardataRecordAdapter> columnOrder = new ColumnOrder<>();
        setColumnOrder(
            columnOrder.of(this).byNames
                (id(),name(),comment(),amount(),created(),updated())
            );

        getColumnByKey(id()).setWidth("5em");
        getColumnByKey(amount()).setWidth("5em");
        getColumnByKey(updated()).setWidth("11em");
        getColumnByKey(created()).setWidth("11em");


        }



    public void refreshRecords(TableService service){
        List<RegulardataRecordAdapter> records = useServiceToGetAllRecords(service);
        this.setItems(records);
    }

    private List<RegulardataRecordAdapter> useServiceToGetAllRecords(TableService service){
        return service.getAllRegularDataRecords();
    }

    public void configureBinder(TableService service) {

        Label message = new Label();

        Binder<RegulardataRecordAdapter> binder = new Binder<>(RegulardataRecordAdapter.class);
        Editor<RegulardataRecordAdapter> editor = this.getEditor();
        editor.setBinder(binder);
        editor.setBuffered(true);


        TextField nameField = new TextField();
        nameField.setWidthFull();
        addCloseHandler(nameField, editor, service);
        binder.forField(nameField)
                .asRequired("First name must not be empty")
                .withStatusLabel(message)
                .bind(RegulardataRecordAdapter::getName, RegulardataRecordAdapter::setName);
        getColumnByKey(name()).setEditorComponent(nameField);

        TextField commentField = new TextField();
        commentField.setWidthFull();
        addCloseHandler(commentField, editor, service);
        binder.forField(commentField)
                .asRequired("First name must not be empty")
                .withStatusLabel(message)
                .bind(RegulardataRecordAdapter::getComment, RegulardataRecordAdapter::setComment);
        getColumnByKey(comment()).setEditorComponent(commentField);

        TextField amountField = new TextField();
        amountField.setWidthFull();
        addCloseHandler(amountField, editor, service);
        binder.forField(amountField)
                .asRequired("First name must not be empty")
                .withStatusLabel(message)
                .withConverter(new StringToIntegerConverter("Must be a number"))
                .bind(RegulardataRecordAdapter::getAmount, RegulardataRecordAdapter::setAmount);
        getColumnByKey(amount()).setEditorComponent(amountField);


        addItemDoubleClickListener(event -> {
            recordValues(recordImage, event.getItem());
            editor.editItem(event.getItem());
            Component editorComponent = event.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable) {
                ((Focusable<?>) editorComponent).focus();
            }
        });


        editor.addCancelListener(e -> message.setText("Cancel"));
    }


    private void addCloseHandler(Component textField,
                                        Editor<RegulardataRecordAdapter> editor,
                                        TableService service) {

        textField.getElement().addEventListener("keydown", e -> editor.cancel())
            .setFilter("event.code === 'Escape'");

        textField.getElement().addEventListener("keydown", e -> {
                RegulardataRecordAdapter record = editor.getItem();
                editor.save();
                if (!record.equals(recordImage)) {
                    service.updateRegularDataRecord(record);
                    refreshRecords(service);
                }
            })
            .setFilter("event.code === 'Enter' || event.code === 'NumpadEnter'");
    }

    private void recordValues(RegulardataRecordAdapter recordImage, RegulardataRecordAdapter item) {
        recordImage.setName(item.getName());
        recordImage.setComment(item.getComment());
        recordImage.setId(item.getId());
    }

}
