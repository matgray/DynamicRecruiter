package com.phideltcmu.recruiter.client.ui.popup;

import com.google.gwt.user.client.ui.*;

public class LoadingPopup extends PopupPanel {
    public LoadingPopup() {
        super();
        VerticalPanel vp = new VerticalPanel();
        vp.add(new Image("images/ajax-loader.gif"));
        vp.add(new Label("Loading..."));
        vp.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
        this.add(vp);
        this.setGlassEnabled(true);
    }
}
