package com.btrll.rooms.client;

import java.util.logging.Logger;

import com.btrll.rooms.client.activities.AboutActivity;
import com.btrll.rooms.client.activities.AboutPlace;
import com.btrll.rooms.client.activities.RoomListPlace;
import com.btrll.rooms.client.activities.gauth.GauthActivity;
import com.btrll.rooms.client.activities.gauth.GauthPlace;
import com.btrll.rooms.client.activities.map.MapActivity;
import com.btrll.rooms.client.activities.map.MapPlace;
import com.btrll.rooms.client.activities.room.RoomActivity;
import com.btrll.rooms.client.activities.room.RoomPlace;
import com.btrll.rooms.client.places.HomePlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class TabletMainActivityMapper implements ActivityMapper {
	static final Logger logger = Logger.getLogger("TabletMainActivityMapper");

	private final ClientFactory clientFactory;

	private Place lastPlace;

	public TabletMainActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;

	}

	@Override
	public Activity getActivity(Place place) {
		Activity activity = getActivity(lastPlace, place);
		lastPlace = place;
		return activity;

	}

	private AboutActivity aboutActivity;

	private AboutActivity getAboutActivity() {
		if (aboutActivity == null) {
			aboutActivity = new AboutActivity(clientFactory);
		}

		return aboutActivity;
	}

	private Activity getActivity(Place lastPlace, Place newPlace) {
		logger.fine(lastPlace + " to " + newPlace);

		if (newPlace instanceof HomePlace) {
			return getAboutActivity();
		}

		if (newPlace instanceof AboutPlace) {
			return getAboutActivity();
		}

		if (newPlace instanceof RoomListPlace) {
			return getAboutActivity();
		}

		if (newPlace instanceof GauthPlace) {
			return new GauthActivity(clientFactory);
		}

		if (newPlace instanceof MapPlace) {
			return new MapActivity(clientFactory, (MapPlace) newPlace);
		}

		if (newPlace instanceof RoomPlace) {
			return new RoomActivity(clientFactory, (RoomPlace) newPlace);
		}

		logger.warning(newPlace + " is not mapped to an activity");
		return null;
	}

}
