package com.phideltcmu.recruiter.client.event;/*
 * @author <a href="mailto:matgray@cmu.edu">Mat Gray</a>
 * Licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */


import com.google.gwt.event.shared.EventHandler;

/**
 * The handler for the RecruitTableFetchedEvent
 */
public interface RecruitTableFetchedEventHandler extends EventHandler {
    void onRecruitTableFetched(RecruitTableFetchedEvent event);
}
