package com.phideltcmu.recruiter.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.*;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.event.SearchCompletedEvent;
import com.phideltcmu.recruiter.client.event.SearchCompletedEventHandler;
import com.phideltcmu.recruiter.client.handler.SearchDirectoryHandler;
import com.phideltcmu.recruiter.client.ui.table.SearchMatchTable;
import com.phideltcmu.recruiter.shared.model.Person;

public class SearchPanel extends VerticalPanel implements SearchCompletedEventHandler {
    Button searchButton = new Button("Search");
    TextBox searchField = new TextBox();
    private SimpleEventBus privateEventBus = new SimpleEventBus();
    private SearchMatchTable table = new SearchMatchTable();

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
                DynamicRecruiter.RECRUIT_SERVICE.search(searchField.getText(), new SearchDirectoryHandler(privateEventBus));
            }
        });

        CellTable<Person> searchResults = new CellTable<Person>();

        DecoratorPanel infoPanel = new DecoratorPanel();
        infoPanel.setWidget(layout);
        this.add(infoPanel);
        this.add(new InlineHTML("<br><br>"));
        this.add(table);
    }

    @Override
    public void onSearchCompleted(SearchCompletedEvent event) {
        table.setData(event.getPersonList());
    }
}
