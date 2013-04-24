package com.phideltcmu.recruiter.client.ui;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.event.CategoriesFetchedEvent;
import com.phideltcmu.recruiter.client.event.CategoriesFetchedEventHandler;
import com.phideltcmu.recruiter.client.event.CategoriesPanelLoadedEvent;
import com.phideltcmu.recruiter.shared.model.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriesPanel extends VerticalPanel implements CategoriesFetchedEventHandler {
    private SimpleEventBus privateEventBus = new SimpleEventBus();
    private Map<String, Boolean> checkMap = new HashMap<String, Boolean>();
    private SimpleEventBus postFetchFireBus;
    private Label header;
    private boolean defaultCheck;

    public CategoriesPanel(SimpleEventBus postFetchFireBus, String headerText, boolean defaultCheck) {
        super();
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
        VerticalPanel vp = new VerticalPanel();
        vp.add(header);
        vp.add(new InlineHTML("<br><hr><br>"));
        for (final Category c : event.getCategoryList()) {
            final String categoryName = c.getValue();
            CheckBox checkBox = new CheckBox(categoryName);
            checkBox.setEnabled(true);
            checkBox.setValue(defaultCheck);
            checkMap.put(c.getValue(), true);

            checkBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                @Override
                public void onValueChange(ValueChangeEvent<Boolean> booleanValueChangeEvent) {
                    checkMap.put(c.getValue(), !checkMap.get(categoryName));
                }
            });
            vp.add(checkBox);
        }
        vp.add(new InlineHTML("<br><hr><br>"));
        this.add(vp);
        postFetchFireBus.fireEvent(new CategoriesPanelLoadedEvent(event.getCategoryList()));
    }

    public Map<String, Boolean> getCheckMap() {
        return checkMap;
    }
}
