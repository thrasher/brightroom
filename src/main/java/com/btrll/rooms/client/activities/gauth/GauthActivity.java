package com.btrll.rooms.client.activities.gauth;

import com.btrll.rooms.client.ClientFactory;
import com.btrll.rooms.client.DetailActivity;
import com.btrll.rooms.client.util.Gapi;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;

public class GauthActivity extends DetailActivity {

	private final ClientFactory clientFactory;

	public GauthActivity(ClientFactory clientFactory) {
		super(clientFactory.getGauthView(), "nav");
		this.clientFactory = clientFactory;

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		super.start(panel, eventBus);
		GauthView view = clientFactory.getGauthView();

		view.getBackbuttonText().setText("UI");
		view.getMainButtonText().setText("Nav");
		view.getHeader().setText("Google Authorization");

		addHandlerRegistration(view.getAuthButton().addTapHandler(
				new TapHandler() {

					@Override
					public void onTap(TapEvent event) {
						Window.alert("You will be redirected to Google");
						Gapi.handleAuthClick();
					}
				}));

		panel.setWidget(view);
	}
}
