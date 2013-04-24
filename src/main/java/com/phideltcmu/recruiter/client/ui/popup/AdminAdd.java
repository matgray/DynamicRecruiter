/*
 * Copyright (c) 2013 Mathew Gray.
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */

package com.phideltcmu.recruiter.client.ui.popup;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.shared.model.InternalUser;

import java.util.List;

public class AdminAdd extends HidablePopup {

    public AdminAdd() {
        HorizontalPanel hp = new HorizontalPanel();
        final SuggestBox suggestBox = new SuggestBox();
        DynamicRecruiter.RECRUIT_SERVICE.getNonAdmins(new AsyncCallback<List<InternalUser>>() {
            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(List<InternalUser> internalUsers) {
                fillSuggest(internalUsers, suggestBox);
            }
        });
        hp.add(suggestBox);
        this.bindWidget(hp);
    }

    private void fillSuggest(List<InternalUser> list, final SuggestBox suggestbox) {
        MultiWordSuggestOracle oracle = (MultiWordSuggestOracle) suggestbox.getSuggestOracle();
        // Populate the suggestion oracle
        for (InternalUser person : list) {
            oracle.add(person.getName());
        }

        // When a suggestion is selected, switch to that client
        suggestbox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
            @Override
            public void onSelection(SelectionEvent<SuggestOracle.Suggestion> suggestionSelectionEvent) {
            }
        });

        // If the user hits enter in the search box, inspect the company
        suggestbox.addKeyUpHandler(new KeyUpHandler() {
            private int keyUpCount = 0;

            @Override
            public void onKeyUp(KeyUpEvent event) {
                // Bug in GWT 2.0.3. KeyUpEvent gets fired twice...

                keyUpCount++;

                if (keyUpCount % 2 == 0) {
                    return;
                }
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    suggestbox.setFocus(false);
                }
            }
        });
        this.display();
    }
}
