/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.ui.table;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.event.RecruitTableFetchedEvent;
import com.phideltcmu.recruiter.client.event.RecruitTableFetchedEventHandler;
import com.phideltcmu.recruiter.client.handler.RemoveUserHandler;
import com.phideltcmu.recruiter.client.ui.ExtraColumn;
import com.phideltcmu.recruiter.client.ui.popup.PersonDetails;
import com.phideltcmu.recruiter.shared.model.Category;
import com.phideltcmu.recruiter.shared.model.Person;

import java.util.ArrayList;
import java.util.List;

public class RecruitTable extends PersonTable implements RecruitTableFetchedEventHandler {
    private List<ExtraColumn> extraColumns = new ArrayList<ExtraColumn>();
    private List<Person> personList = null;

    private void defineColumns() {

        ButtonCell buttonCell = new ButtonCell() {
            @Override
            public boolean handlesSelection() {
                return false;
            }
        };

        Column<Person, String> detailsButton = new Column<Person, String>(buttonCell) {
            @Override
            public String getValue(Person person) {
                return "View";
            }
        };

        detailsButton.setFieldUpdater(new FieldUpdater<Person, String>() {
            @Override
            public void update(int i, Person person, String s) {
                new PersonDetails(person).display();
            }
        });

        extraColumns.add(new ExtraColumn("Details", detailsButton));

        if (DynamicRecruiter.authUser.isAdmin()) {
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

            extraColumns.add(new ExtraColumn("Options", deletePersonButton));
        }

    }

    public RecruitTable() {
        super();
        DynamicRecruiter.GLOBAL_EVENT_BUS.addHandler(RecruitTableFetchedEvent.TYPE, this);

        // EditTextCell.
        EditTextCell editTextCell = new EditTextCell();

        Column<Person, String> phoneNumberColumn = new Column<Person, String>(editTextCell) {
            @Override
            public String getValue(Person person) {
                String phoneNumber = person.getPhoneNumber();
                return phoneNumber.length() == 0 ? "N/A" : phoneNumber;
            }
        };

        phoneNumberColumn.setFieldUpdater(new FieldUpdater<Person, String>() {
            @Override
            public void update(int i, final Person person, String s) {
                String number = s.replaceAll("\\D+", "");
                if (number.length() == 10 || number.length() == 0) {
                    DynamicRecruiter.RECRUIT_SERVICE.setPhoneNumber(person.getAndrewID(), number, DynamicRecruiter.authUser.getAuthToken(), new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            throwable.printStackTrace();
                        }

                        @Override
                        public void onSuccess(String formattedNumber) {
                            person.setPhoneNumber(formattedNumber);
                        }
                    });
                } else {
                    Window.alert("That's an invalid number");
                }
            }
        });

        extraColumns.add(new ExtraColumn("Phone Number", phoneNumberColumn));
    }

    @Override
    public void onRecruitTableFetched(RecruitTableFetchedEvent event) {
        this.personList = event.getPersonList();
        this.setData(personList);
    }

    public void render(List<Category> categories) {
        final List<String> categoryNames = new ArrayList<String>();
        for (Category category : categories) {
            categoryNames.add(category.getValue());
        }
        if (DynamicRecruiter.authUser.isAdmin()) {
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
//                                    Window.alert("Status Updated!");
                                }
                            });
                }
            });
            extraColumns.add(new ExtraColumn("Status", categoryColumn));
        } else {
            TextColumn<Person> statusColumn = new TextColumn<Person>() {
                @Override
                public String getValue(Person person) {
                    return person.getStatus();
                }
            };
            extraColumns.add(new ExtraColumn("Status", statusColumn));
        }
        defineColumns();
        this.initColumns(extraColumns);
    }
}
