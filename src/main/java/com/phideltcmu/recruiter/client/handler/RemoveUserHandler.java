package com.phideltcmu.recruiter.client.handler;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RemoveUserHandler implements AsyncCallback<Void> {
    @Override
    public void onFailure(Throwable throwable) {
    }

    @Override
    public void onSuccess(Void aVoid) {
        Window.alert("Delete Successful!");
    }
}
