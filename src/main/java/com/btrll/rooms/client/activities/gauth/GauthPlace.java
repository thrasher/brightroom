package com.btrll.rooms.client.activities.gauth;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class GauthPlace extends Place {
	public static class Tokenizer implements
			PlaceTokenizer<GauthPlace> {

		@Override
		public GauthPlace getPlace(String token) {
			return new GauthPlace();
		}

		@Override
		public String getToken(GauthPlace place) {
			return "";
		}

	}
}
