package com.btrll.rooms.client;

import com.btrll.rooms.client.activities.AboutPlace;
import com.btrll.rooms.client.activities.RoomListPlace;
import com.btrll.rooms.client.activities.map.MapPlace;
import com.btrll.rooms.client.activities.room.RoomPlace;
import com.btrll.rooms.client.places.HomePlace;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ HomePlace.Tokenizer.class, AboutPlace.Tokenizer.class,
		RoomPlace.Tokenizer.class, MapPlace.Tokenizer.class,
		RoomListPlace.Tokenizer.class })
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}
