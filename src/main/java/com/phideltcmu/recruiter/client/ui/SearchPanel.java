/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.*;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.event.SearchCompletedEvent;
import com.phideltcmu.recruiter.client.event.SearchCompletedEventHandler;
import com.phideltcmu.recruiter.client.handler.SearchDirectoryHandler;
import com.phideltcmu.recruiter.client.ui.popup.SearchingPopup;
import com.phideltcmu.recruiter.client.ui.table.SearchMatchTable;

public class SearchPanel extends VerticalPanel implements SearchCompletedEventHandler {
    Button searchButton = new Button("Search");
    TextBox searchField = new TextBox();
    private SimpleEventBus privateEventBus = new SimpleEventBus();
    private SearchMatchTable table = new SearchMatchTable();
    private Label noResults = new Label("Your query returned no results");
    private static SearchingPopup searchingPopup = new SearchingPopup();

    public SearchPanel() {
        privateEventBus.addHandler(SearchCompletedEvent.TYPE, this);
        this.setHeight("100%");
        this.setWidth("100%");
        this.setStyleName("cent");
        this.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);

        this.add(new InlineHTML("<br>"));

        FlexTable layout = new FlexTable();
        layout.setCellSpacing(6);
        FlexTable.FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

        layout.setHTML(0, 0, "Find a person");
        cellFormatter.setColSpan(0, 0, 2);
        cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);

        layout.setHTML(1, 0, "Search Text");
        layout.setWidget(1, 1, searchField);

        layout.setWidget(2, 0, searchButton);
        cellFormatter.setColSpan(2, 0, 2);
        cellFormatter.setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);

        searchButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                searchingPopup.center();
                DynamicRecruiter.RECRUIT_SERVICE.search(searchField.getText(), new SearchDirectoryHandler(privateEventBus));
                noResults.setVisible(false);
            }
        });

        DecoratorPanel infoPanel = new DecoratorPanel();
        infoPanel.setWidget(layout);
        this.add(infoPanel);
        this.add(new InlineHTML("<br><br>"));
        this.add(table);

        noResults.setStyleName("gwt-Label-red");
        this.add(noResults);
        this.table.setVisible(false);
        this.noResults.setVisible(false);
    }

    @Override
    public void onSearchCompleted(SearchCompletedEvent event) {
        if (event.getPersonList().size() == 0) {
            this.noResults.setVisible(true);
            table.setVisible(false);
            searchingPopup.hide();
        } else {
            table.setVisible(true);
            table.setData(event.getPersonList());
            searchingPopup.hide();
        }
    }
}
