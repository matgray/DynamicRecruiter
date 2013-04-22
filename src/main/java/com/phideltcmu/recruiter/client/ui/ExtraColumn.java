package com.phideltcmu.recruiter.client.ui;

import com.google.gwt.user.cellview.client.Column;

public class ExtraColumn {
    private Column column;
    private String name;

    public ExtraColumn(String name, Column c) {
        this.name = name;
        this.column = c;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
