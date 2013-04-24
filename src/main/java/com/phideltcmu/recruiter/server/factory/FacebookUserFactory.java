/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.server.factory;

import com.phideltcmu.recruiter.shared.model.AuthUser;
import com.restfb.types.User;

public class FacebookUserFactory {

    public static AuthUser createAuthUser(User user) {
        AuthUser authUser = new AuthUser();
        authUser.setFirstName(user.getFirstName());
        authUser.setLastName(user.getLastName());
        authUser.setId(user.getId());
        return authUser;
    }
}
