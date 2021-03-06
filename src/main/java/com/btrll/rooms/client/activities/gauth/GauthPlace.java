package com.btrll.rooms.client.activities.gauth;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class GauthPlace extends Place {
	@Prefix(PREFIX)
	public static class Tokenizer implements PlaceTokenizer<GauthPlace> {

		@Override
		public GauthPlace getPlace(String token) {
			return new GauthPlace();
		}

		@Override
		public String getToken(GauthPlace place) {
			return "";
		}

	}

	private static final String PREFIX = "";

	@Override
	public String toString() {
		return PREFIX + ":";
	}

}
