package com.phideltcmu.recruiter.client.ui.popup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.shared.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDetails extends HidablePopup {
    public PersonDetails(final Person person) {
        final FlexTable ft = new FlexTable();
        String notes = person.getNotes();
        String referrals = person.getRecommencedBy();
        String addedBy = person.getOriginalReferrer();

        Button editNotes = new Button("Edit");

        ft.setText(0, 0, "Notes:");
        ft.setWidget(0, 1, notes == null ? new HTML("NONE") : new HTML(notes));
        ft.setWidget(0, 2, editNotes);
        ft.setWidget(1, 0, new HTML("<b>Referrals: </b>"));
        ft.setText(1, 1, referrals.length() == 0 ? "None" : "Loading...");
        ft.setWidget(2, 0, new HTML("<b>Added By: </b>"));
        ft.setText(2, 1, addedBy == null ? "ERROR" : "Loading...");

        editNotes.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                new NotesAdder(person).display();
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
                    ft.setWidget(1, 1, new HTML(builder.toString()));
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
                    ft.setText(2, 1, strings.get(0));
                }
            });
        }
    }
}
