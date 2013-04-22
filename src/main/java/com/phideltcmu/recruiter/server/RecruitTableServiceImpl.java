package com.phideltcmu.recruiter.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.phideltcmu.recruiter.client.RecruitTableService;
import com.phideltcmu.recruiter.server.auth.Facebook;
import com.phideltcmu.recruiter.server.dao.RecruitListDao;
import com.phideltcmu.recruiter.server.directory.CmuLdap;
import com.phideltcmu.recruiter.server.factory.FacebookUserFactory;
import com.phideltcmu.recruiter.shared.model.AuthUser;
import com.phideltcmu.recruiter.shared.model.Person;
import com.restfb.types.User;
import com.unboundid.ldap.sdk.LDAPException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
    public List<Person> getRecruitList() {
        return recruitListDao.selectAll();
    }

    @Override
    public boolean addPerson(Person p) {
        recruitListDao.create(p.getFirstName(), p.getLastName(), p.getAndrewID());
        return true;
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
}
