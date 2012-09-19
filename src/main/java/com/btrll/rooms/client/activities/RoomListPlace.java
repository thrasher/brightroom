package com.btrll.rooms.client.activities;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class RoomListPlace extends Place {
	@Prefix(PREFIX)
	public static class Tokenizer implements PlaceTokenizer<RoomListPlace> {
		private static final String NO_ID = "create";

		@Override
		public RoomListPlace getPlace(String token) {
			try {
				// Parse the task ID from the URL.
				String officeId = token;
				return new RoomListPlace(officeId);
			} catch (NumberFormatException e) {
				// If the ID cannot be parsed, assume we are creating a task.
				return RoomListPlace.getMapCreatePlace();
			}
		}

		@Override
		public String getToken(RoomListPlace place) {
			String officeId = place.getOfficeId();
			return (officeId == null) ? NO_ID : officeId.toString();
		}
	}

	private static RoomListPlace singleton;

	/**
	 * Get the singleton instance of the {@link TaskPlace} used to create a new
	 * task.
	 * 
	 * @return the place
	 */
	public static RoomListPlace getMapCreatePlace() {
		if (singleton == null) {
			singleton = new RoomListPlace(null);
		}
		return singleton;
	}

	private static final String PREFIX = "rlp";
	private final String officeId;

	public RoomListPlace(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeId() {
		return officeId;
	}

	@Override
	public String toString() {
		return PREFIX + ":" + getOfficeId();
	}

}
