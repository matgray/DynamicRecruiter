package com.phideltcmu.recruiter.client.handler;

import com.google.api.gwt.oauth2.client.Callback;
import com.google.gwt.user.client.Window;
import com.phideltcmu.recruiter.client.DynamicRecruiter;

public class AuthenticationHandler implements Callback<String, Throwable> {
    @Override
    public void onFailure(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onSuccess(String token) {
        Window.alert("Got an OAuth token:\n" + token + "\n");
        DynamicRecruiter.RECRUIT_SERVICE.facebookLogin(token, new UserFetchedEventHandler());
    }
}
