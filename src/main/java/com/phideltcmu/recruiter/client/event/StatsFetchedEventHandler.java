/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.event;/*
 * @author <a href="mailto:matgray@cmu.edu">Mat Gray</a>
 * Licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */


import com.google.gwt.event.shared.EventHandler;

/**
 * The handler for the FacebookUserFetchedEvent
 */
public interface StatsFetchedEventHandler extends EventHandler {
    void onStatsFetched(StatsFetchedEvent event);
}
