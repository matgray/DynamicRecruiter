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
    }

    final public void display() {
        this.setGlassEnabled(true);
        this.center();
    }
}
