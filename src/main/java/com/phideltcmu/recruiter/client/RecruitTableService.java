package com.phideltcmu.recruiter.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.phideltcmu.recruiter.shared.model.AuthUser;
import com.phideltcmu.recruiter.shared.model.Person;

import java.util.List;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("recruitModify")
public interface RecruitTableService extends RemoteService {
    List<Person> getRecruitList();

    boolean addPerson(Person p, AuthUser user);

    List<Person> search(String text);

    public AuthUser facebookLogin(String token) throws Exception;
}
