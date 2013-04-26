/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.event.CategoriesPanelLoadedEvent;
import com.phideltcmu.recruiter.client.event.CategoriesPanelLoadedEventHandler;
import com.phideltcmu.recruiter.client.event.UserDeletedEvent;
import com.phideltcmu.recruiter.client.event.UserDeletedEventHandler;
import com.phideltcmu.recruiter.client.handler.RecruitListLoadHandler;
import com.phideltcmu.recruiter.client.ui.table.RecruitTable;
import com.phideltcmu.recruiter.shared.model.Category;

import java.util.List;

public class MasterListPanel extends HorizontalPanel implements CategoriesPanelLoadedEventHandler, UserDeletedEventHandler {
    private Button refreshButton = new Button("Refresh Table");
    private VerticalPanel vp = new VerticalPanel();
    private SimpleEventBus privateEventBus = new SimpleEventBus();
    private CategoriesPanel categoriesPanel = new CategoriesPanel(privateEventBus, "Visible Categories", true);
    private RecruitTable recruitTable = new RecruitTable();
    private List<Category> lastCategories = null;

    public MasterListPanel() {
        super();
        refreshButton.setWidth("100%");
        this.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        this.add(recruitTable);
        vp.add(categoriesPanel);
        vp.add(refreshButton);
        this.add(vp);

        privateEventBus.addHandler(CategoriesPanelLoadedEvent.TYPE, this);
        DynamicRecruiter.GLOBAL_EVENT_BUS.addHandler(UserDeletedEvent.TYPE, this);

        refreshButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if (categoriesPanel.getCheckBooleanMap().size() == 0) {
                    Window.alert("Categories not yet known");
                    return;
                }
                List<Category> categories = CategoriesPanel.filterCategories(categoriesPanel.getCheckBooleanMap());
                DynamicRecruiter.RECRUIT_SERVICE.getRecruitList(categories, new RecruitListLoadHandler());
                lastCategories = categories;
            }
        });
    }

    @Override
    public void onCategoryPanelLoaded(CategoriesPanelLoadedEvent event) {
        categoriesPanel.setCategory("Brother", false);
        lastCategories = CategoriesPanel.filterCategories(categoriesPanel.getCheckBooleanMap());
        DynamicRecruiter.RECRUIT_SERVICE.getRecruitList(lastCategories, new RecruitListLoadHandler());
        recruitTable.render(event.getCategoryList());
    }

    public void refresh() {
        DynamicRecruiter.RECRUIT_SERVICE.getRecruitList(lastCategories, new RecruitListLoadHandler());
    }

    @Override
    public void onUserDeleted() {
        refresh();
    }
}
