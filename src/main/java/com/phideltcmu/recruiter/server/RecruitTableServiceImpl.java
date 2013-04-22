package com.phideltcmu.recruiter.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.phideltcmu.recruiter.client.RecruitTableService;
import com.phideltcmu.recruiter.server.auth.Facebook;
import com.phideltcmu.recruiter.server.dao.RecruitListDao;
import com.phideltcmu.recruiter.server.directory.CmuLdap;
import com.phideltcmu.recruiter.server.factory.FacebookUserFactory;
import com.phideltcmu.recruiter.shared.model.AuthUser;
import com.phideltcmu.recruiter.shared.model.Category;
import com.phideltcmu.recruiter.shared.model.Person;
import com.restfb.types.User;
import com.unboundid.ldap.sdk.LDAPException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RecruitTableServiceImpl extends RemoteServiceServlet implements
        RecruitTableService {
    private ApplicationContext context = new ClassPathXmlApplicationContext("/spring/Spring-Module.xml");

    private RecruitListDao recruitListDao = (RecruitListDao) context.getBean("recruitListDao");

    @Override
    public List<Person> getRecruitList(List<Category> desiredCategories) {
        try {
            return recruitListDao.selectAll(desiredCategories);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addPerson(Person p, AuthUser user) {
        try {
            if (!recruitListDao.add(p, user)) {
                recruitListDao.addToReferrals(p.getAndrewID(), user.getId());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
    public AuthUser facebookLogin(String token) throws Exception {
        Facebook.isValid(token);
        User fbUser = Facebook.getUser(token);
        return FacebookUserFactory.createAuthUser(fbUser);
    }

    @Override
    public boolean addCategory(String name) {
        //TODO: AUTHENTICATE ADMIN
        return recruitListDao.addCategory(name);
    }

    @Override
    public List<Category> getCategories() {
        return recruitListDao.getCategories();
    }

    @Override
    public void changeCategory(String andrewId, String newCategory, String token) {
        //TODO: AUTHENTICATE ADMIN
        recruitListDao.changeCategory(andrewId, newCategory);
    }

    @Override
    public void removeUser(String andrewID, String token) {
        //TODO: AUTHENTICATE ADMIN
        recruitListDao.delete(andrewID);
    }

    @Override
    public void saveNotes(String andrewID, String notes, String token) {
        //TODO: MAYBE AUTHENTICATE ADMIN
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
        //TODO: MAYBE AUTHENTICATE ADMIN?
        number = number.replaceAll("\\D+", "");
        if (number.length() != 10) {
            throw new IllegalStateException("Illegal Number");
        }
        number = String.format("(%s) %s-%s", number.substring(0, 3), number.substring(3, 6),
                number.substring(6, 10));
        recruitListDao.updateTelephone(andrewID, number);
        return number;
    }
}
