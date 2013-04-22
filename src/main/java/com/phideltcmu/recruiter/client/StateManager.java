/**
 * @author Mathew Gray
 * */

package com.phideltcmu.recruiter.client;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Controls what is being displayed inside of the main div, adding widgets on a stack of displayed widgets.
 * widgets not on top of the stack will be hidden.
 */
public class StateManager {
    private DeckPanel clientView;
    private int currentIndex = -1;
    private SimpleEventBus masterEventBus = new SimpleEventBus();

    public StateManager(DeckPanel clientView) {
        this.clientView = clientView;
    }

    public void switchToWidget(final Widget newView) {
        //Panel we will be adding to the DeckPanel
        clientView.add(newView);
        currentIndex++;
        //Show the inspector
        clientView.showWidget(currentIndex);
    }

    public SimpleEventBus getMasterEventBus() {
        return masterEventBus;
    }
}