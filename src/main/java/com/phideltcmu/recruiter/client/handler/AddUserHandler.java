/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.handler;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;

public class AddUserHandler implements AsyncCallback<String> {
    private Label statusLabel;

    public AddUserHandler(Label statusLabel) {
        this.statusLabel = statusLabel;
    }

    @Override
    public void onFailure(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onSuccess(String s) {
        this.statusLabel.setText(this.statusLabel.getText() + " " + s);
    }
}
