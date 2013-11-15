/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.ui;


import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TabPanel;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
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
        final MasterListPanel masterListPanel = new MasterListPanel();
        HTML tableText = new HTML("Table");
        this.add(masterListPanel, tableText);

        /**
         * Add a tab for adding people
         */
        SearchPanel personAddPanel = new SearchPanel();
        personAddPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        HTML addText = new HTML("Add");
        this.add(personAddPanel, addText);

        /**
         * Add a leaderboard
         */
        final LeaderboardPanel leaderboardPanel = new LeaderboardPanel();
        HTML leaderboardText = new HTML("Leaderboard");
        this.add(leaderboardPanel, leaderboardText);

        /**
         * Add an admin tab
         */
        if (DynamicRecruiter.authUser.isAdmin()) {
            AdminPanel adminPanel = new AdminPanel();
            HTML adminText = new HTML("Admin");
            this.add(adminPanel, adminText);
        }

        /**
         * Add a email tab if admin
         */
        if (DynamicRecruiter.authUser.isAdmin()) {
            MailerPanel mailerPanel = new MailerPanel();
            HTML mailerText = new HTML("Emailer");
            this.add(mailerPanel, mailerText);
        }

        this.selectTab(0);

        this.addSelectionHandler(new SelectionHandler<Integer>() {
            @Override
            public void onSelection(SelectionEvent<Integer> integerSelectionEvent) {
                int tabId = integerSelectionEvent.getSelectedItem();
                if (tabId == 1) {
                    masterListPanel.refresh();
                }
            }
        });
    }
}
