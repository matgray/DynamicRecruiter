/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.handler;

import com.google.api.gwt.oauth2.client.Callback;
import com.google.gwt.user.client.Window;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.ui.popup.LoadingPopup;

public class AuthenticationHandler implements Callback<String, Throwable> {
    public static final LoadingPopup loadingScreen = new LoadingPopup();

    public AuthenticationHandler() {
        loadingScreen.center();
    }

    @Override
    public void onFailure(Throwable throwable) {
        loadingScreen.hide();
        throwable.printStackTrace();
        Window.alert("There was an error authenticating");
    }

    @Override
    public void onSuccess(String token) {
        DynamicRecruiter.RECRUIT_SERVICE.facebookLogin(token, new UserFetchedEventHandler());
    }
}
