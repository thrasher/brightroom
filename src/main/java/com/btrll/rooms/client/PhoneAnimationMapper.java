/*
 * Copyright 2010 Daniel Kurka
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.btrll.rooms.client;

import com.btrll.rooms.client.activities.AboutPlace;
import com.btrll.rooms.client.activities.UIPlace;
import com.btrll.rooms.client.activities.animation.AnimationPlace;
import com.btrll.rooms.client.activities.pulltorefresh.PullToRefreshPlace;
import com.btrll.rooms.client.places.HomePlace;
import com.google.gwt.place.shared.Place;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.mvp.client.AnimationMapper;

/**
 * @author Daniel Kurka
 * 
 */
public class PhoneAnimationMapper implements AnimationMapper {

	@Override
	public Animation getAnimation(Place oldPlace, Place newPlace) {

		if (oldPlace instanceof UIPlace && newPlace instanceof HomePlace) {
			return Animation.SLIDE_REVERSE;
		}

		if (oldPlace instanceof AboutPlace && newPlace instanceof HomePlace) {
			return Animation.SLIDE_UP_REVERSE;
		}

		if (oldPlace instanceof HomePlace && newPlace instanceof AboutPlace) {
			return Animation.SLIDE_UP;
		}

		if (oldPlace instanceof HomePlace && newPlace instanceof AnimationPlace) {
			return Animation.SLIDE;
		}

		if (oldPlace instanceof HomePlace && newPlace instanceof UIPlace) {
			return Animation.SLIDE;
		}

		if (oldPlace instanceof AnimationPlace && newPlace instanceof HomePlace) {
			return Animation.SLIDE_REVERSE;
		}
		if (oldPlace instanceof UIPlace
				&& newPlace instanceof PullToRefreshPlace) {
			return Animation.SLIDE;
		}

		if (oldPlace instanceof PullToRefreshPlace
				&& newPlace instanceof UIPlace) {
			return Animation.SLIDE_REVERSE;
		}

		// animation

		return Animation.SLIDE;

	}
}
