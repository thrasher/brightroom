package com.btrll.rooms.client.activities;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class RoomListPlace extends Place {
	public static class Tokenizer implements PlaceTokenizer<RoomListPlace> {

		@Override
		public RoomListPlace getPlace(String token) {
			return new RoomListPlace();
		}

		@Override
		public String getToken(RoomListPlace place) {
			return "";
		}

	}
}
