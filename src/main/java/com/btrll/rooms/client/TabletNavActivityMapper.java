package com.btrll.rooms.client;

import com.btrll.rooms.client.activities.AboutPlace;
import com.btrll.rooms.client.activities.OfficeListActivity;
import com.btrll.rooms.client.activities.RoomListActivity;
import com.btrll.rooms.client.activities.RoomListPlace;
import com.btrll.rooms.client.activities.map.MapPlace;
import com.btrll.rooms.client.places.HomePlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class TabletNavActivityMapper implements ActivityMapper {

	private final ClientFactory clientFactory;

	public TabletNavActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	private RoomListActivity roomListActivity;
	private OfficeListActivity officeListActivity;

	private Activity getRoomListActivity() {
		if (roomListActivity == null) {
			roomListActivity = new RoomListActivity(clientFactory);
		}
		return roomListActivity;
	}

	private Activity getOfficeListActivity() {
		if (officeListActivity == null) {
			officeListActivity = new OfficeListActivity(clientFactory);
		}
		return officeListActivity;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof HomePlace || place instanceof AboutPlace) {
			return getOfficeListActivity();
		}

		if (place instanceof RoomListPlace || place instanceof MapPlace) {
			return getRoomListActivity();
		}

		return new OfficeListActivity(clientFactory);
	}
}
