package com.btrll.rooms.client.activities.room;

import com.btrll.rooms.client.DetailView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.ProgressIndicator;

public class RoomView extends DetailView implements RoomActivity.View {
	private static final Binder binder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, RoomView> {
	}

	@UiField
	FlowPanel panel;
	@UiField
	ProgressIndicator pi;

	public RoomView() {
		initWidget(binder.createAndBindUi(this));

		scrollPanel.setWidget(panel);
	}

	@Override
	public void setRoom(String svg) {
	}
}