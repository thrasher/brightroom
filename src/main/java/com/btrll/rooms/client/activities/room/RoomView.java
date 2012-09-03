package com.btrll.rooms.client.activities.room;

import com.btrll.rooms.client.DetailView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.Button;

public class RoomView extends DetailView implements RoomActivity.View {
	private static final Binder binder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, RoomView> {
	}

	@UiField
	FlowPanel panel;
	@UiField
	FlowPanel checkPanel;
	@UiField
	FlowPanel busyPanel;
	@UiField
	FlowPanel availablePanel;
	@UiField
	Button checkButton;
	@UiField
	Button bookButton;
	@UiField
	Label roomLabel;

	public RoomView() {
		initWidget(binder.createAndBindUi(this));

		busyPanel.setVisible(false);
		availablePanel.setVisible(false);

		scrollPanel.setWidget(panel);
		scrollPanel.setScrollingEnabledX(false);
	}

	@Override
	public Button getCheckButton() {
		return checkButton;
	}

	@Override
	public Button getBookButton() {
		return bookButton;
	}

	public void setRoomName(String name) {
		roomLabel.setText(name);
	}

	public void setCheck() {
		checkPanel.setVisible(true);
		busyPanel.setVisible(false);
		availablePanel.setVisible(false);
	}

	public void setBusy(boolean isBusy) {
		checkPanel.setVisible(false);
		busyPanel.setVisible(isBusy);
		availablePanel.setVisible(!isBusy);
	}

	// @UiHandler("checkButton")
	// void handleClick(ClickEvent e) {
	// Window.alert("Hello, AJAX");
	// }
}