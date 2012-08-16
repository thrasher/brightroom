package com.btrll.rooms.client;

import com.btrll.rooms.client.activities.AboutPlace;
import com.btrll.rooms.client.activities.OfficeListActivity;
import com.btrll.rooms.client.activities.UIActivity;
import com.btrll.rooms.client.activities.UIPlace;
import com.btrll.rooms.client.activities.animation.AnimationActivity;
import com.btrll.rooms.client.activities.animation.AnimationPlace;
import com.btrll.rooms.client.activities.animationdone.AnimationCubePlace;
import com.btrll.rooms.client.activities.animationdone.AnimationDissolvePlace;
import com.btrll.rooms.client.activities.animationdone.AnimationFadePlace;
import com.btrll.rooms.client.activities.animationdone.AnimationFlipPlace;
import com.btrll.rooms.client.activities.animationdone.AnimationPopPlace;
import com.btrll.rooms.client.activities.animationdone.AnimationSlidePlace;
import com.btrll.rooms.client.activities.animationdone.AnimationSlideUpPlace;
import com.btrll.rooms.client.activities.animationdone.AnimationSwapPlace;
import com.btrll.rooms.client.activities.map.MapPlace;
import com.btrll.rooms.client.activities.pulltorefresh.PullToRefreshPlace;
import com.btrll.rooms.client.places.HomePlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class TabletNavActivityMapper implements ActivityMapper {

	private final ClientFactory clientFactory;

	public TabletNavActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	private UIActivity uiActivity;
	private OfficeListActivity officeListActivity;
	private AnimationActivity animationActivity;

	private Activity getUIActivity() {
		if (uiActivity == null) {
			uiActivity = new UIActivity(clientFactory);
		}
		return uiActivity;
	}

	private Activity getOfficeListActivity() {
		if (officeListActivity == null) {
			officeListActivity = new OfficeListActivity(clientFactory);
		}
		return officeListActivity;
	}

	private Activity getAnimationActivity() {
		if (animationActivity == null) {
			animationActivity = new AnimationActivity(clientFactory);
		}
		return animationActivity;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof HomePlace || place instanceof AboutPlace || place instanceof MapPlace) {
			return getOfficeListActivity();
		}

		if (place instanceof PullToRefreshPlace
//				|| place instanceof GroupedCellListPlace
//				|| place instanceof CarouselPlace
				|| place instanceof UIPlace
//				|| place instanceof ScrollWidgetPlace
//				|| place instanceof ElementsPlace
//				|| place instanceof FormsPlace
//				|| place instanceof ButtonBarPlace
//				|| place instanceof SearchBoxPlace
//				|| place instanceof TabBarPlace
//				|| place instanceof ButtonPlace
//				|| place instanceof PopupPlace
//				|| place instanceof ProgressBarPlace
//				|| place instanceof SliderPlace
//				|| place instanceof ProgressIndicatorPlace
				) {
			return getUIActivity();
		}

		if (place instanceof AnimationPlace) {
			return getAnimationActivity();
		}

		if (place instanceof AnimationSlidePlace
				|| place instanceof AnimationSlideUpPlace
				|| place instanceof AnimationDissolvePlace
				|| place instanceof AnimationFadePlace
				|| place instanceof AnimationFlipPlace
				|| place instanceof AnimationPopPlace
				|| place instanceof AnimationSwapPlace
				|| place instanceof AnimationCubePlace) {
			return getAnimationActivity();
		}
		return new OfficeListActivity(clientFactory);
	}
}
