package com.phideltcmu.recruiter.client.ui;

import com.google.gwt.user.client.ui.*;

public class PersonAddPanel extends VerticalPanel {
    public PersonAddPanel() {
        FlexTable layout = new FlexTable();
        layout.setCellSpacing(6);
        FlexTable.FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

        layout.setHTML(0, 0, "Add a Person");
        cellFormatter.setColSpan(0, 0, 2);
        cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);

        layout.setHTML(1, 0, "Name");
        layout.setWidget(1, 1, new TextBox());

        layout.setHTML(2, 0, "Andrew ID");
        layout.setWidget(2, 1, new TextBox());

        DecoratorPanel infoPanel = new DecoratorPanel();
        infoPanel.setWidget(layout);
        this.add(infoPanel);
    }
}
