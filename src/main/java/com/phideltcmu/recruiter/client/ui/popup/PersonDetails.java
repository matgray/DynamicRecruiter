/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.ui.popup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.callback.StringCallback;
import com.phideltcmu.recruiter.shared.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDetails extends HidablePopup {
    public PersonDetails(final Person person) {
        final FlexTable ft = new FlexTable();
        String notes = person.getNotes();
        String referrals = person.getRecommencedBy();
        String addedBy = person.getOriginalReferrer();

        Button editNotes = new Button("Edit Notes");
        editNotes.setWidth("100%");

        ft.setWidget(0, 0, new HTML("<b>Details of " + person.getFullName() + "</b><br><hr>"));

        ft.setWidget(1, 0, new HTML("<b>Notes:</b>"));
        ft.setWidget(1, 1, notes == null ? new HTML("NONE") : new HTML(notes));
        ft.setWidget(2, 1, editNotes);

        ft.setWidget(3, 0, new HTML("<b>Referrals: </b>"));
        ft.setText(4, 1, referrals.length() == 0 ? "None" : "Loading...");
        ft.setWidget(5, 0, new HTML("<b>Added By: </b>"));
        ft.setText(6, 1, addedBy == null ? "ERROR" : "Loading...");

        editNotes.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                NotesAdder adder = new NotesAdder(person, new StringCallback() {
                    @Override
                    public void onStringReturned(String result) {
                        ft.setWidget(1, 1, result == null ? new HTML("NONE") : new HTML(result));
                        person.setNotes(result);
                    }
                });
                adder.show();
            }
        });

        this.bindWidget(ft);

        String[] delim = referrals.split(",");
        List<String> list = new ArrayList<String>(delim.length);

        for (String s : delim) {
            list.add(s);
        }

        if (referrals.length() > 0) {
            DynamicRecruiter.RECRUIT_SERVICE.internalIDsToNames(list, new AsyncCallback<List<String>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    throwable.printStackTrace();
                }

                @Override
                public void onSuccess(List<String> strings) {
                    //TODO SHOW REFERRER NAMES
                    StringBuilder builder = new StringBuilder();
                    for (String s : strings) {
                        builder.append(s + "<br>");
                    }
                    ft.setWidget(4, 1, new HTML(builder.toString()));
                }
            });
        }
        if (addedBy != null) {
            List<String> singletonUser = new ArrayList<String>(1);
            singletonUser.add(addedBy);
            DynamicRecruiter.RECRUIT_SERVICE.internalIDsToNames(singletonUser, new AsyncCallback<List<String>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    throwable.printStackTrace();
                }

                @Override
                public void onSuccess(List<String> strings) {
                    ft.setText(6, 1, strings.get(0));
                }
            });
        }
    }
}
