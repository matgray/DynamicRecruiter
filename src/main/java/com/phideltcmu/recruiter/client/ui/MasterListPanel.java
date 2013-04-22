package com.phideltcmu.recruiter.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.event.CategoriesFetchedEvent;
import com.phideltcmu.recruiter.client.event.CategoriesFetchedEventHandler;
import com.phideltcmu.recruiter.client.handler.RecruitListLoadHandler;
import com.phideltcmu.recruiter.client.ui.table.RecruitTable;
import com.phideltcmu.recruiter.shared.model.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterListPanel extends HorizontalPanel implements CategoriesFetchedEventHandler {
    Button refreshButton = new Button("Refresh Table");
    final Map<String, Boolean> filters = new HashMap<String, Boolean>();
    VerticalPanel vp = new VerticalPanel();

    public MasterListPanel() {
        super();
        refreshButton.setWidth("100%");
        this.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        this.add(new RecruitTable());

        DynamicRecruiter.GLOBAL_EVENT_BUS.addHandler(CategoriesFetchedEvent.TYPE, this);

        refreshButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if (filters.size() == 0) {
                    Window.alert("Categories not yet known");
                    return;
                }
                List<Category> categories = new ArrayList<Category>();
                for (String s : filters.keySet()) {
                    if (filters.get(s)) {
                        categories.add(new Category(s));
                    }
                }
                DynamicRecruiter.RECRUIT_SERVICE.getRecruitList(categories, new RecruitListLoadHandler());
            }
        });

        DynamicRecruiter.RECRUIT_SERVICE.getCategories(new AsyncCallback<List<Category>>() {
            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(List<Category> categories) {
                DynamicRecruiter.GLOBAL_EVENT_BUS.fireEvent(new CategoriesFetchedEvent(categories));
            }
        });
    }

    @Override
    public void onCategoriesFetched(CategoriesFetchedEvent event) {
        Label label = new Label("Visible Categories");
        vp.add(label);
        vp.add(new InlineHTML("<br><hr><br>"));
        for (final Category c : event.getCategoryList()) {
            final String categoryName = c.getValue();
            CheckBox checkBox = new CheckBox(categoryName);
            checkBox.setEnabled(true);
            checkBox.setValue(true);
            filters.put(c.getValue(), true);

            checkBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                @Override
                public void onValueChange(ValueChangeEvent<Boolean> booleanValueChangeEvent) {
                    filters.put(c.getValue(), !filters.get(categoryName));
                }
            });
            vp.add(checkBox);
        }
        vp.add(new InlineHTML("<br><hr><br>"));
        vp.add(refreshButton);
        this.add(vp);
        DynamicRecruiter.RECRUIT_SERVICE.getRecruitList(event.getCategoryList(), new RecruitListLoadHandler());
    }
}
