/*
 * @author <a href="mailto:matgray@cmu.edu">Mat Gray</a>
 * Licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.phideltcmu.recruiter.shared.model.InternalUserStat;

import java.util.List;

/**
 * Event for when a client creates a game
 */
public class StatsFetchedEvent extends GwtEvent<StatsFetchedEventHandler> {

    public static final Type<StatsFetchedEventHandler> TYPE =
            new Type<StatsFetchedEventHandler>();

    private List<InternalUserStat> stats;

    public StatsFetchedEvent(List<InternalUserStat> list) {
        stats = list;
    }

    public List<InternalUserStat> getStats() {
        return stats;
    }

    @Override
    public Type<StatsFetchedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(StatsFetchedEventHandler statsFetchedEventHandler) {
        statsFetchedEventHandler.onStatsFetched(this);
    }
}