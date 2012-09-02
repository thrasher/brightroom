package com.btrll.rooms.client;

import com.btrll.rooms.client.activities.AboutPlace;
import com.btrll.rooms.client.activities.OfficeListActivity;
import com.btrll.rooms.client.activities.RoomListActivity;
import com.btrll.rooms.client.activities.RoomListPlace;
import com.btrll.rooms.client.activities.map.MapPlace;
import com.btrll.rooms.client.activities.room.RoomPlace;
import com.btrll.rooms.client.places.HomePlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class TabletNavActivityMapper implements ActivityMapper {

	private final ClientFactory clientFactory;

	public TabletNavActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	private OfficeListActivity officeListActivity;
	private RoomListActivity roomListActivity;

	private Activity getOfficeListActivity() {
		if (officeListActivity == null) {
			officeListActivity = new OfficeListActivity(clientFactory);
		}
		return officeListActivity;
	}

	private RoomListActivity getRoomListActivity(Place place) {
		if (roomListActivity == null || !roomListActivity.isCurrent(place)) {
			if (place instanceof MapPlace)
				roomListActivity = new RoomListActivity(clientFactory,
						(MapPlace) place);
			else if (place instanceof RoomPlace)
				roomListActivity = new RoomListActivity(clientFactory,
						(RoomPlace) place);
			else if (place instanceof RoomListPlace)
				roomListActivity = new RoomListActivity(clientFactory,
						(RoomListPlace) place);
		}
		return roomListActivity;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof HomePlace || place instanceof AboutPlace) {
			return getOfficeListActivity();
		}

		if (place instanceof MapPlace || place instanceof RoomPlace
				|| place instanceof RoomListPlace) {
			return getRoomListActivity(place);
		}

		return new OfficeListActivity(clientFactory);
	}
}
