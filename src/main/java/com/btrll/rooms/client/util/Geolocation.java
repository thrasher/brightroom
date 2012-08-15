package com.btrll.rooms.client.util;

import java.util.logging.Logger;

/**
 * <a href="http://dev.w3.org/geo/api/spec-source.html">HTML5 GEO spec</a>
 * 
 * @author jthrasher
 * 
 */
public class Geolocation {
	static final Logger logger = Logger.getLogger("Location");

	static void geoLocationCallback(Position position) {
		logger.fine("location: " + position.toJson());
	}

	static void geoLocationCallbackError(JSOModel error) {
		logger.warning("location error: " + error != null ? error.toJson()
				: null);
		if (error.get("code").equals(error.get("TIMEOUT"))) {
			logger.fine("The length of time specified by the timeout property has elapsed before the implementation could successfully acquire a new Position object.");
			getGeoLocation(false, 60 * 60 * 1000, 60 * 1000);
		} else if (error.get("code").equals(error.get("PERMISSION_DENIED"))) {
			logger.fine("The location acquisition process failed because the document does not have permission to use the Geolocation API.");
		} else if (error.get("code").equals(error.get("POSITION_UNAVAILABLE"))) {
			logger.fine("The position of the device could not be determined. For instance, one or more of the location providers used in the location acquisition process reported an internal error that caused the process to fail entirely.");
		} else {
			logger.fine("other error: " + error.get("code"));
			// notify of failure
		}
	}

	public static void getGeoLocationCached() {
		getGeoLocation(false, 60 * 60 * 1000, 0);
	}

	// NOTE: using an int because JSNI doesn't support long
	public static native void getGeoLocation(boolean enableHighAccuracy,
			int maximumAge, int timeout) /*-{
		if(null == $wnd.navigator.geolocation) {
			return;
		}
		var positionOptions = {
			"enableHighAccuracy": enableHighAccuracy,
			"maximumAge": maximumAge,
			"timeout": timeout
		};
		$wnd.alert(positionOptions.maximumAge);
		$wnd.alert(positionOptions.timeout);
		$wnd.navigator.geolocation.getCurrentPosition(
			@com.btrll.rooms.client.util.Geolocation::geoLocationCallback(Lcom/btrll/rooms/client/util/Position;),
			@com.btrll.rooms.client.util.Geolocation::geoLocationCallbackError(Lcom/btrll/rooms/client/util/JSOModel;),
			positionOptions
		);
	}-*/;
}