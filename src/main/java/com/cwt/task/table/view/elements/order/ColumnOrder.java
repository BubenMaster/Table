package com.cwt.task.table.view.elements.order;


import com.vaadin.flow.component.grid.Grid;


public class ColumnOrder<T> {

     Grid<T> grid;

    public  ColumnOrder<T> of(Grid<T> inputGrid){
        grid = inputGrid;
        return this;
    }

    @SuppressWarnings("unchecked")
    public Grid.Column<T>[] byNames(String... names){
        Grid.Column<T>[] columns;
        columns = new Grid.Column[names.length];
        for (int i = 0; i < names.length; i++){
                columns[i] = grid.getColumnByKey(names[i]);
            }
        return columns;
    }

}
