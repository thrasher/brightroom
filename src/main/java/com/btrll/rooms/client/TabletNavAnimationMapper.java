package com.btrll.rooms.client;

import com.btrll.rooms.client.activities.RoomListPlace;
import com.btrll.rooms.client.places.HomePlace;
import com.google.gwt.place.shared.Place;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.mvp.client.AnimationMapper;

public class TabletNavAnimationMapper implements AnimationMapper {

	@Override
	public Animation getAnimation(Place oldPlace, Place newPlace) {
		if (oldPlace == null) {
			return Animation.FADE;
		}

		if (oldPlace instanceof HomePlace && newPlace instanceof RoomListPlace) {
			return Animation.SLIDE;
		}

		if (oldPlace instanceof RoomListPlace && newPlace instanceof HomePlace) {
			return Animation.SLIDE_REVERSE;
		}

		return Animation.SLIDE;
	}

}
