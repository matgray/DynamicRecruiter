/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.ui;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.event.CategoriesFetchedEvent;
import com.phideltcmu.recruiter.client.event.CategoriesFetchedEventHandler;
import com.phideltcmu.recruiter.client.event.CategoriesPanelLoadedEvent;
import com.phideltcmu.recruiter.shared.model.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriesPanel extends VerticalPanel implements CategoriesFetchedEventHandler {
    private SimpleEventBus privateEventBus = new SimpleEventBus();
    private Map<String, Boolean> checkBooleanMap = new HashMap<String, Boolean>();
    private Map<String, CheckBox> checkMap = new HashMap<String, CheckBox>();
    private SimpleEventBus postFetchFireBus;
    private Label header;
    private boolean defaultCheck;
    private Image loading = new Image("images/ajax-loader.gif");

    public CategoriesPanel(SimpleEventBus postFetchFireBus, String headerText, boolean defaultCheck) {
        super();
        this.add(loading);
        privateEventBus.addHandler(CategoriesFetchedEvent.TYPE, this);
        this.postFetchFireBus = postFetchFireBus;
        this.header = new Label(headerText);
        this.defaultCheck = defaultCheck;

        DynamicRecruiter.RECRUIT_SERVICE.getCategories(new AsyncCallback<List<Category>>() {
            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(List<Category> categories) {
                privateEventBus.fireEvent(new CategoriesFetchedEvent(categories));
            }
        });
    }

    @Override
    public void onCategoriesFetched(CategoriesFetchedEvent event) {
        this.remove(loading);
        VerticalPanel vp = new VerticalPanel();
        vp.add(header);
        vp.add(new InlineHTML("<br><hr><br>"));
        for (final Category c : event.getCategoryList()) {
            final String categoryName = c.getValue();
            CheckBox checkBox = new CheckBox(categoryName);
            checkBox.setEnabled(true);
            checkBox.setValue(defaultCheck);
            checkBooleanMap.put(categoryName, defaultCheck);
            checkMap.put(c.getValue(), checkBox);

            checkBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                @Override
                public void onValueChange(ValueChangeEvent<Boolean> booleanValueChangeEvent) {
                    checkBooleanMap.put(categoryName, !checkBooleanMap.get(categoryName));
                }
            });
            vp.add(checkBox);
        }
        vp.add(new InlineHTML("<br><hr><br>"));
        this.add(vp);
        postFetchFireBus.fireEvent(new CategoriesPanelLoadedEvent(event.getCategoryList()));
    }

    public Map<String, Boolean> getCheckBooleanMap() {
        return checkBooleanMap;
    }

    public void setCategory(String s, Boolean b) {
        CheckBox c = checkMap.get(s);
        if (c != null) {
            c.setValue(b);
            checkBooleanMap.put(s, b);
        }
    }

    public static List<Category> filterCategories(Map<String, Boolean> booleanMap) {
        List<Category> categories = new ArrayList<Category>();
        for (String s : booleanMap.keySet()) {
            if (booleanMap.get(s)) {
                categories.add(new Category(s));
            }
        }
        return categories;
    }
}
