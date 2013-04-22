package com.phideltcmu.recruiter.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.handler.RecruitListLoadHandler;
import com.phideltcmu.recruiter.client.ui.table.RecruitTable;

public class MasterListPanel extends VerticalPanel {
    Button refreshButton = new Button("Refresh Table");

    public MasterListPanel() {
        super();
        refreshButton.setWidth("100%");
        this.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        this.add(refreshButton);
        this.add(new RecruitTable());

        refreshButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                DynamicRecruiter.RECRUIT_SERVICE.getRecruitList(new RecruitListLoadHandler());
            }
        });
    }
}
