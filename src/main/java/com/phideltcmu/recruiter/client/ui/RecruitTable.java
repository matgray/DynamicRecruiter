package com.phideltcmu.recruiter.client.ui;

import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.event.RecruitTableFetchedEvent;
import com.phideltcmu.recruiter.client.event.RecruitTableFetchedEventHandler;

public class RecruitTable extends PersonTable implements RecruitTableFetchedEventHandler {
    public RecruitTable() {
        super();
        DynamicRecruiter.GLOBAL_EVENT_BUS.addHandler(RecruitTableFetchedEvent.TYPE, this);
    }

    @Override
    public void onRecruitTableFetched(RecruitTableFetchedEvent event) {
        this.setData(event.getPersonList());
    }
}
