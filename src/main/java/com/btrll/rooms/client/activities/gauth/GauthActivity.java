package com.btrll.rooms.client.activities.gauth;

import java.util.logging.Logger;

import com.btrll.rooms.client.ClientFactory;
import com.btrll.rooms.client.DetailActivity;
import com.btrll.rooms.client.util.Gapi;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;

public class GauthActivity extends DetailActivity {
	static final Logger logger = Logger.getLogger("GauthActivity");
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
						logger.fine("redirecting user to Google Auth");
						Gapi.handleAuthClick();
					}
				}));

		panel.setWidget(view);
	}
}
