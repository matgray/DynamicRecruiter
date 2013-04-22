package com.phideltcmu.recruiter.client.handler;

import com.google.api.gwt.oauth2.client.Callback;
import com.phideltcmu.recruiter.client.DynamicRecruiter;

public class AuthenticationHandler implements Callback<String, Throwable> {
    @Override
    public void onFailure(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onSuccess(String token) {
        DynamicRecruiter.RECRUIT_SERVICE.facebookLogin(token, new UserFetchedEventHandler());
    }
}
