package com.btrll.rooms.client;

import com.btrll.rooms.client.activities.AboutActivity;
import com.btrll.rooms.client.activities.AboutPlace;
import com.btrll.rooms.client.activities.OfficeListActivity;
import com.btrll.rooms.client.activities.RoomListActivity;
import com.btrll.rooms.client.activities.RoomListPlace;
import com.btrll.rooms.client.activities.map.MapActivity;
import com.btrll.rooms.client.activities.map.MapPlace;
import com.btrll.rooms.client.activities.room.RoomActivity;
import com.btrll.rooms.client.activities.room.RoomPlace;
import com.btrll.rooms.client.places.HomePlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class PhoneActivityMapper implements ActivityMapper {

	private final ClientFactory clientFactory;
	private RoomListActivity roomListActivity;

	public PhoneActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
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
		if (place instanceof HomePlace) {
			return new OfficeListActivity(clientFactory);
		}

		if (place instanceof RoomListPlace) {
			return getRoomListActivity(place);
		}

		if (place instanceof RoomPlace) {
			return new RoomActivity(clientFactory, (RoomPlace) place);
		}

		if (place instanceof AboutPlace) {
			return new AboutActivity(clientFactory);
		}

		if (place instanceof MapPlace) {
			return new MapActivity(clientFactory, (MapPlace) place);
		}

		return new OfficeListActivity(clientFactory);
	}
}
