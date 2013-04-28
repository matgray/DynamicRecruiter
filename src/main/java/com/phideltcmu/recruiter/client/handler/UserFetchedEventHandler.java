/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.handler;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.event.FacebookUserFetchedEvent;
import com.phideltcmu.recruiter.shared.model.AuthUser;

public class UserFetchedEventHandler implements AsyncCallback<AuthUser> {
    @Override
    public void onFailure(Throwable throwable) {
        throwable.printStackTrace();
        AuthenticationHandler.loadingScreen.hide();
        Window.alert("You are not permitted to use this application.  Contact PDT: PA Rho for assistance");
    }

    @Override
    public void onSuccess(AuthUser user) {
        DynamicRecruiter.GLOBAL_EVENT_BUS.fireEvent(new FacebookUserFetchedEvent(user));
    }
}
