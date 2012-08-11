package com.btrll.rooms.client;

import com.btrll.rooms.client.activities.animation.AnimationPlace;
import com.btrll.rooms.client.activities.animationdone.AnimationDissolvePlace;
import com.btrll.rooms.client.activities.animationdone.AnimationFadePlace;
import com.btrll.rooms.client.activities.animationdone.AnimationFlipPlace;
import com.btrll.rooms.client.activities.animationdone.AnimationPopPlace;
import com.btrll.rooms.client.activities.animationdone.AnimationSlidePlace;
import com.btrll.rooms.client.activities.animationdone.AnimationSlideUpPlace;
import com.btrll.rooms.client.activities.animationdone.AnimationSwapPlace;
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

		if (oldPlace instanceof AnimationSlidePlace && newPlace instanceof AnimationPlace) {
			return Animation.SLIDE_REVERSE;
		}

		if (newPlace instanceof AnimationSlideUpPlace) {
			return Animation.SLIDE_UP;
		}

		if (oldPlace instanceof AnimationSlideUpPlace && newPlace instanceof AnimationPlace) {
			return Animation.SLIDE_UP_REVERSE;
		}

		if (newPlace instanceof AnimationDissolvePlace) {
			return Animation.DISSOLVE;
		}

		if (oldPlace instanceof AnimationDissolvePlace && newPlace instanceof AnimationPlace) {
			return Animation.DISSOLVE_REVERSE;
		}

		if (newPlace instanceof AnimationFadePlace) {
			return Animation.FADE;
		}

		if (oldPlace instanceof AnimationFadePlace && newPlace instanceof AnimationPlace) {
			return Animation.FADE_REVERSE;
		}
		if (newPlace instanceof AnimationFlipPlace) {
			return Animation.FLIP;
		}

		if (oldPlace instanceof AnimationFlipPlace && newPlace instanceof AnimationPlace) {
			return Animation.FLIP_REVERSE;
		}

		if (newPlace instanceof AnimationPopPlace) {
			return Animation.POP;
		}

		if (oldPlace instanceof AnimationPopPlace && newPlace instanceof AnimationPlace) {
			return Animation.POP_REVERSE;
		}

		if (newPlace instanceof AnimationSwapPlace) {
			return Animation.SWAP;
		}

		if (oldPlace instanceof AnimationSwapPlace && newPlace instanceof AnimationPlace) {
			return Animation.SWAP_REVERSE;
		}

		//		if (oldPlace instanceof AnimationCubePlace && newPlace instanceof AnimationPlace) {
		//			return Animation.CUBE_REVERSE;
		//		}
		//
		//		if (oldPlace instanceof AnimationPlace && newPlace instanceof AnimationCubePlace) {
		//			System.out.println("asdfasdf");
		//			return Animation.CUBE;
		//		}

		return Animation.SLIDE;
	}

}
