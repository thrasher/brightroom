package com.btrll.rooms.client.activities.gauth;

import java.util.logging.Logger;

import com.btrll.rooms.client.ClientFactory;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class GauthActivity extends MGWTAbstractActivity {
	public interface View extends IsWidget {
		public HasTapHandlers getAuthButton();

		public void doPopup();

		public void hidePopup();
	}

	static final Logger logger = Logger.getLogger("GauthActivity");
	private final ClientFactory clientFactory;

	public GauthActivity(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		final View view = clientFactory.getGauthView();

		// TODO: add handler that gets called when gapi is loaded

		addHandlerRegistration(view.getAuthButton().addTapHandler(
				new TapHandler() {

					@Override
					public void onTap(TapEvent event) {
						logger.fine("redirecting user to Google Auth");
						clientFactory.getGapi().handleAuthClick();
					}
				}));
		addHandlerRegistration(eventBus.addHandler(GauthEvent.getType(),
				new GauthEvent.Handler() {
					@Override
					public void onGauth(GauthEvent event) {
						logger.fine("GauthEvent auth needed: "
								+ event.isAuthNeeded());
						if (event.isAuthNeeded()) {
							logger.fine("doPopup");
							view.doPopup();
						} else {
							logger.fine("hidePopup");
							clientFactory.getGauthView().hidePopup();
						}
					}
				}));

		panel.setWidget(view);

		// load gapi
		clientFactory.getGapi();
	}

	@Override
	public void onStop() {
		logger.fine("onStop");
		super.onStop();
	}

}
