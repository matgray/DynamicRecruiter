/*
 * @author <a href="mailto:matgray@cmu.edu">Mat Gray</a>
 * Licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event for when a client creates a game
 */
public class RecruitTableFetchedEvent extends GwtEvent<RecruitTableFetchedEventHandler> {

    public static final Type<RecruitTableFetchedEventHandler> TYPE =
            new Type<RecruitTableFetchedEventHandler>();

    @Override
    public Type<RecruitTableFetchedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(RecruitTableFetchedEventHandler handler) {
        handler.onRecruitTableFetched(this);
    }
}