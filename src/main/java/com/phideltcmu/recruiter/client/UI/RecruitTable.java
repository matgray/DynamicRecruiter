package com.phideltcmu.recruiter.client.UI;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;

public class RecruitTable {
    interface RecruitTableUiBinder extends UiBinder<DivElement, RecruitTable> {
    }

    private static RecruitTableUiBinder ourUiBinder = GWT.create(RecruitTableUiBinder.class);

    public RecruitTable() {
        DivElement rootElement = ourUiBinder.createAndBindUi(this);

    }
}