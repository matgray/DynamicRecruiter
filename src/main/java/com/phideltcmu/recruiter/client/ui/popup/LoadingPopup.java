/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.ui.popup;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoadingPopup extends PopupPanel {
    public LoadingPopup() {
        super();
        VerticalPanel vp = new VerticalPanel();
        Image i = new Image("images/ajax-loader.gif");
        vp.add(i);
        vp.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
        vp.setWidth("100%");
        this.add(vp);
        this.setGlassEnabled(true);
        vp.setStyleName("cent");
    }
}
