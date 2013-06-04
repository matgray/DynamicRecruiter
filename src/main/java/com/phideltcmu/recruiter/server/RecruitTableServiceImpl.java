/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.phideltcmu.recruiter.client.RecruitTableService;
import com.phideltcmu.recruiter.server.auth.Facebook;
import com.phideltcmu.recruiter.server.dao.RecruitListDao;
import com.phideltcmu.recruiter.server.directory.CmuLdap;
import com.phideltcmu.recruiter.server.factory.FacebookUserFactory;
import com.phideltcmu.recruiter.server.mailer.GmailTlsMailer;
import com.phideltcmu.recruiter.shared.model.*;
import com.restfb.types.User;
import com.unboundid.ldap.sdk.LDAPException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RecruitTableServiceImpl extends RemoteServiceServlet implements
        RecruitTableService {

    private ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
    private RecruitListDao recruitListDao = (RecruitListDao) context.getBean("recruitListDao");

    private ApplicationContext emailContext = new ClassPathXmlApplicationContext("Spring-EmailAuthenticaiton.xml");
    private GmailTlsMailer mailer = (GmailTlsMailer) emailContext.getBean("emailAuthentication");

    @Override
    public List<Person> getRecruitList(List<Category> desiredCategories) {
        return recruitListDao.selectAll(desiredCategories);
    }

    @Override
    public String addPerson(Person p, AuthUser user) {
        try {
            /**
             * If already in database
             */
            if (!recruitListDao.add(p, user)) {
                return recruitListDao.addToReferrals(p.getAndrewID(), user.getId());
            }
            return "Added";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "There was a problem";
    }

    @Override
    public List<Person> search(String text) {
        try {
            return CmuLdap.getAttributes(text);
        } catch (LDAPException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AuthUser facebookLogin(String token) {
        if (!Facebook.isValid(token)) {
            throw new IllegalAccessError();
        }
        User fbUser = Facebook.getUser(token);
        AuthUser user = FacebookUserFactory.createAuthUser(fbUser);
        user.setAdmin(recruitListDao.isAdmin(user.getId()));
        user.setAuthToken(token);
        recruitListDao.register(user);
        return user;
    }

    @Override
    public boolean addCategory(String name, String token) {
        ensureAdmin(token);
        return recruitListDao.addCategory(name);
    }

    @Override
    public List<Category> getCategories() {
        return recruitListDao.getCategories();
    }

    @Override
    public void changeCategory(String andrewId, String newCategory, String token) {
        ensureAdmin(token);
        recruitListDao.changeCategory(andrewId, newCategory);
    }

    @Override
    public void removeUser(String andrewID, String token) {
        ensureAdmin(token);
        recruitListDao.delete(andrewID);
    }

    @Override
    public void saveNotes(String andrewID, String notes, String token) {
        ensureAdmin(token);
        recruitListDao.saveNotes(andrewID, notes);
    }

    @Override
    public List<String> internalIDsToNames(List<String> ids) {
        List<String> names = new ArrayList<String>(ids.size());
        for (String id : ids) {
            names.add(recruitListDao.getNameFromInternalID(id));
        }
        return names;
    }

    @Override
    public String setPhoneNumber(String andrewID, String number, String token) {
//        ensureAdmin(token);
        number = number.replaceAll("\\D+", "");
        if (number.length() != 10 && number.length() != 0) {
            throw new IllegalStateException("Illegal Number");
        }
        if (number.length() == 10) {
            number = String.format("(%s) %s-%s", number.substring(0, 3), number.substring(3, 6),
                    number.substring(6, 10));
        }
        recruitListDao.updateTelephone(andrewID, number);
        return number;
    }

    @Override
    public List<InternalUser> getNonAdmins() {
        return recruitListDao.getNonAdmins();
    }

    @Override
    public List<InternalUser> getAdmins() {
        return recruitListDao.getAdmins();
    }

    @Override
    public void setUserAdminLevel(String internalID, boolean b, String token) {
        ensureAdmin(token);
        recruitListDao.setAdmin(internalID, b);
    }

    @Override
    public List<InternalUserStat> getUserStats() {
        List<InternalUserStat> stats = recruitListDao.getStats();

        for (InternalUserStat s : stats) {
            s.setName(internalIDsToNames(Arrays.asList(Integer.toString(s.getInternalID()))).get(0));
        }
        return stats;
    }

    @Override
    public void sendMail(String subject, String message, List<Category> categories, String token) {
        ensureAdmin(token);
        List<Person> emailList = getRecruitList(categories);
        try {
            for (Person p : emailList) {
                mailer.sendMail(emailFromId(p.getAndrewID()), subject, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("There was a problem sending the emails");
        }
    }

    @Override
    public void updateRecruitList(String token) {
        ensureAdmin(token);
        try {
            recruitListDao.updateList();
        } catch (LDAPException e) {
            throw new IllegalStateException("Can not connect to CMU LDAP");
        }
    }

    private String emailFromId(String andrewID) {
        return andrewID + "@andrew.cmu.edu";
    }

    private void ensureAdmin(String token) {
        AuthUser u = facebookLogin(token);
        if (!u.isAdmin()) {
            throw new IllegalStateException("Admin Verification Failed");
        }
    }
}
