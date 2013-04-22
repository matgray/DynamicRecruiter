package com.phideltcmu.recruiter.client.ui.popup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.phideltcmu.recruiter.client.DynamicRecruiter;
import com.phideltcmu.recruiter.shared.model.Person;

public class NotesAdder extends HidablePopup {
    private Button saveButton = new Button("Save");
    private Label title = new Label();

    public NotesAdder(final Person p) {
        super();
        title.setText("Notes For " + p.getFullName());
        title.setStyleName("oldenburg150");
        final RichTextArea area = new RichTextArea();
        area.setSize("100%", "14em");
        String notes = p.getNotes();
        area.setHTML(notes == null ? "" : notes);

        // Add the components to a panel
        Grid grid = new Grid(4, 1);
        grid.setWidget(0, 0, title);
        grid.setWidget(1, 0, area);
        grid.setWidget(2, 0, saveButton);

        saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                DynamicRecruiter.RECRUIT_SERVICE.saveNotes(p.getAndrewID(), area.getHTML(), DynamicRecruiter.authUser.getAuthToken(), new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onSuccess(Void aVoid) {
                        Window.alert("Notes Saved!");
                    }
                });
            }
        });
        this.bindWidget(grid);
    }

}
