/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.ui.popup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

public class HidablePopup extends PopupPanel {
    Button closeButton = new Button("Close");
    private HidablePopup popup = this;

    final public void bindWidget(Widget w) {
        VerticalPanel vp = new VerticalPanel();
        vp.add(w);
        vp.add(closeButton);
        vp.setWidth("100%");
        closeButton.setWidth("100%");
        vp.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
        closeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                popup.hide();
            }
        });
        this.setWidget(vp);
        this.setAutoHideEnabled(true);
        this.setStyleName("darkPopup");
    }

    final public void display() {
        this.center();
    }

    final public void display(String closeText) {
        closeButton.setText(closeText);
        this.center();
    }
}
