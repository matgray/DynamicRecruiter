package com.phideltcmu.recruiter.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("recruitModify")
public interface RecruitTableService extends RemoteService {
    String greetServer(String name) throws IllegalArgumentException;
}
