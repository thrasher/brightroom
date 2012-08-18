package com.btrll.rooms.client.activities.map;

import java.util.logging.Logger;

import com.btrll.rooms.client.ClientFactory;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;

public class MapActivity extends MGWTAbstractActivity {
	public interface View extends IsWidget {
	}

	static final Logger logger = Logger.getLogger("MapActivity");
	private final ClientFactory clientFactory;
	private final MapPlace place;

	public MapActivity(ClientFactory clientFactory, MapPlace place) {
		super();
		this.clientFactory = clientFactory;
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		logger.fine("MapActivity start");
		final View view = clientFactory.getMapView();

		if (place.getOfficeId() == null) {
			Window.alert("TODO: support adding new office maps.");
			// presenter = startCreate();
		} else {
			// TODO: select the office map
			logger.fine("Load the office map for id: " + place.getOfficeId());
		}

		panel.setWidget(view);

		// load gapi
		clientFactory.getGapi();
	}

}
