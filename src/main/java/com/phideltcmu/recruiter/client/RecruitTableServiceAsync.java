package com.phideltcmu.recruiter.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>RecruitTableService</code>.
 */
public interface RecruitTableServiceAsync {
    void greetServer(String input, AsyncCallback<String> callback)
            throws IllegalArgumentException;
}
