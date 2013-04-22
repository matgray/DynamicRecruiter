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
