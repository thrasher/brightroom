package com.btrll.rooms.client;

import com.btrll.rooms.client.activities.RoomListPlace;
import com.btrll.rooms.client.activities.map.MapPlace;
import com.btrll.rooms.client.activities.room.RoomPlace;
import com.btrll.rooms.client.places.HomePlace;
import com.google.gwt.place.shared.Place;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.mvp.client.AnimationMapper;

public class TabletMainAnimationMapper implements AnimationMapper {

	@Override
	public Animation getAnimation(Place oldPlace, Place newPlace) {
		if (oldPlace == null) {
			return Animation.FADE;
		}

		// animation

		if (newPlace instanceof HomePlace) {
			return Animation.SLIDE_UP;
		}

		if (newPlace instanceof MapPlace) {
			return Animation.FLIP;
		}

		if (oldPlace instanceof MapPlace && newPlace instanceof RoomListPlace) {
			return Animation.FLIP_REVERSE;
		}

		if (oldPlace instanceof MapPlace && newPlace instanceof RoomPlace) {
			return Animation.FLIP;
		}

		if (oldPlace instanceof RoomPlace && newPlace instanceof MapPlace) {
			return Animation.FLIP_REVERSE;
		}

		if (oldPlace instanceof RoomPlace && newPlace instanceof RoomPlace) {
			return Animation.SLIDE_UP;
		}

		return Animation.SLIDE;
	}

}
