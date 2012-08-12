package com.btrll.rooms.client;

import java.util.logging.Logger;

import com.btrll.rooms.client.activities.AboutActivity;
import com.btrll.rooms.client.activities.AboutPlace;
import com.btrll.rooms.client.activities.UIPlace;
import com.btrll.rooms.client.activities.animation.AnimationPlace;
import com.btrll.rooms.client.activities.animationdone.AnimationCubePlace;
import com.btrll.rooms.client.activities.animationdone.AnimationDissolvePlace;
import com.btrll.rooms.client.activities.animationdone.AnimationDoneActivity;
import com.btrll.rooms.client.activities.animationdone.AnimationFadePlace;
import com.btrll.rooms.client.activities.animationdone.AnimationFlipPlace;
import com.btrll.rooms.client.activities.animationdone.AnimationPopPlace;
import com.btrll.rooms.client.activities.animationdone.AnimationSlidePlace;
import com.btrll.rooms.client.activities.animationdone.AnimationSlideUpPlace;
import com.btrll.rooms.client.activities.animationdone.AnimationSwapPlace;
import com.btrll.rooms.client.activities.gauth.GauthActivity;
import com.btrll.rooms.client.activities.gauth.GauthPlace;
import com.btrll.rooms.client.activities.pulltorefresh.PullToRefreshActivity;
import com.btrll.rooms.client.activities.pulltorefresh.PullToRefreshPlace;
import com.btrll.rooms.client.places.HomePlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class TabletMainActivityMapper implements ActivityMapper {
	static final Logger logger = Logger.getLogger("TabletMainActivityMapper");

	private final ClientFactory clientFactory;

	private Place lastPlace;

	public TabletMainActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;

	}

	@Override
	public Activity getActivity(Place place) {
		Activity activity = getActivity(lastPlace, place);
		lastPlace = place;
		return activity;

	}

	private AboutActivity aboutActivity;

	private AboutActivity getAboutActivity() {
		if (aboutActivity == null) {
			aboutActivity = new AboutActivity(clientFactory);
		}

		return aboutActivity;
	}

	private Activity getActivity(Place lastPlace, Place newPlace) {
		if (newPlace instanceof HomePlace) {
			return getAboutActivity();
		}

		if (newPlace instanceof AboutPlace) {
			return getAboutActivity();
		}

		if (newPlace instanceof UIPlace) {
			return getAboutActivity();
		}

		// if (newPlace instanceof ScrollWidgetPlace) {
		// return new ScrollWidgetActivity(clientFactory);
		// }
		//
		// if (newPlace instanceof ElementsPlace) {
		// return new ElementsActivity(clientFactory);
		// }
		//
		// if (newPlace instanceof FormsPlace) {
		// return new FormsActivity(clientFactory);
		// }
		//
		// if (newPlace instanceof ButtonBarPlace) {
		// return new ButtonBarActivity(clientFactory);
		// }
		//
		// if (newPlace instanceof SearchBoxPlace) {
		// return new SearchBoxActivity(clientFactory);
		// }
		//
		// if (newPlace instanceof TabBarPlace) {
		// return new TabBarActivity(clientFactory);
		// }
		//
		// if (newPlace instanceof ButtonPlace) {
		// return new ButtonActivity(clientFactory);
		// }
		//
		// if (newPlace instanceof PopupPlace) {
		// return new PopupActivity(clientFactory);
		// }
		//
		// if (newPlace instanceof ProgressBarPlace) {
		// return new ProgressBarActivity(clientFactory);
		// }
		//
		// if (newPlace instanceof ProgressIndicatorPlace) {
		// return new ProgressIndicatorActivity(clientFactory);
		// }
		//
		// if (newPlace instanceof SliderPlace) {
		// return new SliderActivity(clientFactory);
		// }

		if (newPlace instanceof AnimationPlace) {
			return new AboutActivity(clientFactory);
		}

		if (newPlace instanceof PullToRefreshPlace) {
			return new PullToRefreshActivity(clientFactory);
		}

		// if (newPlace instanceof CarouselPlace) {
		// return new CarouselActivity(clientFactory);
		// }
		//
		// if (newPlace instanceof GroupedCellListPlace) {
		// return new GroupedCellListActivity(clientFactory);
		// }

		if (newPlace instanceof AnimationSlidePlace
				|| newPlace instanceof AnimationSlideUpPlace
				|| newPlace instanceof AnimationDissolvePlace
				|| newPlace instanceof AnimationFadePlace
				|| newPlace instanceof AnimationFlipPlace
				|| newPlace instanceof AnimationPopPlace
				|| newPlace instanceof AnimationSwapPlace
				|| newPlace instanceof AnimationCubePlace) {
			return new AnimationDoneActivity(clientFactory);
		}

		if (newPlace instanceof GauthPlace) {
			return new GauthActivity(clientFactory);
		}

		logger.warning(newPlace + " is not mapped to an activity");
		return null;
	}

}
