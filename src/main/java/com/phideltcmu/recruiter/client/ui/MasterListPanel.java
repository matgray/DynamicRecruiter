package com.phideltcmu.recruiter.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
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

public class MasterListPanel extends VerticalPanel implements CategoriesFetchedEventHandler {
    Button refreshButton = new Button("Refresh Table");
    Map<Category, Boolean> filters = new HashMap<Category, Boolean>();
    HorizontalPanel hp = new HorizontalPanel();

    public MasterListPanel() {
        super();
        refreshButton.setWidth("100%");
        this.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        hp.add(refreshButton);
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
                for (Category cat : filters.keySet()) {
                    if (filters.get(cat)) {
                        categories.add(cat);
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
        for (final Category c : event.getCategoryList()) {
            final String categoryName = c.getValue();
            CheckBox checkBox = new CheckBox(categoryName);
            checkBox.setEnabled(true);
            checkBox.setValue(true);
            filters.put(c, true);

            checkBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                @Override
                public void onValueChange(ValueChangeEvent<Boolean> booleanValueChangeEvent) {
                    filters.put(c, !filters.get(categoryName));
                }
            });
            hp.add(checkBox);
        }
        this.add(hp);
    }
}
