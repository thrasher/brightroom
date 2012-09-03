package com.btrll.rooms.client.activities;

import com.btrll.rooms.client.DetailView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.RoundPanel;

public class AboutView extends DetailView implements AboutActivity.View {
	private static final Binder binder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, AboutView> {
	}

	@UiField
	RoundPanel panel;

	private Button button;

	public AboutView() {
		initWidget(binder.createAndBindUi(this));

		if (MGWT.getOsDetection().isPhone()) {
			button = new Button("back");
			button.getElement().setAttribute("style",
					"margin:auto;width: 200px;display:block");
			panel.add(button);
			headerBackButton.removeFromParent();
		}

		scrollPanel.setWidget(panel);
		scrollPanel.setScrollingEnabledX(false);
	}

	@Override
	public HasTapHandlers getBackbutton() {
		if (MGWT.getOsDetection().isPhone()) {
			return button;
		}
		return super.getBackbutton();
	}

}
