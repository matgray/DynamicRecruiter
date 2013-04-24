/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.handler;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.phideltcmu.recruiter.client.event.SearchCompletedEvent;
import com.phideltcmu.recruiter.shared.model.Person;

import java.util.List;

public class SearchDirectoryHandler implements AsyncCallback<List<Person>> {
    private SimpleEventBus eventBus;

    public SearchDirectoryHandler(SimpleEventBus fireBus) {
        this.eventBus = fireBus;
    }

    @Override
    public void onFailure(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onSuccess(List<Person> persons) {
        eventBus.fireEvent(new SearchCompletedEvent(persons));
    }
}
