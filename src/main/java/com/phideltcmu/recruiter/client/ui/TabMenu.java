package com.phideltcmu.recruiter.client.ui;


import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TabPanel;
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
        MasterListPanel masterListPanel = new MasterListPanel();
        HTML tableText = new HTML("Table");
        this.add(masterListPanel, tableText);

        /**
         * Add a tab for adding people
         */
        SearchPanel personAddPanel = new SearchPanel();
        personAddPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        HTML addText = new HTML("Add");
        this.add(personAddPanel, addText);

        this.selectTab(0);
    }
}
