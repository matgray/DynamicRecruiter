/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.ui.popup.CategoryNameChooser;
import com.phideltcmu.recruiter.client.ui.popup.LoadingPopup;

public class AdminPanel extends VerticalPanel {
    Button addCategoryButton = new Button("Add new category");
    Button addAdmin = new Button("Add another Admin");
    Button updateList = new Button("Update Database");
    public static final LoadingPopup loadingScreen = new LoadingPopup();

    public AdminPanel() {
        super();
        updateList.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                loadingScreen.center();
                DynamicRecruiter.RECRUIT_SERVICE.updateRecruitList(DynamicRecruiter.authUser.getAuthToken(), new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        loadingScreen.hide();
                        Window.alert(throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(Void aVoid) {
                        loadingScreen.hide();
                        Window.alert("Users Updated");
                    }
                });
            }
        });
        this.add(updateList);
        this.add(addCategoryButton);
        addCategoryButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                new CategoryNameChooser().display();
            }
        });
        this.add(addAdmin);

        addAdmin.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                new AdminPanel();
            }
        });
    }


}
