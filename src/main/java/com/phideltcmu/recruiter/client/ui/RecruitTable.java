package com.phideltcmu.recruiter.client.ui;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.phideltcmu.recruiter.client.event.RecruitTableFetchedEvent;
import com.phideltcmu.recruiter.client.event.RecruitTableFetchedEventHandler;
import com.phideltcmu.recruiter.shared.model.Person;

public class RecruitTable extends CellTable<Person> implements RecruitTableFetchedEventHandler {
    private final TextColumn<Person> firstNameColumn;
    private final TextColumn<Person> lastNameColumn;
    private final TextColumn<Person> andrewIdColumn;
    private final TextColumn<Person> phoneNumberColumn;
    private final TextColumn<Person> classYearColumn;

    public RecruitTable() {
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
                return person.getPhoneNumber();
            }
        };
        this.classYearColumn = new TextColumn<Person>() {
            @Override
            public String getValue(Person person) {
                return person.getClassYear().toString();
            }
        };
    }

    @Override
    public void onRecruitTableFetched(RecruitTableFetchedEvent event) {
        this.addColumn(firstNameColumn, "First Name");
        this.addColumn(lastNameColumn, "Last Name");
        this.addColumn(andrewIdColumn, "Andrew ID");
        this.addColumn(phoneNumberColumn, "Phone Number");
        this.addColumn(classYearColumn, "Class Year");
    }
}
