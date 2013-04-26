/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

/*
 * @author <a href="mailto:matgray@cmu.edu">Mat Gray</a>
 * Licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event for when a client creates a game
 */
public class UserDeletedEvent extends GwtEvent<UserDeletedEventHandler> {
    public static final Type<UserDeletedEventHandler> TYPE =
            new Type<UserDeletedEventHandler>();

    @Override
    public Type<UserDeletedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(UserDeletedEventHandler handler) {
        handler.onUserDeleted();
    }
}