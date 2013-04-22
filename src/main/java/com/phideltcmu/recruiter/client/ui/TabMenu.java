package com.phideltcmu.recruiter.client.ui;


import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TabPanel;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.handler.RecruitListLoadHandler;
import com.phideltcmu.recruiter.shared.model.AuthUser;

public class TabMenu extends TabPanel {
    public TabMenu(AuthUser user) {

        this.setWidth("100%");
        this.setHeight("100%");
        /**
         * Add a welcome Tab
         */
        WelcomePanel welcomePanel = new WelcomePanel(user);
        HTML welcomeTabText = new HTML("Home");
        this.add(welcomePanel, welcomeTabText);

        /**
         * Add a table tab
         */
        RecruitTable table = new RecruitTable();
        HTML tableText = new HTML("Table");
        this.add(table, tableText);
        DynamicRecruiter.RECRUIT_SERVICE.getRecruitList(new RecruitListLoadHandler());

        /**
         * Add a tab for adding people
         */
        PersonAddPanel personAddPanel = new PersonAddPanel();
        personAddPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        HTML addText = new HTML("Add");
        this.add(personAddPanel, addText);

        this.selectTab(0);
    }
}
