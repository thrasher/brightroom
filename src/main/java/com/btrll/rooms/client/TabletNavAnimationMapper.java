package com.btrll.rooms.client;

import java.util.logging.Logger;

import com.btrll.rooms.client.activities.map.MapPlace;
import com.btrll.rooms.client.activities.room.RoomPlace;
import com.btrll.rooms.client.places.HomePlace;
import com.google.gwt.place.shared.Place;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.mvp.client.AnimationMapper;

public class TabletNavAnimationMapper implements AnimationMapper {
	static final Logger logger = Logger
			.getLogger(TabletNavAnimationMapper.class.getName());

	@Override
	public Animation getAnimation(Place oldPlace, Place newPlace) {
		logger.fine("oldPlace: " + oldPlace + " newPlace: " + newPlace);

		if (oldPlace == null) {
			return Animation.FADE;
		}

		if (oldPlace instanceof HomePlace && newPlace instanceof MapPlace) {
			return Animation.SLIDE;
		}

		if (oldPlace instanceof MapPlace && newPlace instanceof HomePlace) {
			return Animation.SLIDE_REVERSE;
		}

		if (oldPlace instanceof MapPlace && newPlace instanceof RoomPlace) {
			return Animation.DISSOLVE;
		}

		return Animation.SLIDE;
	}

}
