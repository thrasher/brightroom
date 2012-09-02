package com.btrll.rooms.client;

import com.btrll.rooms.client.activities.AboutPlace;
import com.btrll.rooms.client.activities.RoomListPlace;
import com.btrll.rooms.client.activities.map.MapPlace;
import com.btrll.rooms.client.activities.room.RoomPlace;
import com.btrll.rooms.client.places.HomePlace;
import com.google.gwt.place.shared.Place;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.mvp.client.AnimationMapper;

public class PhoneAnimationMapper implements AnimationMapper {

	@Override
	public Animation getAnimation(Place oldPlace, Place newPlace) {

		if (oldPlace instanceof HomePlace && newPlace instanceof RoomListPlace) {
			return Animation.SLIDE;
		}
		if (oldPlace instanceof RoomListPlace && newPlace instanceof HomePlace) {
			return Animation.SLIDE_REVERSE;
		}
		if (oldPlace instanceof RoomListPlace && newPlace instanceof RoomPlace) {
			return Animation.SLIDE;
		}
		if (oldPlace instanceof RoomPlace && newPlace instanceof RoomListPlace) {
			return Animation.SLIDE_REVERSE;
		}
		if (oldPlace instanceof MapPlace && newPlace instanceof RoomListPlace) {
			return Animation.SLIDE_REVERSE;
		}
		if (oldPlace instanceof HomePlace && newPlace instanceof AboutPlace) {
			return Animation.SLIDE_UP;
		}
		if (oldPlace instanceof AboutPlace && newPlace instanceof HomePlace) {
			return Animation.SLIDE_UP_REVERSE;
		}


		// default animation

		return Animation.SLIDE;

	}
}
