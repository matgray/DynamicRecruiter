/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.phideltcmu.recruiter.shared.model.*;

import java.util.List;

/**
 * The async counterpart of <code>RecruitTableService</code>.
 */
public interface RecruitTableServiceAsync {
    void getRecruitList(List<Category> desiredCategorie, AsyncCallback<List<Person>> async);

    void addPerson(Person p, AuthUser user, AsyncCallback<Boolean> async);

    void search(String text, AsyncCallback<List<Person>> async);

    void facebookLogin(String token, AsyncCallback<AuthUser> async);

    void addCategory(String name, String token, AsyncCallback<Boolean> async);

    void getCategories(AsyncCallback<List<Category>> async);

    void changeCategory(String andrewId, String newCategory, String token, AsyncCallback<Void> async);

    void removeUser(String andrewID, String token, AsyncCallback<Void> async);

    void saveNotes(String andrewID, String notes, String token, AsyncCallback<Void> async);

    void internalIDsToNames(List<String> ids, AsyncCallback<List<String>> async);

    void setPhoneNumber(String andrewID, String number, String token, AsyncCallback<String> async);

    void getAdmins(AsyncCallback<List<InternalUser>> async);

    void getNonAdmins(AsyncCallback<List<InternalUser>> async);

    void setUserAdminLevel(String internalID, boolean b, String token, AsyncCallback<Void> async);

    void getUserStats(AsyncCallback<List<InternalUserStat>> async);

    void sendMail(String subject, String message, List<Category> categories, String token, AsyncCallback<Void> async);
}
