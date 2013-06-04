/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

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

    String addPerson(Person p, AuthUser user);

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

    void sendMail(String subject, String message, List<Category> categories, String token);

    void updateRecruitList(String token);
}
