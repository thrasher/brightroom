package com.btrll.rooms.client;

import com.btrll.rooms.client.activities.AboutPlace;
import com.btrll.rooms.client.activities.animation.AnimationPlace;
import com.btrll.rooms.client.activities.map.MapPlace;
import com.btrll.rooms.client.activities.pulltorefresh.PullToRefreshPlace;
import com.btrll.rooms.client.places.HomePlace;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ HomePlace.Tokenizer.class, AboutPlace.Tokenizer.class,
		AnimationPlace.Tokenizer.class, PullToRefreshPlace.Tokenizer.class,
		MapPlace.Tokenizer.class })
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}
