package com.btrll.rooms.client.activities.map;

import java.util.logging.Logger;

import com.btrll.rooms.client.ClientFactory;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class MapActivity extends MGWTAbstractActivity {
	public interface View extends IsWidget {
	}

	static final Logger logger = Logger.getLogger("MapActivity");
	private final ClientFactory clientFactory;

	public MapActivity(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		logger.fine("MapActivity start");
		final View view = clientFactory.getMapView();

		panel.setWidget(view);

		// load gapi
		clientFactory.getGapi();
	}

}
