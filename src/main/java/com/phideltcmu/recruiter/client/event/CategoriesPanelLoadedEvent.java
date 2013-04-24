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
public class CategoriesPanelLoadedEvent extends GwtEvent<CategoriesPanelLoadedEventHandler> {
    List<Category> categoryList;

    public CategoriesPanelLoadedEvent(List<Category> categories) {
        this.categoryList = categories;
    }

    public static final Type<CategoriesPanelLoadedEventHandler> TYPE =
            new Type<CategoriesPanelLoadedEventHandler>();

    @Override
    public Type<CategoriesPanelLoadedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(CategoriesPanelLoadedEventHandler handler) {
        handler.onCategoryPanelLoaded(this);
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }
}