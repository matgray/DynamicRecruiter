package com.phideltcmu.recruiter.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.phideltcmu.recruiter.client.RecruitTableService;
import com.phideltcmu.recruiter.server.dao.RecruitListDao;
import com.phideltcmu.recruiter.shared.model.Person;
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

    /**
     * Escape an html string. Escaping data received from the client helps to
     * prevent cross-site script vulnerabilities.
     *
     * @param html the html string to escape
     * @return the escaped string
     */
    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
                ">", "&gt;");
    }

    @Override
    public List<Person> getRecruitList() {
        return recruitListDao.selectAll();
    }

    @Override
    public boolean addPerson(Person p) {
        recruitListDao.create(p.getFirstName(), p.getLastName(), p.getAndrewID());
        return true;
    }
}
