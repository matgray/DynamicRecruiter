package com.phideltcmu.recruiter.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.*;
import com.phideltcmu.recruiter.client.event.FacebookUserFetchedEvent;
import com.phideltcmu.recruiter.client.event.FacebookUserFetchedEventHandler;
import com.phideltcmu.recruiter.client.ui.LoginPanel;
import com.phideltcmu.recruiter.client.ui.TabMenu;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DynamicRecruiter implements EntryPoint, FacebookUserFetchedEventHandler {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";

    /**
     * Create a remote service proxy to talk to the server-side PersonTable service.
     */
    public static final RecruitTableServiceAsync RECRUIT_SERVICE = GWT.create(RecruitTableService.class);

    public static final SimpleEventBus GLOBAL_EVENT_BUS = new SimpleEventBus();

    private LoginPanel loginPanel = new LoginPanel();
    private Label topLeftText = new Label("Phi Delta Theta of Carnegie Mellon");
    private Label topLeftText2 = new Label("Dynamic Recruitment Manager");
    private StateManager stateManager;

    private void registerWithGlobalBus() {
        DynamicRecruiter.GLOBAL_EVENT_BUS.addHandler(FacebookUserFetchedEvent.TYPE, this);
    }

    private void promptLogin() {
        VerticalPanel vp = new VerticalPanel();
        /**
         * Center popup
         */
        vp.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
        vp.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
        topLeftText.setStyleName("oldenburg150");
        vp.add(loginPanel);
        vp.setStyleName("cent");
        stateManager.switchToWidget(vp);
    }

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        registerWithGlobalBus();
        RootPanel.get("top-left").add(topLeftText);
        RootPanel.get("top-left").add(topLeftText2);
        DeckPanel stateDeck = new DeckPanel();
        stateDeck.setWidth("100%");
        stateDeck.setHeight("100%");
        stateManager = new StateManager(stateDeck);
        RootPanel.get("container").add(stateDeck);
        promptLogin();
    }

    @Override
    public void onLoginSuccess(FacebookUserFetchedEvent event) {
        RootPanel.get("top-left").remove(topLeftText);
        RootPanel.get("top-left").remove(topLeftText2);
        TabMenu tabMenu = new TabMenu(event.getFacebookPerson());
        tabMenu.setStyleName("fadeMe");
        stateManager.switchToWidget(tabMenu);
    }
}
