package com.btrll.rooms.client;

import com.btrll.rooms.client.activities.AboutPlace.AboutPlaceTokenizer;
import com.btrll.rooms.client.activities.animation.AnimationPlace.AnimationPlaceTokenizer;
import com.btrll.rooms.client.activities.gauth.GauthPlace;
import com.btrll.rooms.client.activities.pulltorefresh.PullToRefreshPlace;
import com.btrll.rooms.client.places.HomePlace.HomePlaceTokenizer;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ HomePlaceTokenizer.class, AboutPlaceTokenizer.class,
		AnimationPlaceTokenizer.class, PullToRefreshPlace.Tokenizer.class,
		GauthPlace.Tokenizer.class })
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}
