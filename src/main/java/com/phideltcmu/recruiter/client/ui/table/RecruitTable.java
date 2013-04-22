package com.phideltcmu.recruiter.client.ui.table;

import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.event.RecruitTableFetchedEvent;
import com.phideltcmu.recruiter.client.event.RecruitTableFetchedEventHandler;
import com.phideltcmu.recruiter.shared.model.Person;

import java.util.HashMap;
import java.util.Map;

public class RecruitTable extends PersonTable implements RecruitTableFetchedEventHandler {
    public RecruitTable() {
        super();

        TextColumn<Person> phoneNumberColumn = new TextColumn<Person>() {
            @Override
            public String getValue(Person person) {
                String phoneNumber = person.getPhoneNumber();
                return phoneNumber == null ? "N/A" : phoneNumber;
            }
        };


        Map<String, Column<Person, String>> columnMap = new HashMap<String, Column<Person, String>>();

        columnMap.put("Phone Number", phoneNumberColumn);

        this.initColumns(columnMap);

        DynamicRecruiter.GLOBAL_EVENT_BUS.addHandler(RecruitTableFetchedEvent.TYPE, this);
    }

    @Override
    public void onRecruitTableFetched(RecruitTableFetchedEvent event) {
        this.setData(event.getPersonList());
    }
}
