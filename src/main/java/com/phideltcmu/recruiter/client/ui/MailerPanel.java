/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.ui;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.phideltcmu.recruiter.client.ui.mail.RichTextToolbar;
import com.phideltcmu.recruiter.client.ui.popup.MailSendPopup;

public class MailerPanel extends VerticalPanel {

    private Button sendButton = new Button("Send...");
    private SimpleEventBus privateEventBus = new SimpleEventBus();
    private CategoriesPanel categoriesPanel = new CategoriesPanel(privateEventBus, "Send Email to...", false);
    private MailSendPopup confirmationPopup = new MailSendPopup();

    public MailerPanel() {
        RichTextArea area = new RichTextArea();
        RichTextToolbar tb = new RichTextToolbar(area);
        sendButton.setWidth("100%");

        HorizontalPanel hp = new HorizontalPanel();
        VerticalPanel p = new VerticalPanel();
        p.add(tb);
        p.add(area);

        area.setHeight("14em");
        area.setWidth("100%");
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
    }
}
