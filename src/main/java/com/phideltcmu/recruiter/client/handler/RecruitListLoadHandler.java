/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.handler;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.event.RecruitTableFetchedEvent;
import com.phideltcmu.recruiter.shared.model.Person;

import java.util.List;

public class RecruitListLoadHandler implements AsyncCallback<List<Person>> {

    @Override
    public void onFailure(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onSuccess(List<Person> persons) {
        DynamicRecruiter.GLOBAL_EVENT_BUS.fireEvent(new RecruitTableFetchedEvent(persons));
    }
}
