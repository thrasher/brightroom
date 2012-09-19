package com.btrll.rooms.client.activities.room;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class RoomPlace extends Place {
	@Prefix(PREFIX)
	public static class Tokenizer implements PlaceTokenizer<RoomPlace> {
		private static final String NO_ID = "create";

		@Override
		public RoomPlace getPlace(String resourceId) {
			try {
				return new RoomPlace(resourceId);
			} catch (NumberFormatException e) {
				// If the ID cannot be parsed, assume we are creating a task.
				return RoomPlace.getRoomCreatePlace();
			}
		}

		@Override
		public String getToken(RoomPlace place) {
			String resourceId = place.getResourceId();
			return (resourceId == null) ? NO_ID : resourceId;
		}
	}

	private static final String PREFIX = "room";
	private static RoomPlace singleton;

	/**
	 * Get the singleton instance of the {@link TaskPlace} used to create a new
	 * task.
	 * 
	 * @return the place
	 */
	public static RoomPlace getRoomCreatePlace() {
		if (singleton == null) {
			singleton = new RoomPlace(null);
		}
		return singleton;
	}

	private final String resourceId;

	public RoomPlace(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceId() {
		return resourceId;
	}

	@Override
	public String toString() {
		return PREFIX + ":" + getResourceId();
	}
}
