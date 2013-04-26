/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.handler;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.event.UserDeletedEvent;

public class RemoveUserHandler implements AsyncCallback<Void> {
    @Override
    public void onFailure(Throwable throwable) {
    }

    @Override
    public void onSuccess(Void aVoid) {
        DynamicRecruiter.GLOBAL_EVENT_BUS.fireEvent(new UserDeletedEvent());
    }
}
