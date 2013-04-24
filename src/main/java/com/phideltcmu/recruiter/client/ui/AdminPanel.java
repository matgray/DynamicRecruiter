/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.phideltcmu.recruiter.client.ui.popup.CategoryNameChooser;

public class AdminPanel extends VerticalPanel {
    Button addCategoryButton = new Button("Add new category");
    Button addAdmin = new Button("Add another Admin");

    public AdminPanel() {
        super();
        this.add(addCategoryButton);
        addCategoryButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                new CategoryNameChooser().display();
            }
        });

//        this.add(addAdmin);
//
//        addAdmin.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent clickEvent) {
//                new AdminPanel();
//            }
//        });
    }


}
