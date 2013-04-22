package com.phideltcmu.recruiter.client.ui;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.*;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.event.SearchCompletedEvent;
import com.phideltcmu.recruiter.client.event.SearchCompletedEventHandler;
import com.phideltcmu.recruiter.client.handler.AddUserHandler;
import com.phideltcmu.recruiter.client.handler.SearchDirectoryHandler;
import com.phideltcmu.recruiter.client.ui.table.PersonTable;
import com.phideltcmu.recruiter.shared.model.Person;

import java.util.HashMap;
import java.util.Map;

public class SearchPanel extends VerticalPanel implements SearchCompletedEventHandler {
    Button searchButton = new Button("Search");
    TextBox searchField = new TextBox();
    private SimpleEventBus privateEventBus = new SimpleEventBus();
    PersonTable table = new PersonTable();

    public SearchPanel() {
        privateEventBus.addHandler(SearchCompletedEvent.TYPE, this);
        this.setWidth("100%");
        this.setHeight("100%");
        this.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
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
        this.setWidth("100%");
        this.add(infoPanel);

        ButtonCell buttonCell = new ButtonCell() {
            @Override
            public boolean handlesSelection() {
                return false;
            }
        };

        Column<Person, String> addButtonColumn = new Column<Person, String>(buttonCell) {
            @Override
            public String getValue(Person person) {
                return "Add to recruitment list";
            }
        };

        addButtonColumn.setFieldUpdater(new FieldUpdater<Person, String>() {
            @Override
            public void update(int i, Person person, String s) {
                DynamicRecruiter.RECRUIT_SERVICE.addPerson(person,
                        DynamicRecruiter.authUser,
                        new AddUserHandler());
            }
        });

        Map<String, Column<Person, String>> extraCols = new HashMap<String, Column<Person, String>>();
        extraCols.put("", addButtonColumn);

        table.initColumns(extraCols);
        this.add(table);
    }

    @Override
    public void onSearchCompleted(SearchCompletedEvent event) {
        table.setData(event.getPersonList());
    }
}
