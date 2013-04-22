package com.phideltcmu.recruiter.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.phideltcmu.recruiter.shared.model.*;

import java.util.List;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("recruitModify")
public interface RecruitTableService extends RemoteService {
    List<Person> getRecruitList(List<Category> desiredCategories);

    boolean addPerson(Person p, AuthUser user);

    List<Person> search(String text);

    public AuthUser facebookLogin(String token);

    boolean addCategory(String name, String token);

    List<Category> getCategories();

    void changeCategory(String andrewId, String newCategory, String token);

    void removeUser(String andrewID, String token);

    void saveNotes(String andrewID, String notes, String token);

    List<String> internalIDsToNames(List<String> ids);

    String setPhoneNumber(String andrewID, String number, String token);

    List<InternalUser> getNonAdmins();

    List<InternalUser> getAdmins();

    void setUserAdminLevel(String internalID, boolean b, String token);

    List<InternalUserStat> getUserStats();
}
