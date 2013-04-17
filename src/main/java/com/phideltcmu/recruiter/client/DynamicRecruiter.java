package com.phideltcmu.recruiter.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.phideltcmu.recruiter.client.handler.RecruitListLoadHandler;
import com.phideltcmu.recruiter.client.ui.PersonAddPanel;
import com.phideltcmu.recruiter.client.ui.RecruitTable;
import com.phideltcmu.recruiter.client.ui.WelcomePanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DynamicRecruiter implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";

    /**
     * Create a remote service proxy to talk to the server-side RecruitTable service.
     */
    public static final RecruitTableServiceAsync RECRUIT_SERVICE = GWT.create(RecruitTableService.class);

    public static final SimpleEventBus GLOBAL_EVENT_BUS = new SimpleEventBus();

    private TabPanel tabPanel = new TabPanel();


    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        RootPanel.get("listContainer").add(tabPanel);
        tabPanel.setWidth("100%");
        tabPanel.setHeight("100%");

        /**
         * Add a welcome Tab
         */
        WelcomePanel welcomePanel = new WelcomePanel();
        HTML welcomeTabText = new HTML("Home");
        tabPanel.add(welcomePanel, welcomeTabText);

        /**
         * Add a table tab
         */
        RecruitTable table = new RecruitTable();
        HTML tableText = new HTML("Table");
        tabPanel.add(table, tableText);
        RECRUIT_SERVICE.getRecruitList(new RecruitListLoadHandler());

        /**
         * Add a tab for adding people
         */
        PersonAddPanel personAddPanel = new PersonAddPanel();
        HTML addText = new HTML("Add");
        tabPanel.add(personAddPanel, addText);

        tabPanel.selectTab(0);
    }
}
