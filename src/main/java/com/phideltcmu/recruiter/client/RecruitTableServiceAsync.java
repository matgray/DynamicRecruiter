package com.phideltcmu.recruiter.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.phideltcmu.recruiter.shared.model.AuthUser;
import com.phideltcmu.recruiter.shared.model.Person;

import java.util.List;

/**
 * The async counterpart of <code>RecruitTableService</code>.
 */
public interface RecruitTableServiceAsync {
    void getRecruitList(AsyncCallback<List<Person>> async);

    void addPerson(Person p, AuthUser user, AsyncCallback<Boolean> async);

    void search(String text, AsyncCallback<List<Person>> async);

    void facebookLogin(String token, AsyncCallback<AuthUser> async);
}
