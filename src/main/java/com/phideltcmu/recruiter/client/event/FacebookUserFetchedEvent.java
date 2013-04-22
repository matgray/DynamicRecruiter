/*
 * @author <a href="mailto:matgray@cmu.edu">Mat Gray</a>
 * Licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.phideltcmu.recruiter.shared.model.AuthUser;

/**
 * Event for when a client creates a game
 */
public class FacebookUserFetchedEvent extends GwtEvent<FacebookUserFetchedEventHandler> {
    AuthUser facebookPerson = null;

    public AuthUser getFacebookPerson() {
        return this.facebookPerson;
    }

    public FacebookUserFetchedEvent(AuthUser person) {
        this.facebookPerson = person;
    }

    public static final Type<FacebookUserFetchedEventHandler> TYPE =
            new Type<FacebookUserFetchedEventHandler>();

    @Override
    public Type<FacebookUserFetchedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(FacebookUserFetchedEventHandler handler) {
        handler.onLoginSuccess(this);
    }
}