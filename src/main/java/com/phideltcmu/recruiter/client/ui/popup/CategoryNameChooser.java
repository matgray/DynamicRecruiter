package com.phideltcmu.recruiter.client.ui.popup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.client.handler.AddCategoryHandler;

public class CategoryNameChooser extends HidablePopup {
    private static Label prompt = new Label("Add a new Category:");
    private final TextBox inputBox = new TextBox();
    private Button submitButton = new Button("Submit");
    private final CategoryNameChooser nameChooser = this;

    public CategoryNameChooser() {
        super();
        VerticalPanel vp = new VerticalPanel();
        vp.add(prompt);
        HorizontalPanel hp = new HorizontalPanel();
        hp.add(inputBox);
        hp.add(submitButton);
        vp.add(hp);

        submitButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if (inputBox.getText().length() < 4) {
                    Window.alert("The category has to have at least 4 characters...");
                    return;
                } else {
                    DynamicRecruiter.RECRUIT_SERVICE.addCategory(inputBox.getText(), DynamicRecruiter.authUser.getAuthToken(), new AddCategoryHandler());
                    nameChooser.hide();
                }
            }
        });
        this.bindWidget(vp);
    }
}
