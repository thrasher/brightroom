package com.btrll.rooms.client.activities.map;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class MapPlace extends Place {
	@Prefix(PREFIX)
	public static class Tokenizer implements PlaceTokenizer<MapPlace> {
		private static final String NO_ID = "create";

		@Override
		public MapPlace getPlace(String token) {
			try {
				// Parse the task ID from the URL.
				String officeId = token;
				return new MapPlace(officeId);
			} catch (NumberFormatException e) {
				// If the ID cannot be parsed, assume we are creating a task.
				return MapPlace.getMapCreatePlace();
			}
		}

		@Override
		public String getToken(MapPlace place) {
			String officeId = place.getOfficeId();
			return (officeId == null) ? NO_ID : officeId.toString();
		}
	}

	private static MapPlace singleton;

	/**
	 * Get the singleton instance of the {@link TaskPlace} used to create a new
	 * task.
	 * 
	 * @return the place
	 */
	public static MapPlace getMapCreatePlace() {
		if (singleton == null) {
			singleton = new MapPlace(null);
		}
		return singleton;
	}

	private final String officeId;

	public MapPlace(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeId() {
		return officeId;
	}

	private static final String PREFIX = "map";

	@Override
	public String toString() {
		return PREFIX + ":" + getOfficeId();
	}

}
