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
import com.phideltcmu.recruiter.shared.model.Person;

import java.util.List;

/**
 * Event for when a client creates a game
 */
public class SearchCompletedEvent extends GwtEvent<SearchCompletedEventHandler> {
    List<Person> personList;

    public List<Person> getPersonList() {
        return personList;
    }

    public SearchCompletedEvent(List<Person> list) {
        this.personList = list;
    }

    public static final Type<SearchCompletedEventHandler> TYPE =
            new Type<SearchCompletedEventHandler>();

    @Override
    public Type<SearchCompletedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SearchCompletedEventHandler handler) {
        handler.onSearchCompleted(this);
    }
}