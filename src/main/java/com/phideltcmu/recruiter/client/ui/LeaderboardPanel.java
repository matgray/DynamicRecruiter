/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.ui;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.event.StatsFetchedEvent;
import com.phideltcmu.recruiter.client.event.StatsFetchedEventHandler;
import com.phideltcmu.recruiter.shared.model.InternalUserStat;

import java.util.List;

public class LeaderboardPanel extends CellTable<InternalUserStat> implements StatsFetchedEventHandler {
    private TextColumn<InternalUserStat> nameColumn;
    private TextColumn<InternalUserStat> uniqueAdditionsColumn;

    public LeaderboardPanel() {
        super();
        DynamicRecruiter.GLOBAL_EVENT_BUS.addHandler(StatsFetchedEvent.TYPE, this);

        nameColumn = new TextColumn<InternalUserStat>() {
            @Override
            public String getValue(InternalUserStat internalUserStat) {
                return internalUserStat.getName();
            }
        };

        uniqueAdditionsColumn = new TextColumn<InternalUserStat>() {
            @Override
            public String getValue(InternalUserStat internalUserStat) {
                return Integer.toString(internalUserStat.getUniqueAdditions());
            }
        };

        this.addColumn(nameColumn, "Name");
        this.addColumn(uniqueAdditionsColumn, "Unique Additions");

        DynamicRecruiter.RECRUIT_SERVICE.getUserStats(new AsyncCallback<List<InternalUserStat>>() {
            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(List<InternalUserStat> internalUserStats) {
                DynamicRecruiter.GLOBAL_EVENT_BUS.fireEvent(new StatsFetchedEvent(internalUserStats));
            }
        });
    }

    @Override
    public void onStatsFetched(StatsFetchedEvent event) {
        this.setRowData(event.getStats());
    }
}
