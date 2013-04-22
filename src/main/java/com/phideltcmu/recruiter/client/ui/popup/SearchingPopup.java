package com.phideltcmu.recruiter.client.ui.popup;

import com.google.gwt.user.client.ui.*;

public class SearchingPopup extends PopupPanel {
    public SearchingPopup() {
        super();
        VerticalPanel vp = new VerticalPanel();
        vp.add(new Image("images/ajax-loader2.gif"));
        vp.add(new Label("Querying The CMU Directory..."));
        vp.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
        vp.setWidth("100%");
        this.add(vp);
        this.setGlassEnabled(true);
    }
}
