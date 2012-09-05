package com.btrll.rooms.client.activities.room;

import com.btrll.rooms.client.DetailView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.ProgressIndicator;

public class RoomView extends DetailView implements RoomActivity.View {
	private static final Binder binder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, RoomView> {
	}

	@UiField
	FlowPanel panel;
	@UiField
	ProgressIndicator pi;
	@UiField
	Button checkButton;
	@UiField
	Button bookButton;
	@UiField
	HTML roomLabel;

	public RoomView() {
		initWidget(binder.createAndBindUi(this));

		checkButton.setVisible(false);
		bookButton.setVisible(false);

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

	public void setRoomName(SafeHtml name) {
		roomLabel.setHTML(name);
	}

	public void setCheck() {
		pi.setVisible(true);
		checkButton.setVisible(false);
		bookButton.setVisible(false);
	}

	public void setBusy(boolean isBusy) {
		pi.setVisible(false);
		checkButton.setVisible(isBusy);
		bookButton.setVisible(!isBusy);
	}

	// @UiHandler("checkButton")
	// void handleClick(ClickEvent e) {
	// Window.alert("Hello, AJAX");
	// }
}