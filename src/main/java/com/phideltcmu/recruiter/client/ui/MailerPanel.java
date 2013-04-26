/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.ui.mail.RichTextToolbar;
import com.phideltcmu.recruiter.client.ui.popup.MailSendPopup;
import com.phideltcmu.recruiter.shared.model.Category;

import java.util.ArrayList;
import java.util.List;

public class MailerPanel extends VerticalPanel {

    private Button sendButton = new Button("Send...");
    private SimpleEventBus privateEventBus = new SimpleEventBus();
    private CategoriesPanel categoriesPanel = new CategoriesPanel(privateEventBus, "Send Email to...", false);
    private final RichTextArea TEXT_AREA;
    private final TextBox subject = new TextBox();
    private List<Category> categories = new ArrayList<Category>();
    private final MailerPanel t = this;
    MailSendPopup mailSendPopup = null;

    public MailerPanel() {
        Grid g = new Grid(1, 2);
        g.setWidget(0, 0, new Label("Subject:"));
        g.setWidget(0, 1, subject);
        this.add(g);
        TEXT_AREA = new RichTextArea();
        RichTextToolbar tb = new RichTextToolbar(TEXT_AREA);
        sendButton.setWidth("100%");

        HorizontalPanel hp = new HorizontalPanel();
        VerticalPanel p = new VerticalPanel();
        p.add(tb);
        p.add(TEXT_AREA);

        TEXT_AREA.setHeight("14em");
        TEXT_AREA.setWidth("100%");
        tb.setWidth("100%");
        p.setWidth("100%");
        DOM.setStyleAttribute(p.getElement(), "marginRight", "4px");
        p.setWidth("32em");
        hp.add(p);
        VerticalPanel vp2 = new VerticalPanel();
        vp2.add(categoriesPanel);
        vp2.add(sendButton);
        hp.add(vp2);
        this.add(hp);

        sendButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                /**
                 * Check subject
                 */
                if (subject.getText().length() == 0) {
                    if (Window.confirm("Send without subject?")) {
                        subject.setText("NO SUBJECT");
                    } else {
                        return;
                    }
                }
                /**
                 * Check body
                 */
                if (TEXT_AREA.getText().length() == 0) {
                    Window.alert("I refuse to send an empty message");
                    return;
                }
                /**
                 * Check categories
                 */
                categories.clear();
                categories = CategoriesPanel.filterCategories(categoriesPanel.getCheckBooleanMap());
                mailSendPopup = new MailSendPopup(categories, t);
                mailSendPopup.generateWarning();
                mailSendPopup.display("Cancel");
            }
        });
    }

    public void sendMail() {
        DynamicRecruiter.RECRUIT_SERVICE.sendMail(subject.getText(),
                TEXT_AREA.getHTML(),
                categories,
                DynamicRecruiter.authUser.getAuthToken(),
                new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        throwable.printStackTrace();
                        Window.alert("There was a problem sending the emails...");
                    }

                    @Override
                    public void onSuccess(Void aVoid) {

                        mailSendPopup.hide();
                        Window.alert("The emails were sent");
                    }
                });
    }

}
