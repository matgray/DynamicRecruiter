/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.ui.popup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.ui.MailerPanel;
import com.phideltcmu.recruiter.shared.model.Category;
import com.phideltcmu.recruiter.shared.model.Person;

import java.util.List;

public class MailSendPopup extends HidablePopup {
    private final VerticalPanel vp = new VerticalPanel();
    private final MailSendPopup t = this;
    private List<Category> categories;
    private MailerPanel caller;
    private Image loading = new Image("images/ajax-loader.gif");
    private LoadingPopup lp = new LoadingPopup();

    public MailSendPopup(List<Category> categories, final MailerPanel mailerPanel) {
        this.categories = categories;
        this.caller = mailerPanel;
    }

    public void generateWarning() {
        vp.add(new Label("You are about to send this email to:"));
        vp.add(new InlineHTML("<hr><br>"));
        vp.add(loading);
        DynamicRecruiter.RECRUIT_SERVICE.getRecruitList(categories, new AsyncCallback<List<Person>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("There was a problem getting the email list from the server");
                vp.remove(loading);
                t.hide();
            }

            @Override
            public void onSuccess(List<Person> persons) {
                if (persons.size() == 0) {
                    Window.alert("No one matches the categories specified... Aborting");
                    t.hide();
                    return;
                }
                vp.remove(loading);
                for (Person p : persons) {
                    vp.add(new Label(p.getFullName()));
                }
                Button concentButton = new Button("Send it");
                concentButton.setWidth("100%");
                concentButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent clickEvent) {
                        caller.sendMail();
                        lp.center();
                    }
                });
                vp.add(new InlineHTML("<br><hr><br>"));
                vp.add(concentButton);
                vp.add(new InlineHTML("<br>"));
            }
        });
        this.bindWidget(vp);
    }

    @Override
    public void hide() {
        lp.hide();
        super.hide();
    }
}
