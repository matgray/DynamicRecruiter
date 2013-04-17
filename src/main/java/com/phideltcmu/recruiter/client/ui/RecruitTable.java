package com.phideltcmu.recruiter.client.ui;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.comparator.PersonComparators;
import com.phideltcmu.recruiter.client.event.RecruitTableFetchedEvent;
import com.phideltcmu.recruiter.client.event.RecruitTableFetchedEventHandler;
import com.phideltcmu.recruiter.shared.model.Person;

import java.util.Date;
import java.util.List;

public class RecruitTable extends CellTable<Person> implements RecruitTableFetchedEventHandler {
    private final TextColumn<Person> firstNameColumn;
    private final TextColumn<Person> lastNameColumn;
    private final TextColumn<Person> andrewIdColumn;
    private final TextColumn<Person> phoneNumberColumn;
    private final TextColumn<Person> classYearColumn;

    public RecruitTable() {
        /**
         * Add Handlers
         */
        DynamicRecruiter.GLOBAL_EVENT_BUS.addHandler(RecruitTableFetchedEvent.TYPE, this);

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

        this.phoneNumberColumn = new TextColumn<Person>() {
            @Override
            public String getValue(Person person) {
                String phoneNumber = person.getPhoneNumber();
                return phoneNumber == null ? "N/A" : phoneNumber;
            }
        };
        this.classYearColumn = new TextColumn<Person>() {
            @Override
            public String getValue(Person person) {
                Date classYear = person.getClassYear();
                return classYear == null ? "N/A" : classYear.toString();
            }
        };
    }

    @Override
    public void onRecruitTableFetched(RecruitTableFetchedEvent event) {
        /**
         * Set columns
         */
        this.addColumn(lastNameColumn, "Last Name");
        this.addColumn(firstNameColumn, "First Name");
        this.addColumn(andrewIdColumn, "Andrew ID");
        this.addColumn(phoneNumberColumn, "Phone Number");
        this.addColumn(classYearColumn, "Class Year");

        /**
         * Set up data provider
         */
        ListDataProvider<Person> dataProvider = new ListDataProvider<Person>();
        dataProvider.addDataDisplay(this);
        List<Person> dataProviderList = dataProvider.getList();
        dataProviderList.addAll(event.getPersonList());

        /**
         * Enable sorting by last name
         */
        ColumnSortEvent.ListHandler<Person> lastNameSortHandler =
                new ColumnSortEvent.ListHandler<Person>(dataProviderList);
        lastNameSortHandler.setComparator(lastNameColumn, PersonComparators.lastNameComparator);
        this.addColumnSortHandler(lastNameSortHandler);
        this.getColumn(0).setSortable(true);

        /**
         * Set the row data
         */
        this.setRowData(0, dataProviderList);

        /**
         * Default sort by last name
         */
        this.getColumnSortList().push(lastNameColumn);
        ColumnSortEvent.fire(this, this.getColumnSortList());
    }
}
