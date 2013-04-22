package com.phideltcmu.recruiter.client.ui;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.phideltcmu.recruiter.shared.model.AuthUser;

public class WelcomePanel extends VerticalPanel {
    public WelcomePanel(AuthUser user) {
        this.setWidth("100%");
        this.setHeight("100%");
        this.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        this.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);

        Label letters = new Label("ΦΔΘ");
        Label welcome = new Label("You are logged in as: " + user.getFullName());
        letters.setStyleName("oldenburg500");
        welcome.setStyleName("oldenburg150");
        this.add(letters);
        this.add(welcome);
    }
}
