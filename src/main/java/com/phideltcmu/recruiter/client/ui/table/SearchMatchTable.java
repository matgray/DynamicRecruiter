package com.phideltcmu.recruiter.client.ui.table;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.Column;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.handler.AddUserHandler;
import com.phideltcmu.recruiter.client.ui.ExtraColumn;
import com.phideltcmu.recruiter.shared.model.Person;

import java.util.ArrayList;
import java.util.List;

public class SearchMatchTable extends PersonTable {
    public SearchMatchTable() {
        ButtonCell buttonCell = new ButtonCell() {
            @Override
            public boolean handlesSelection() {
                return false;
            }
        };

        Column<Person, String> addButtonColumn = new Column<Person, String>(buttonCell) {
            @Override
            public String getValue(Person person) {
                return "Add to list";
            }
        };

        addButtonColumn.setFieldUpdater(new FieldUpdater<Person, String>() {
            @Override
            public void update(int i, Person person, String s) {
                DynamicRecruiter.RECRUIT_SERVICE.addPerson(person,
                        DynamicRecruiter.authUser,
                        new AddUserHandler());
            }
        });

        List<ExtraColumn> extraCols = new ArrayList<ExtraColumn>();
        extraCols.add(new ExtraColumn("", addButtonColumn));
        this.initColumns(extraCols);
    }
}
