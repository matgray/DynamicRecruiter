package com.phideltcmu.recruiter.server.auth;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;

public class Facebook {
    public static User getUser(String token) {
        FacebookClient facebookClient = new DefaultFacebookClient(token);
        User user = facebookClient.fetchObject("me", User.class);
        return user;
    }
}
