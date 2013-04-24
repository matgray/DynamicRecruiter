/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.server.auth;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Group;
import com.restfb.types.User;

public class Facebook {

    public static User getUser(String token) {
        FacebookClient facebookClient = new DefaultFacebookClient(token);
        User user = facebookClient.fetchObject("me", User.class);
        return user;
    }

    public static boolean isValid(String token) {
        FacebookClient facebookClient = new DefaultFacebookClient(token);
        Group g = facebookClient.fetchObject("340434239379682", Group.class);
        return true;
    }
}
