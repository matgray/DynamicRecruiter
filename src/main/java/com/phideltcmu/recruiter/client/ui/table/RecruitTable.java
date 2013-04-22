package com.phideltcmu.recruiter.client.ui.table;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.event.CategoriesFetchedEvent;
import com.phideltcmu.recruiter.client.event.CategoriesFetchedEventHandler;
import com.phideltcmu.recruiter.client.event.RecruitTableFetchedEvent;
import com.phideltcmu.recruiter.client.event.RecruitTableFetchedEventHandler;
import com.phideltcmu.recruiter.client.handler.RemoveUserHandler;
import com.phideltcmu.recruiter.shared.model.Category;
import com.phideltcmu.recruiter.shared.model.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecruitTable extends PersonTable implements RecruitTableFetchedEventHandler, CategoriesFetchedEventHandler {
    private Map<String, Column<Person, String>> columnMap = new HashMap<String, Column<Person, String>>();

    public RecruitTable() {
        super();
        DynamicRecruiter.GLOBAL_EVENT_BUS.addHandler(RecruitTableFetchedEvent.TYPE, this);
        DynamicRecruiter.GLOBAL_EVENT_BUS.addHandler(CategoriesFetchedEvent.TYPE, this);

        TextColumn<Person> phoneNumberColumn = new TextColumn<Person>() {
            @Override
            public String getValue(Person person) {
                String phoneNumber = person.getPhoneNumber();
                return phoneNumber == null ? "N/A" : phoneNumber;
            }
        };

        columnMap.put("Phone Number", phoneNumberColumn);

        ButtonCell buttonCell = new ButtonCell() {
            @Override
            public boolean handlesSelection() {
                return false;
            }
        };

        Column<Person, String> deletePersonButton = new Column<Person, String>(buttonCell) {
            @Override
            public String getValue(Person person) {
                return "Delete";
            }
        };

        deletePersonButton.setFieldUpdater(new FieldUpdater<Person, String>() {
            @Override
            public void update(int i, Person person, String s) {
                DynamicRecruiter.RECRUIT_SERVICE.removeUser(person.getAndrewID(), DynamicRecruiter.authUser.getAuthToken(), new RemoveUserHandler());
            }
        });

        columnMap.put("", deletePersonButton);

        DynamicRecruiter.RECRUIT_SERVICE.getCategories(new AsyncCallback<List<Category>>() {
            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(List<Category> categories) {
                DynamicRecruiter.GLOBAL_EVENT_BUS.fireEvent(new CategoriesFetchedEvent(categories));
            }
        });
    }

    @Override
    public void onRecruitTableFetched(RecruitTableFetchedEvent event) {
        this.setData(event.getPersonList());
    }

    @Override
    public void onCategoriesFetched(CategoriesFetchedEvent event) {
        final List<Category> categories = event.getCategoryList();
        final List<String> categoryNames = new ArrayList<String>();
        for (Category category : categories) {
            categoryNames.add(category.getValue());
        }
        SelectionCell categoryCell = new SelectionCell(categoryNames);
        Column<Person, String> categoryColumn = new Column<Person, String>(categoryCell) {
            @Override
            public String getValue(Person object) {
                return categoryNames.get(categoryNames.indexOf(object.getStatus()));
            }
        };

        categoryColumn.setFieldUpdater(new FieldUpdater<Person, String>() {
            @Override
            public void update(int index, Person object, String value) {
                object.setStatus(value);
                DynamicRecruiter.RECRUIT_SERVICE.changeCategory(object.getAndrewID(),
                        value,
                        DynamicRecruiter.authUser.getAuthToken(),
                        new AsyncCallback<Void>() {
                            @Override
                            public void onFailure(Throwable throwable) {
                                throwable.printStackTrace();
                            }

                            @Override
                            public void onSuccess(Void aVoid) {
                                Window.alert("Status Updated!");
                            }
                        });
            }
        });
        columnMap.put("Status", categoryColumn);
        this.initColumns(columnMap);
    }
}
