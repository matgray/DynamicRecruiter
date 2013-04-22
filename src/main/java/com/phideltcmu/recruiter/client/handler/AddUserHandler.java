package com.phideltcmu.recruiter.client.handler;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AddUserHandler implements AsyncCallback<Boolean> {
    @Override
    public void onFailure(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onSuccess(Boolean aBoolean) {
        Window.alert("ADDED: " + aBoolean.toString());
    }
}
