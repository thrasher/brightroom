package com.btrll.rooms.client.activities.gauth;

import com.btrll.rooms.client.ChromeWorkaround;
import com.btrll.rooms.client.DetailViewGwtImpl;
import com.google.gwt.user.client.ui.FlowPanel;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.widget.Button;

public class GauthViewImpl extends DetailViewGwtImpl implements GauthView {
	private Button authButton;

	public GauthViewImpl() {

		FlowPanel content = new FlowPanel();
		// content.getElement().getStyle().setMargin(20, Unit.PX);

		scrollPanel.setScrollingEnabledX(false);

		authButton = new Button("Google Auth");

		content.add(authButton);

		scrollPanel.setWidget(content);

		ChromeWorkaround.maybeUpdateScroller(scrollPanel);

	}

	@Override
	public HasTapHandlers getAuthButton() {
		return authButton;
	}

}
