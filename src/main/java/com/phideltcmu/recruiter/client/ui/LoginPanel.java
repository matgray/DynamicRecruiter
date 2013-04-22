package com.phideltcmu.recruiter.client.ui;

import com.google.api.gwt.oauth2.client.Auth;
import com.google.api.gwt.oauth2.client.AuthRequest;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.phideltcmu.recruiter.client.handler.AuthenticationHandler;

public class LoginPanel extends VerticalPanel {
    private final Image shield = new Image("images/pdt-shield.png");
    private final Label warning = new Label("This Application's use is restricted");

    public LoginPanel() {
        super();
        this.setHorizontalAlignment(ALIGN_CENTER);
        warning.setStyleName("gwt-Label-red");
        this.add(warning);
        this.add(shield);
        addFacebookAuth();
        Auth.export();
    }

    // Use the implementation of Auth intended to be used in the GWT client app.
    private static final Auth AUTH = Auth.get();

    private static final String FACEBOOK_AUTH_URL = "https://www.facebook.com/dialog/oauth";

    // This app's personal client ID assigned by the Facebook Developer App
    // (http://www.facebook.com/developers).
    private static final String FACEBOOK_CLIENT_ID = "438576859566362";

    // All available scopes are listed here:
    // http://developers.facebook.com/docs/authentication/permissions/
    // This scope allows the app to access the user's email address.
    private static final String FACEBOOK_EMAIL_SCOPE = "email";

    // This scope allows the app to access the user's birthday.
    private static final String FACEBOOK_GROUP_SCOPE = "user_groups";

    // Adds a button to the page that asks for authentication from Facebook.
    // Note that Facebook does not allow localhost as a redirect URL, so while
    // this code will work when hosted, it will not work when testing locally.
    private void addFacebookAuth() {
        // Since the auth flow requires opening a popup window, it must be started
        // as a direct result of a user action, such as clicking a button or link.
        // Otherwise, a browser's popup blocker may block the popup.
        Button button = new Button("Authenticate with Facebook");
        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final AuthRequest req = new AuthRequest(FACEBOOK_AUTH_URL, FACEBOOK_CLIENT_ID)
                        .withScopes(FACEBOOK_EMAIL_SCOPE, FACEBOOK_GROUP_SCOPE)
                                // Facebook expects a comma-delimited list of scopes
                        .withScopeDelimiter(",");
                AUTH.login(req, new AuthenticationHandler());
            }
        });
        button.setStyleName("css_btn_class");
        this.add(button);
    }
}
