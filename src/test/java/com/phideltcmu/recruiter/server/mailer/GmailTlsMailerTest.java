package com.phideltcmu.recruiter.server.mailer;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GmailTlsMailerTest {
    @Test
    public void TestSendMail() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("mail/Spring-EmailAuthenticaiton.xml");
        GmailTlsMailer mailer = (GmailTlsMailer) context.getBean("emailAuthentication");
    }
}
