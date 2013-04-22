package com.phideltcmu.recruiter.client.ui.table;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;
import com.phideltcmu.recruiter.client.comparator.PersonComparators;
import com.phideltcmu.recruiter.shared.model.Person;

import java.util.List;
import java.util.Map;

public class PersonTable extends CellTable<Person> {
    private final TextColumn<Person> firstNameColumn;
    private final TextColumn<Person> lastNameColumn;
    private final TextColumn<Person> andrewIdColumn;
    private final TextColumn<Person> classYearColumn;
    private boolean initialized = false;


    ListDataProvider<Person> dataProvider = new ListDataProvider<Person>();
    List<Person> dataProviderList = dataProvider.getList();

    public PersonTable() {
        /**
         * Initialize Columns
         */
        this.firstNameColumn = new TextColumn<Person>() {
            @Override
            public String getValue(Person person) {
                return person.getFirstName();
            }
        };

        this.lastNameColumn = new TextColumn<Person>() {
            @Override
            public String getValue(Person person) {
                return person.getLastName();
            }
        };

        this.andrewIdColumn = new TextColumn<Person>() {
            @Override
            public String getValue(Person person) {
                return person.getAndrewID();
            }
        };

        this.classYearColumn = new TextColumn<Person>() {
            @Override
            public String getValue(Person person) {
                String classYear = person.getClassYear();
                return classYear == null ? "N/A" : classYear;
            }
        };
        dataProvider.addDataDisplay(this);
    }

    private void initSorting() {
        /**
         * Enable sorting by last name
         */
        ColumnSortEvent.ListHandler<Person> lastNameSortHandler =
                new ColumnSortEvent.ListHandler<Person>(dataProviderList);
        lastNameSortHandler.setComparator(lastNameColumn, PersonComparators.lastNameComparator);
        this.addColumnSortHandler(lastNameSortHandler);
        this.getColumn(0).setSortable(true);
        this.getColumnSortList().push(lastNameColumn);
    }

    private void initDefaultColumns() {
        this.addColumn(lastNameColumn, "Last Name");
        this.addColumn(firstNameColumn, "First Name");
        this.addColumn(andrewIdColumn, "Andrew ID");
        this.addColumn(classYearColumn, "Class Year");
    }

    public void initColumns() {
        if (initialized) {
            throw new IllegalStateException("columns initialized multiple times!");
        }
        initDefaultColumns();
        initSorting();
        this.initialized = true;
    }

    public void initColumns(Map<String, Column<Person, String>> extraColumns) {
        if (initialized) {
            throw new IllegalStateException("columns initialized multiple times!");
        }
        initDefaultColumns();
        /**
         * Set columns
         */
        for (String colName : extraColumns.keySet()) {
            this.addColumn(extraColumns.get(colName), colName);
        }
        initSorting();
        this.initialized = true;
    }

    final public void setData(List<Person> personList) {
        dataProviderList.clear();
        dataProviderList.addAll(personList);

        /**
         * Default sort by last name
         */
        ColumnSortEvent.fire(this, this.getColumnSortList());
    }
}
