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
import com.phideltcmu.recruiter.shared.model.Category;

import java.util.List;

/**
 * Event for when a client creates a game
 */
public class CategoriesFetchedEvent extends GwtEvent<CategoriesFetchedEventHandler> {
    List<Category> categoryList;

    public CategoriesFetchedEvent(List<Category> categories) {
        this.categoryList = categories;
    }

    public static final Type<CategoriesFetchedEventHandler> TYPE =
            new Type<CategoriesFetchedEventHandler>();

    @Override
    public Type<CategoriesFetchedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(CategoriesFetchedEventHandler handler) {
        handler.onCategoriesFetched(this);
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }
}